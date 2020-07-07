package com.bhhan.zuulsvr.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Component
public class FilterUtils {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";
    public static final String PRE_FILTER_TYPE = "pre";
    public static final String POST_FILTER_TYPE = "post";
    public static final String ROUTE_FILTER_TYPE = "route";

    public String getCorrelationId() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final String correlationId = ctx.getRequest().getHeader(CORRELATION_ID);

        if(correlationId != null){
            return correlationId;
        }else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId){
        final RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }

    public String getOrgId(){
        final RequestContext ctx = RequestContext.getCurrentContext();
        final String orgId = ctx.getRequest().getHeader(ORG_ID);

        if(orgId != null){
            return orgId;
        }else {
            return ctx.getZuulRequestHeaders().get(ORG_ID);
        }
    }

    public void setOrgId(String orgId){
        final RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(ORG_ID, orgId);
    }

    public String getUserId(){
        final RequestContext ctx = RequestContext.getCurrentContext();
        final String userId = ctx.getRequest().getHeader(USER_ID);

        if(userId != null){
            return userId;
        }else {
            return ctx.getZuulRequestHeaders().get(USER_ID);
        }
    }

    public void setUserId(String userId){
        final RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USER_ID, userId);
    }

    public String getAuthToken(){
        final RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }

    public String getServiceId(){
        final RequestContext ctx = RequestContext.getCurrentContext();

        if(ctx.get("serviceId") == null){
            return "";
        }

        return ctx.get("serviceId").toString();
    }
}
