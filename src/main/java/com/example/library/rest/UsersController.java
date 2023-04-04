package com.example.library.rest;

import com.example.library.dto.LoginDto;
import com.example.library.dto.ResponseDto;
import com.example.library.dto.UsersDto;
import com.example.library.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;

    @PostMapping
    public ResponseDto<UsersDto> addUser(@RequestBody UsersDto dto){
        System.out.println(dto);
        return usersService.addUser(dto);
    }
    @PostMapping("login")
    public ResponseDto<String > login(@RequestBody LoginDto loginDto){
        return usersService.login(loginDto);
    }


    @PatchMapping
    @PreAuthorize("hasAnyAuthority('READ')")

    public ResponseDto<UsersDto> updateUser(@RequestBody UsersDto dto){
        return usersService.updateUser(dto);
    }

    @GetMapping("all-users")
    @PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN')")
    public ResponseDto<List<UsersDto>> getAllUsers(){
        return usersService.getALlUsers();
    }

    @GetMapping("local")
    public List<String> getLocal(){
        List<String > stringList = new ArrayList<>();
        stringList.add(LocaleContextHolder.getLocale().toString());
        stringList.add(LocaleContextHolder.getTimeZone().toString());
        stringList.add(LocaleContextHolder.getLocale().getDisplayLanguage());
        return stringList;
//        return  LocaleContextHolder.getLocale().getCountry();

    }
}
