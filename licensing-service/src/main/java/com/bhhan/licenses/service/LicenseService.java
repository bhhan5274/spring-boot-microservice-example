package com.bhhan.licenses.service;

import com.bhhan.licenses.client.OrganizationDiscoveryClient;
import com.bhhan.licenses.client.OrganizationFeignClient;
import com.bhhan.licenses.client.OrganizationRestTemplateClient;
import com.bhhan.licenses.config.ServiceConfig;
import com.bhhan.licenses.model.License;
import com.bhhan.licenses.model.Organization;
import com.bhhan.licenses.repository.LicenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final ServiceConfig config;
    private final OrganizationFeignClient organizationFeignClient;
    private final OrganizationRestTemplateClient organizationRestTemplateClient;
    private final OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String organizationId, String licenseId){
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
        return license.withComment(config.getExampleProperty());
    }

    public List<License> getLicensesByOrg(String organizationId){
        return licenseRepository.findByOrganizationId(organizationId);
    }

    public License getLicense(String organizationId, String licenseId, String clientType){
        final License license = licenseRepository.findByOrganizationIdAndLicenseId(organizationId, licenseId);

        final Organization organization = retreiveOrgInfo(organizationId, clientType);

        return license.addOrgInfo(organization);
    }

    public void saveLicense(License license){
        license.withId(UUID.randomUUID().toString());
        licenseRepository.save(license);
    }

    private Organization retreiveOrgInfo(String organizationId, String clientType){
        Organization organization = null;

        switch (clientType){
            case "feign":
                log.info("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                log.info("I am using the rest client");
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case "discovery":
                log.info("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
            default:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
        }

        return organization;
    }

    public void updateLicense(License license){
        licenseRepository.save(license);
    }

    public void deleteLicense(License license){
        licenseRepository.delete(license);
    }
}
