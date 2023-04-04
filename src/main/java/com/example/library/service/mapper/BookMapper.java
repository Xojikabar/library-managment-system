package com.example.library.service.mapper;

import com.example.library.dto.BookDto;
import com.example.library.dto.UsersDto;
import com.example.library.model.Book;
import com.example.library.model.Users;
import com.example.library.service.BookService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Mapper(componentModel = "spring")
public abstract class BookMapper implements CommonMapper<BookDto, Book> {

//    @Autowired
//    @Lazy
//
//    protected BookService bookService;
//
//    @Mapping(target = "genreList", expression = "java(bookService.findBookGenreFromCategory(d.getGenres()))")
//    public abstract Book toEntity(BookDto d);
}
