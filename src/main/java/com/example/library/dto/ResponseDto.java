package com.example.library.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto <T>{

    private String message;
    private int code;
    private boolean success;
    T data;
}
