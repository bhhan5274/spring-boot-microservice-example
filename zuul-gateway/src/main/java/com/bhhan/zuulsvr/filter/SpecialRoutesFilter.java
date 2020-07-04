package com.bhhan.zuulsvr.filter;

import com.bhhan.zuulsvr.model.AbTestRoute;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FilterUtils filterUtils;
    private final RestTemplate restTemplate;
    private ProxyRequestHelper helper = new ProxyRequestHelper();

    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        final RequestContext ctx = RequestContext.getCurrentContext();
        AbTestRoute abTestRoute = getAbRoutingInfo(filterUtils.getServiceId());

        if(abTestRoute != null && useSpecialRoute(abTestRoute)){
            String route = buildRouteString(ctx.getRequest().getRequestURI(),
                    abTestRoute.getEndpoint(),
                    ctx.get("serviceId").toString());
            forwardToSpecialRoute(route);
        }

        return null;
    }

    private void forwardToSpecialRoute(String route) {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final HttpServletRequest request = ctx.getRequest();

        final MultiValueMap<String, String> headers = helper.buildZuulRequestHeaders(request);
        final MultiValueMap<String, String> params = helper.buildZuulRequestQueryParams(request);
        final String verb = getVerb(request);
        InputStream requestEntity = getRequestBody(request);

        if(request.getContentLength() < 0){
            ctx.setChunkedRequestBody();
        }

        helper.addIgnoredHeaders();
        CloseableHttpClient httpClient = null;
        HttpResponse response = null;

        try {
             httpClient = HttpClients.createDefault();
             response = forward(httpClient, verb, route, request, headers,
                     params, requestEntity);
             setResponse(response);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                httpClient.close();
            }catch(IOException e){

            }
        }
    }

    private void setResponse(HttpResponse response) throws IOException {
        helper.setResponse(response.getStatusLine().getStatusCode(),
                response.getEntity() == null ? null : response.getEntity().getContent(),
                revertHeaders(response.getAllHeaders()));
    }

    private MultiValueMap<String, String> revertHeaders(Header[] headers) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        for (Header header : headers) {
            final String name = header.getName();
            if(!map.containsKey(name)){
                map.put(name, new ArrayList<>());
            }
            Objects.requireNonNull(map.get(name)).add(header.getValue());
        }
        return map;
    }

    private HttpResponse forward(CloseableHttpClient httpClient, String verb, String route, HttpServletRequest request, MultiValueMap<String, String> headers, MultiValueMap<String, String> params, InputStream requestEntity) throws IOException {
        final Map<String, Object> info = helper.debug(verb, route, headers, params, requestEntity);
        final URL host = new URL(route);
        HttpHost httpHost = getHttpHost(host);

        HttpRequest httpRequest;
        final int contentLength = request.getContentLength();
        InputStreamEntity entity = new InputStreamEntity(requestEntity, contentLength, request.getContentType() != null ? ContentType.create(request.getContentType()) : null);

        switch (verb.toUpperCase()){
            case "POST":
                final HttpPost httpPost = new HttpPost(route);
                httpRequest = httpPost;
                httpPost.setEntity(entity);
                break;
            case "PUT":
                HttpPut httpPut = new HttpPut(route);
                httpRequest = httpPut;
                httpPut.setEntity(entity);
                break;
            case "PATCH":
                HttpPatch httpPatch = new HttpPatch(route);
                httpRequest = httpPatch;
                httpPatch.setEntity(entity);
                break;
            default:
                httpRequest = new BasicHttpRequest(verb, route);
        }

        httpRequest.setHeaders(convertHeaders(headers));
        return forwardRequest(httpClient, httpHost, httpRequest);
    }

    private HttpResponse forwardRequest(CloseableHttpClient httpClient, HttpHost httpHost, HttpRequest httpRequest) throws IOException {
        return httpClient.execute(httpHost, httpRequest);
    }

    private Header[] convertHeaders(MultiValueMap<String, String> headers) {
        List<Header> list = new ArrayList<>();
        for (String name : headers.keySet()) {
            for (String value : headers.get(name)) {
                list.add(new BasicHeader(name, value));
            }
        }

        return list.toArray(new BasicHeader[0]);
    }

    private HttpHost getHttpHost(URL host) {
        final HttpHost httpHost = new HttpHost(host.getHost(), host.getPort(), host.getProtocol());
        return httpHost;
    }

    private InputStream getRequestBody(HttpServletRequest request) {
        InputStream requestEntity = null;
        try {
            requestEntity = request.getInputStream();
        }catch(IOException ex){

        }

        return requestEntity;
    }

    private String getVerb(HttpServletRequest request) {
        return request.getMethod().toUpperCase();
    }

    private String buildRouteString(String oldEndpoint, String newEndpoint, String serviceName) {
        final int index = oldEndpoint.indexOf(serviceName);
        final String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        final String routeString = String.format("%s/%s", newEndpoint, strippedRoute);
        log.info("Target route: {}", routeString);
        return routeString;
    }

    private AbTestRoute getAbRoutingInfo(String serviceName) {
        ResponseEntity<AbTestRoute> restExchange = null;
        try{
            restExchange = restTemplate.exchange(
                    "http://specialroutesservice/v1/route/abtesting/{serviceName}",
                    HttpMethod.GET,
                    null,
                    AbTestRoute.class,
                    serviceName
            );
        }catch(HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND){
                return null;
            }
        }

        return restExchange.getBody();
    }

    private boolean useSpecialRoute(AbTestRoute abTestRoute) {
        final Random random = new Random();
        if(abTestRoute.getActive().equals("N")){
            return false;
        }

        int value = random.nextInt((10 - 1) + 1) + 1;

        if(abTestRoute.getWeight() < value) {
            return true;
        }

        return false;
    }
}
