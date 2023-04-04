package com.example.library.security.jwt;

import com.example.library.dto.ResponseDto;
import com.example.library.dto.UsersDto;
import com.example.library.model.Users;
import com.example.library.service.messages.AppStatusCode;
import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final Gson gson;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")){


            String token = authorization.substring(7);
            if(jwtService.isExpired(token)){
                response.getWriter().println(gson.toJson(ResponseDto.builder()
                        .code(AppStatusCode.VALIDATION_ERROR_CODE)
                        .message("Token is expired!")
                        .build()));
                response.setContentType("application/json");
                response.setStatus(400);
            }
            else {
                UsersDto usersDto = jwtService.getSubject(token);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usersDto,null,usersDto.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }
}
