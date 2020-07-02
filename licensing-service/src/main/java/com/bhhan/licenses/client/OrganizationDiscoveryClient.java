package com.bhhan.licenses.client;

import com.bhhan.licenses.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-07-02
 * Github : http://github.com/bhhan5274
 */

@Component
@RequiredArgsConstructor
public class OrganizationDiscoveryClient {
    private final DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId){
        final RestTemplate restTemplate = new RestTemplate();
        final List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if(instances.size() == 0){
            return null;
        }

        String serviceUrl = String.format("%s/v1/organizations/%s",
                instances.get(0).getUri().toString(), organizationId);

        final ResponseEntity<Organization> restExchange = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, Organization.class, organizationId);
        return restExchange.getBody();
    }
}
