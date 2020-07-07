package com.bhhan.authsvr.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by hbh5274@gmail.com on 2020-07-06
 * Github : http://github.com/bhhan5274
 */

@Component
@Slf4j
public class InspectHeaderFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        log.info("I AM HITTING THE AUTH SERVER: {}", httpServletRequest.getHeader("Authorization"));
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
