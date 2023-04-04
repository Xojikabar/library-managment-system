package com.example.library.security;

import java.util.ArrayList;
import java.util.List;

import static com.example.library.security.UserAuthorities.*;

public enum UserRoles {
    ADMIN(List.of(READ,UPDATE,CREATE)),
    MODERATOR(List.of(READ,CREATE)),
    USER(List.of(READ)),
    SUPER_ADMIN(List.of(READ,UPDATE,CREATE,DELETE));
    List<UserAuthorities> authorities;

    UserRoles(List<UserAuthorities> authorities) {
        this.authorities = authorities;
    }

    public List<String> getAuthorities(){
        List<String> list = new ArrayList<>(this.authorities.stream()
                .map(UserAuthorities::getName)
                .toList());
        list.add("ROLE_" + this.name());

        return list;
    }
}
