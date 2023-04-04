package com.example.library.service;

import com.example.library.dto.LoginDto;
import com.example.library.dto.ResponseDto;
import com.example.library.dto.UsersDto;
import com.example.library.model.Users;
import com.example.library.repository.UsersRepository;
import com.example.library.security.jwt.JwtService;
import com.example.library.service.mapper.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.library.service.messages.AppStatusCode.*;
import static com.example.library.service.messages.AppStatusMessage.*;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersMapper usersMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;

    public ResponseDto<UsersDto> addUser(UsersDto dto) {
        Optional<Users> user = usersRepository.findFirstByEmail(dto.getEmail());

        if (user.isPresent()) {
            return ResponseDto.<UsersDto>builder()
                    .message(DUPLICATE_ERROR)
                    .code(DUPLICATE_ERROR_CODE)
                    .data(dto)
                    .build();
        }

        Users users = usersMapper.toEntity(dto);
        users.setIsActive((short) 1);


        try {

            usersRepository.save(users);
            return ResponseDto.<UsersDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(dto)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .message("DB error")
                    .code(-1)
                    .build();
        }
    }

    public ResponseDto<UsersDto> updateUser(UsersDto usersDto) {

        Optional<Users> users = usersRepository.findFirstByEmail(usersDto.getEmail());

        if (users.isEmpty()) {
            return ResponseDto.<UsersDto>builder()
                    .message("User not found with this email: " + usersDto.getEmail())
                    .code(-1)
                    .data(usersDto)
                    .build();
        }

        Users user = users.get();
        user.setIsActive((short) 1);


        if (usersDto.getUsername() != null) {
            user.setUsername(usersDto.getUsername());
        }
        if (usersDto.getPhoneNumber() != null) {
            user.setPhoneNumber(usersDto.getPhoneNumber());
        }
        if (usersDto.getPassword() != null) {
            user.setPassword(usersDto.getPassword());
        }
        if (usersDto.getPassportId() != null) {
            user.setPassportId(usersDto.getPassportId());
        }

        try {
            usersRepository.save(user);
            return ResponseDto.<UsersDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(usersMapper.toDto(user))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UsersDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .data(usersDto)
                    .build();
        }
    }

    public ResponseDto<List<UsersDto>> getALlUsers() {
        return ResponseDto.<List<UsersDto>>builder()
                .data(usersRepository.findAll().stream().filter(u -> u.getIsActive() == 1).map(usersMapper::toDto).toList())
                .success(true)
                .message(OK)
                .code(OK_CODE)
                .build();
    }

    @Override
    public UsersDto loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findFirstByEmail(email);
        if (optionalUsers.isEmpty())
            throw new UsernameNotFoundException("User with this " + email + " email is not found");
        return usersMapper.toDto(optionalUsers.get());
    }

    public ResponseDto<String> login(LoginDto loginDto) {
        UsersDto optionalUsers = loadUserByUsername(loginDto.getUsername());
        if (!passwordEncoder.matches(loginDto.getPassword(), optionalUsers.getPassword())) {
            return ResponseDto.<String>builder()
                    .data("Password is incorrect: " + loginDto)
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }


        return ResponseDto.<String>builder()
                .data(jwtService.generateToken(optionalUsers))
                .success(true)
                .message(OK)
                .build();

    }
}
