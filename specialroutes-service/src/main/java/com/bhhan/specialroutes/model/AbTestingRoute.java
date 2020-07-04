package com.bhhan.specialroutes.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by hbh5274@gmail.com on 2020-07-04
 * Github : http://github.com/bhhan5274
 */

@Entity
@Table(name = "ABTESTINGROUTES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class AbTestingRoute {
    @Id
    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String active;

    @Column(nullable = false)
    private String endpoint;

    @Column(nullable = false)
    private Integer weight;
}
