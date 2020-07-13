package com.bhhan.licenses.event.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
public class OrganizationChangeModel {
    private String type;
    private String action;
    private String organizationId;
    private String correlationId;

    @Builder
    public OrganizationChangeModel(String type, String action, String organizationId, String correlationId){
        this.type = type;
        this.action = action;
        this.organizationId = organizationId;
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "OrganizationChangeModel{" +
                "type='" + type + '\'' +
                ", action='" + action + '\'' +
                ", organizationId='" + organizationId + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
