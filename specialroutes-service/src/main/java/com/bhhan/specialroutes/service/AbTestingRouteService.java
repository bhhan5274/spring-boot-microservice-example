package com.bhhan.specialroutes.service;

import com.bhhan.specialroutes.exception.NoRouteFound;
import com.bhhan.specialroutes.model.AbTestingRoute;
import com.bhhan.specialroutes.repository.AbTestingRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AbTestingRouteService {
    private final AbTestingRouteRepository abTestingRouteRepository;

    public AbTestingRoute getRoute(String serviceName){
        final AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);
        if(route == null){
            throw new NoRouteFound();
        }

        return route;
    }

    public void saveAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    public void updateRouteAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    public void deleteRoute(AbTestingRoute route){
        abTestingRouteRepository.delete(route);
    }
}
