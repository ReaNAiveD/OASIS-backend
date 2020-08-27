package com.nju.oasislogin.domain;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@RedisHash
@Data
public class UserSso {

    @Id
    private String session;

    private Long expireFreshTime;

}
