package com.bhhan.licenses.event;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */
public interface CustomChannel {
    @Input("inboundOrgChanges")
    SubscribableChannel orgs();
}
