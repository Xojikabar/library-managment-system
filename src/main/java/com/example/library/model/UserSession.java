package com.example.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@RedisHash(timeToLive = 60*60*4)
public class UserSession {

    @Id
    private String uuid;
    private String token;

    public UserSession(String uuid, String toJson) {
    }
}
