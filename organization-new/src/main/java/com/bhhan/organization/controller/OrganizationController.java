package com.bhhan.organization.controller;

import com.bhhan.organization.model.Organization;
import com.bhhan.organization.service.OrganizationService;
import com.bhhan.utils.usercontext.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hbh5274@gmail.com on 2020-07-02
 * Github : http://github.com/bhhan5274
 */

@RestController
@RequestMapping(value = "/v1/organizations")
@RequiredArgsConstructor
@Slf4j
public class OrganizationController {
    private final OrganizationService organizationService;

    @GetMapping("/{organizationId}")
    public Organization getOrganization(@PathVariable("organizationId") String organizationId){
        log.info("OrganizationController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        final Organization org = organizationService.getOrg(organizationId);
        org.setContactName("NEW::" + org.getContactName());
        return org;
    }

    @PutMapping("/{organizationId}")
    public void updateOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization){
        organizationService.updateOrg(organization);
    }

    @PostMapping("/{organizationId}")
    public void saveOrganization(@RequestBody Organization organization){
        organizationService.saveOrg(organization);
    }

    @DeleteMapping("/{organizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@PathVariable("organizationId") String organizationId, @RequestBody Organization organization){
        organizationService.deleteOrg(organization);
    }
}
