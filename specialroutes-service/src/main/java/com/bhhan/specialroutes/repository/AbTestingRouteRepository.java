package com.bhhan.specialroutes.repository;

import com.bhhan.specialroutes.model.AbTestingRoute;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */
public interface AbTestingRouteRepository extends JpaRepository<AbTestingRoute, String> {
    AbTestingRoute findByServiceName(String serviceName);
}
