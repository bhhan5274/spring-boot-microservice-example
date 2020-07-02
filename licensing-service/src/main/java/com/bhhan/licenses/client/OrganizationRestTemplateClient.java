package com.bhhan.licenses.client;

import com.bhhan.licenses.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by hbh5274@gmail.com on 2020-07-02
 * Github : http://github.com/bhhan5274
 */

@Component
@RequiredArgsConstructor
public class OrganizationRestTemplateClient {
    private final RestTemplate restTemplate;

    public Organization getOrganization(String organizationId){
        final ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);

        return restExchange.getBody();
    }
}
