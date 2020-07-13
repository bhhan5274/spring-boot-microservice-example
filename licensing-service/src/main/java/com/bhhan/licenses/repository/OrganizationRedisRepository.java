package com.bhhan.licenses.repository;

import com.bhhan.licenses.model.Organization;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */
public interface OrganizationRedisRepository {
    void saveOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganization(String organizationId);
    Organization findOrganizationById(String organizationId);
}
