package com.bhhan.licenses.client;

import com.bhhan.licenses.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hbh5274@gmail.com on 2020-07-02
 * Github : http://github.com/bhhan5274
 */

@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @GetMapping(value = "/v1/organizations/{organizationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
