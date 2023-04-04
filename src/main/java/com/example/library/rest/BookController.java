package com.example.library.rest;

import com.example.library.dto.BookDto;
import com.example.library.dto.CategoryDto;
import com.example.library.dto.ResponseDto;
import com.example.library.model.Book;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE','ROLE_ADMIN')")
    public ResponseDto<BookDto> addBook(@RequestBody BookDto bookDto) {
        System.out.println(bookDto);
        return bookService.addBook(bookDto);
    }

    @PatchMapping
    @PreAuthorize("hasAnyAuthority('UPDATE','ROLE_ADMIN','ROLE_SUPER_ADMIN')")

    public ResponseDto<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }


    @DeleteMapping()
    @PreAuthorize("hasAnyAuthority('DELETE','ROLE_SUPER_ADMIN')")

    public ResponseDto<BookDto> deleteBook() {
        return null;
    }


    @GetMapping("all-books")
    @PreAuthorize("hasAnyAuthority('READ')")
    public ResponseDto<List<BookDto>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("search")
    @PreAuthorize("hasAnyAuthority('READ')")

    public List<BookDto> universalSearch(@RequestParam Map<String, String> map) {
        return bookService.universalSearch(map);
    }

    @GetMapping("search-by-date")
    @PreAuthorize("hasAnyAuthority('READ')")

    public List<BookDto> searchByDate(@RequestParam Date firstDate, Date secondDate) {
        return bookService.searchByDate(firstDate, secondDate);
    }

    @GetMapping("get-book-by-genre")
    @PreAuthorize("hasAnyAuthority('READ')")

    public ResponseDto<List<Book>> getBookByGenre(@RequestParam String genre) {
        return bookService.getBookByGenre(genre);
    }

//    @GetMapping("get-all-genres-with-book")
//    private Map<String , List<Book>> getAllGenresWithBookName(){
//
//    }

}
