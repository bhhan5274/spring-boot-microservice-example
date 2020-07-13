package com.bhhan.licenses.repository;

import com.bhhan.licenses.model.Organization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

/**
 * Created by hbh5274@gmail.com on 2020-07-13
 * Github : http://github.com/bhhan5274
 */

@Repository
@RequiredArgsConstructor
public class OrganizationRedisRepositoryImpl implements OrganizationRedisRepository {

    private static final String HASH_NAME = "organization";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @PostConstruct
    private void init(){
        this.hashOperations =  redisTemplate.opsForHash();
    }

    @Override
    public void saveOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void updateOrganization(Organization org) {
        hashOperations.put(HASH_NAME, org.getId(), org);
    }

    @Override
    public void deleteOrganization(String organizationId) {
        hashOperations.delete(HASH_NAME, organizationId);
    }

    @Override
    public Organization findOrganizationById(String organizationId) {
        return (Organization) hashOperations.get(HASH_NAME, organizationId);
    }
}
