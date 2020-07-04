package com.bhhan.zuulsvr.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfo {
    private String organizationId;
    private String userId;
}
