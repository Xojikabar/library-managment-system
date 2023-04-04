package com.example.library.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.example.library.service.messages.AppStatusMessage.NULL_VALUE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDto {

    private Integer id;
    @NotBlank(message = NULL_VALUE)
//    @Column(unique=true)

    private String genreName;
}
