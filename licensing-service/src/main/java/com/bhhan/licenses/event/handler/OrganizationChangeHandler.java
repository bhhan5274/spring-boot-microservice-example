package com.bhhan.licenses.event.handler;

import com.bhhan.licenses.event.CustomChannel;
import com.bhhan.licenses.event.model.OrganizationChangeModel;
import com.bhhan.licenses.repository.OrganizationRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */

@EnableBinding(CustomChannel.class)
@RequiredArgsConstructor
@Slf4j
public class OrganizationChangeHandler {
    private final OrganizationRedisRepository organizationRedisRepository;

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel orgChange){
        log.info("Received a message of type " + orgChange.getType());

        switch (orgChange.getAction()){
            case "GET":
                log.info("Received a GET event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "SAVE":
                log.info("Received a SAVE event from the organization service for organization id {}", orgChange.getOrganizationId());
                break;
            case "UPDATE":
                log.info("Received a UPDATE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            case "DELETE":
                log.info("Received a DELETE event from the organization service for organization id {}", orgChange.getOrganizationId());
                organizationRedisRepository.deleteOrganization(orgChange.getOrganizationId());
                break;
            default:
                log.info("Received an UNKNOWN event from the organization service of type {}", orgChange.getType());;
                break;
        }
    }
}
