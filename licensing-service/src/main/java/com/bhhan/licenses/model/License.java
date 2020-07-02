package com.bhhan.licenses.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name = "LICENSES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class License {
    @Id
    @Column(name = "LICENSE_ID", nullable = false)
    private String licenseId;

    @Column(nullable = false)
    private String organizationId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String licenseType;

    @Column(nullable = false)
    private Integer licenseMax;

    @Column(nullable = false)
    private Integer licenseAllocated;

    private String comment;

    @Transient
    private String organizationName ="";

    @Transient
    private String contactName ="";

    @Transient
    private String contactPhone ="";

    @Transient
    private String contactEmail ="";

    public License withId(String id){
        this.setLicenseId(id);
        return this;
    }

    public License withComment(String comment){
        this.setComment(comment);
        return this;
    }

    private void setLicenseId(String id){
        this.licenseId = id;
    }

    private void setComment(String comment){
        this.comment = comment;
    }

    @Builder
    public License(String licenseId, String organizationId, String productName,
                   String licenseType, Integer licenseMax, Integer licenseAllocated,
                   String comment, String organizationName, String contactName, String contactPhone, String contactEmail){
        this.licenseId = licenseId;
        this.organizationId = organizationId;
        this.productName = productName;
        this.licenseType = licenseType;
        this.licenseMax = licenseMax;
        this.licenseAllocated = licenseAllocated;
        this.comment = comment;
        this.organizationName = organizationName;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    public License addOrgInfo(Organization organization) {
        this.organizationName = organization.getName();
        this.contactName = organization.getContactName();
        this.contactPhone = organization.getContactPhone();
        this.contactEmail = organization.getContactEmail();

        return this;
    }
}
