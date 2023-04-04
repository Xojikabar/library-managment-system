package com.example.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(
        name = "authorities",
        uniqueConstraints = @UniqueConstraint(name = "username_auth_unique",
                columnNames = {"username", "authority"})
)
@Entity
@Getter
@Setter
public class Authorities {
    @Id
    @GeneratedValue(generator = "auth_id_seq")
    @SequenceGenerator(name = "auth_id_seq", sequenceName = "auth_id_seq", allocationSize = 1, initialValue = 3)
    private Integer id;

    private String username;
    private String authority;
}
