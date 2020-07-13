package com.bhhan.licenses.client;

import com.bhhan.licenses.model.Organization;
import com.bhhan.licenses.repository.OrganizationRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class OrganizationRestTemplateClient {
    private final RestTemplate restTemplate;
    private final OrganizationRedisRepository organizationRedisRepository;

    public Organization getOrganization(String organizationId){

        Organization org = checkRedisCache(organizationId);

        if(org != null){
            log.info("I have successfully retrieved an organization {} from the redis cache: {}", organizationId, org);
            return org;
        }

        log.info("Unable to locate organization from the redis cache: {}.", organizationId);

        final ResponseEntity<Organization> restExchange = restTemplate.exchange("http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null,
                Organization.class,
                organizationId);

        org = restExchange.getBody();

        if(org != null){
            cacheOrganizationObject(org);
        }

        return org;
    }

    private Organization checkRedisCache(String organizationId){
        try{
            return organizationRedisRepository.findOrganizationById(organizationId);
        }catch(Exception e){
            log.info("Error encountered while trying to retrieve organization {} check Redis Cache.", organizationId);
            return null;
        }
    }

    private void cacheOrganizationObject(Organization org){
        try{
            organizationRedisRepository.saveOrganization(org);
        }catch(Exception e){
            log.info("Unable to cache organization {} in Redis. Exception {}", org.getId(), e.getMessage());
        }
    }
}
