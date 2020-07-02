package com.bhhan.licenses.repository;

import com.bhhan.licenses.model.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */
public interface LicenseRepository extends JpaRepository<License, String> {
    List<License> findByOrganizationId(String organizationId);
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
