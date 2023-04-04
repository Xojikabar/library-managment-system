package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "genreName"}) })
public class Category {

    @Id
    @SequenceGenerator(name = "categorySeq", sequenceName = "categorySequence", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "categorySeq")
    private Integer id;

    private String genreName;
//    @ManyToMany(mappedBy = "categories")
//    private Set<Book> book = new HashSet<>();

}