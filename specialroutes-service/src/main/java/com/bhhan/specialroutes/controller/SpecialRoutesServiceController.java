package com.bhhan.specialroutes.controller;

import com.bhhan.specialroutes.model.AbTestingRoute;
import com.bhhan.specialroutes.service.AbTestingRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@RestController
@RequestMapping("/v1/route")
@Slf4j
@RequiredArgsConstructor
public class SpecialRoutesServiceController {
    private final AbTestingRouteService routeService;

    @GetMapping("/abtesting/{serviceName}")
    public AbTestingRoute abTestingRoute(@PathVariable("serviceName") String serviceName){
        return routeService.getRoute(serviceName);
    }
}
