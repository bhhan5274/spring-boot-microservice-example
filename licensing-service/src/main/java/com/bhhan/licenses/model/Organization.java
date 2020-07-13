package com.bhhan.licenses.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by hbh5274@gmail.com on 2020-07-01
 * Github : http://github.com/bhhan5274
 */

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Organization implements Serializable {
    String id;
    String name;
    String contactName;
    String contactEmail;
    String contactPhone;
}
