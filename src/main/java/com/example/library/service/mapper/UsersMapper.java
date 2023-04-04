package com.example.library.service.mapper;

import com.example.library.dto.UsersDto;
import com.example.library.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users> {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(dto.getPassword()))")
    @Mapping(target = "enabled", expression = "java(true)")
//    @Mapping(target = "role", expression = "java('USER')")
    public abstract Users toEntity(UsersDto dto);
}
