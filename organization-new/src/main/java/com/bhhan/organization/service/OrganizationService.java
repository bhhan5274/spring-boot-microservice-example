package com.bhhan.organization.service;

import com.bhhan.organization.model.Organization;
import com.bhhan.organization.repository.OrganizationRepository;
import com.bhhan.utils.usercontext.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by hbh5274@gmail.com on 2020-07-02
 * Github : http://github.com/bhhan5274
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public Organization getOrg(String organizationId){
        log.info("OrganizationService getOrg Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new IllegalArgumentException(organizationId + "를 찾을 수 없습니다."));
    }

    public void saveOrg(Organization organization){
        organization.setId(UUID.randomUUID().toString());
        organizationRepository.save(organization);
    }

    public void updateOrg(Organization organization){
        organizationRepository.save(organization);
    }

    public void deleteOrg(Organization organization){
        organizationRepository.delete(organization);
    }
}
