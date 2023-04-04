package com.example.library.dto;

import com.example.library.security.UserAuthorities;
import com.example.library.security.UserRoles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
//@JsonIgnoreProperties(value = {"password", "role", "passportId", "username"}, allowSetters = true)
public class UsersDto implements UserDetails {

    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String passportId;
    private String password;
    private String role ;
    private short isActive;
    private Boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println(UserRoles.valueOf(role).toString());
      return   UserRoles.valueOf(role)
                .getAuthorities().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername(){
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
