package com.bhhan.zuulsvr.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Component
@Slf4j
@RequiredArgsConstructor
public class TrackingFilter extends ZuulFilter {

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;
    private final FilterUtils filterUtils;

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
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
        if(isCorrelationIdPresent()){
            log.info("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }else {
            filterUtils.setCorrelationId(generateCorrelationId());
            log.info("tmx-correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }

        final RequestContext ctx = RequestContext.getCurrentContext();
        log.info("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());

        return null;
    }

    private boolean isCorrelationIdPresent(){
        if(filterUtils.getCorrelationId() != null){
            return true;
        }

        return false;
    }

    private String generateCorrelationId(){
        return UUID.randomUUID().toString();
    }
}
