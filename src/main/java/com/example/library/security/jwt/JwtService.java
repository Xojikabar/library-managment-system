package com.example.library.security.jwt;

import com.example.library.dto.UsersDto;
import com.example.library.model.UserSession;
import com.example.library.repository.UserSessionRepository;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {


    @Autowired
    private Gson gson;

    @Autowired
    public UserSessionRepository userSessionRepository;

    @Value("${spring.security.secret-key}")
    private String secretKey;






    public String generateToken(UsersDto dto) {
        String uuid = String.valueOf(UUID.randomUUID());

        userSessionRepository.save(new UserSession(uuid,gson.toJson(dto)));
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4))
                .setSubject(gson.toJson(dto))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().getTime() < System.currentTimeMillis();
    }


    public UsersDto getSubject(String token){
        String usersDto = getClaims(token).getSubject();
        return gson.fromJson(usersDto, UsersDto.class);
    }


}
