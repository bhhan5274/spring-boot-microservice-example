package com.bhhan.licenses.controller;

import com.bhhan.licenses.config.ServiceConfig;
import com.bhhan.licenses.model.License;
import com.bhhan.licenses.service.LicenseService;
import com.bhhan.utils.usercontext.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@RestController
@RequestMapping(value = "/v1/organizations/{organizationId}/licenses")
@RequiredArgsConstructor
@Slf4j
public class LicenseServiceController {
    private final LicenseService licenseService;

    @GetMapping("/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId){
        log.info("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return licenseService.getLicensesByOrg(organizationId);
    }

    @GetMapping("/{licenseId}")
    public License getLicenses(@PathVariable("organizationId") String organizationId,
                               @PathVariable("licenseId") String licenseId){
        return licenseService.getLicense(organizationId, licenseId);
    }

    @GetMapping("/{licenseId}/{clientType}")
    public License getLicenseWithClient(@PathVariable("organizationId") String organizationId,
                                        @PathVariable("licenseId") String licenseId,
                                        @PathVariable("clientType") String clientType){
        return licenseService.getLicense(organizationId, licenseId, clientType);
    }

    @PutMapping("/{licenseId}")
    public String updateLicenses(@PathVariable("licenseId") String licenseId){
        return "This is the put";
    }

    @PostMapping("/")
    public void saveLicenses(@RequestBody License license){
        licenseService.saveLicense(license);
    }

    @DeleteMapping("/{licenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteLicenses(@PathVariable("licenseId") String licenseId){
        return "This is the Delete";
    }
}
