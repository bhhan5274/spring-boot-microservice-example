package com.bhhan.organization.event.source;

import com.bhhan.organization.event.model.OrganizationChangeModel;
import com.bhhan.utils.usercontext.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class SimpleSourceBean {
    private final Source source;

    public void publishOrgChange(String action, String orgId){
        log.info("Sending Kafka message {} for Organization Id: {}", action, orgId);
        final OrganizationChangeModel change = OrganizationChangeModel.builder()
                .type(OrganizationChangeModel.class.getTypeName())
                .action(action)
                .organizationId(orgId)
                .correlationId(UserContextHolder.getContext().getCorrelationId())
                .build();
        source.output().send(MessageBuilder.withPayload(change).build());
    }
}
