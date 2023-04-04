package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Book {

    @Id
    @SequenceGenerator(name = "bookSeq",sequenceName = "bookSequence",initialValue = 1,allocationSize = 1)
    @GeneratedValue(generator = "bookSeq")
    private Integer id;
    private String name;
    private String author;
    private Date releaseDate;
    private Integer amount;
    private Boolean isAvailable;
    private String genre;
//    @ManyToOne
//    @JoinColumn(name = "category_id")
//    private Category categories;
}
