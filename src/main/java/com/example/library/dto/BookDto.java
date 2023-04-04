package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.NamedQuery;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static com.example.library.service.messages.AppStatusMessage.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDto  {
    private Integer id;
    @NotBlank(message = NULL_VALUE)
    private String name;
    @NotBlank(message = NULL_VALUE)
    private String author;

    private Date releaseDate;
    @Size(min = 1)
    @NotBlank(message = NULL_VALUE)
    private Integer amount;
    private Boolean isAvailable;
    private String genre;
}
