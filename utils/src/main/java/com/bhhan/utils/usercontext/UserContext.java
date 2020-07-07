package com.bhhan.utils.usercontext;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by hbh5274@gmail.com on 2020-07-03
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
public class UserContext {
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "tmx-user-id";
    public static final String ORG_ID         = "tmx-org-id";

    private String correlationId;
    private String authToken;
    private String userId;
    private String orgId;
}
