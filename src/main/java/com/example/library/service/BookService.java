package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.ResponseDto;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BookRepositoryImpl;
import com.example.library.repository.CategoryRepository;
import com.example.library.service.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.library.service.messages.AppStatusCode.*;
import static com.example.library.service.messages.AppStatusMessage.*;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepositoryImpl bookRepositoryImpl;
    private final CategoryRepository categoryRepository;


    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public ResponseDto<BookDto> addBook(BookDto bookDto) {
        Optional<Book> bookOptional = bookRepository.findFirstByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());


        if (bookOptional.isPresent()) {
            return ResponseDto.<BookDto>builder()
                    .message(DUPLICATE_ERROR)
                    .code(DUPLICATE_ERROR_CODE)
                    .data(bookDto)
                    .build();
        }

        Book book = bookMapper.toEntity(bookDto);
        System.out.println(book);
        if (book.getAmount() > 0) book.setIsAvailable(true);
        try {
            bookRepository.save(book);
            return ResponseDto.<BookDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DUPLICATE_ERROR_CODE)
                    .data(bookDto)
                    .build();
        }
    }



    public ResponseDto<BookDto> updateBook(BookDto bookDto) {

        Optional<Book> optionalBook = bookRepository.findFirstByNameAndAuthor(bookDto.getName(), bookDto.getAuthor());

        if (optionalBook.isEmpty()) {
            return ResponseDto.<BookDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(bookDto)
                    .build();
        }

        Book book = optionalBook.get();

        if (bookDto.getAmount() != null && bookDto.getAmount() > 0) {
            book.setAmount(bookDto.getAmount());
            book.setIsAvailable(true);
        }
        if (bookDto.getReleaseDate() != null) {
            book.setReleaseDate(new Date(bookDto.getReleaseDate().getTime()));
        }

        try {
            bookRepository.save(book);
            return ResponseDto.<BookDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DUPLICATE_ERROR_CODE)
                    .data(bookDto)
                    .build();
        }

    }

    public ResponseDto<List<BookDto>> getAllBooks() {
        return ResponseDto.<List<BookDto>>builder()
                .data(bookRepository.findAll().stream().map(bookMapper::toDto).toList())
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .build();
    }

    public List<BookDto> universalSearch(Map<String, String> map) {
        return bookRepositoryImpl.universalSearch(map).stream().filter(u -> u.getIsAvailable()).map(bookMapper::toDto).toList();
    }

    public List<BookDto> searchByDate(Date firstDate, Date secondDate) {
        return bookRepository.findAllByReleaseDateAfterAndReleaseDateLessThanEqual(firstDate, secondDate).stream().map(bookMapper::toDto).toList();
    }


    public List<Integer> findBookGenreFromCategory(String genre) {
        List<Integer> list = new ArrayList<>();

        for (String s : genre.split(",")) {
            Optional<Category> category = categoryRepository.findFirstByGenreName(s);
            if (category.isPresent()) {
            System.out.println(category.get().toString());
                list.add(category.get().getId());
            }
        }
        return list;
    }

    public ResponseDto<List<Book>> getBookByGenre(String genre) {
       List<Book> optionalBook = bookRepository.findAllByGenre(genre);

       if(optionalBook.size() == 0){
           return ResponseDto.<List<Book>>builder()
                   .code(NOT_FOUND_CODE)
                   .message(NOT_FOUND)
                   .build();

       }

       return ResponseDto.<List<Book>>builder()
               .data(optionalBook)
               .code(OK_CODE)
               .message(OK)
               .success(true)
               .build();

    }
}
