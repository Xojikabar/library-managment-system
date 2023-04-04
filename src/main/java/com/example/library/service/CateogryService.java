package com.example.library.service;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.ResponseDto;
import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.repository.BookRepository;
import com.example.library.repository.CategoryRepository;
import com.example.library.repository.CategoryRepositoryImpl;
import com.example.library.service.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.library.service.messages.AppStatusCode.*;
import static com.example.library.service.messages.AppStatusMessage.*;

@Service
@RequiredArgsConstructor
public class CateogryService {

    private final CategoryRepositoryImpl categoryRepositoryImpl;
    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {

        try {
            categoryRepository.save(categoryMapper.toEntity(categoryDto));
            return ResponseDto.<CategoryDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<CategoryDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .data(categoryDto)
                    .build();
        }

    }

    public ResponseDto<Page<CategoryDto>> getAllCategories(String order) {

        if (order.toLowerCase().equals("asc")) {
            return ResponseDto.<Page<CategoryDto>>builder()
                    .data(categoryRepository.findAll(PageRequest.of(0, 20, Sort.by("genreName").ascending())).map(categoryMapper::toDto))
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        }
        return ResponseDto.<Page<CategoryDto>>builder()
                .data(categoryRepository.findAll(PageRequest.of(1, 20, Sort.by("genreName").descending())).map(categoryMapper::toDto))
                .message(OK)
                .code(OK_CODE)
                .success(true)
                .build();


//        Page<CategoryDto> page =  categoryRepositoryImpl.getAllCategories();
    }

    public ResponseDto<CategoryDto> updateCategory(CategoryDto categoryDto) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryDto.getId());

        if (categoryOptional.isEmpty()) {
            return ResponseDto.<CategoryDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .data(categoryDto)
                    .build();
        }

        try {
            Category category = categoryOptional.get();
            if (categoryDto.getGenreName() != null) {
                category.setGenreName(categoryDto.getGenreName());
            }
            categoryRepository.save(category);
            return ResponseDto.<CategoryDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .build();
        } catch (Exception e) {

            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .data(categoryDto)
                    .message(DATABASE_ERROR)
                    .build();
        }
    }


    public ResponseDto<Void> deleteCategory(String genre) {

        Optional<Category> categoryOptional = categoryRepository.findFirstByGenreName(genre);
        if (categoryOptional.isEmpty()) {
            return ResponseDto.<Void>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_CODE)
                    .build();
        }


        try {
            categoryRepository.deleteById(categoryOptional.get().getId());
            return ResponseDto.<Void>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Void>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }


    public Map<String, List<Book>> getAllGenresWithBookName() {
        List<Category> categories = categoryRepository.findAll();

        Stream<List<Book>> listStream = categories.stream().map(c -> bookRepository.findAllByGenre(c.getGenreName()));

        return   categories.stream()
                .collect(Collectors.toMap(
                        Category::getGenreName,
                        c -> bookRepository.findAllByGenre(c.getGenreName())
                ));

//        Map<String, List<String>> map = categories.stream()
//                .collect(Collectors.toMap(
//                        Category::getGenreName,
//                        c -> bookRepository.findAllByGenre(c.getGenreName())
//                                .stream()
//                                .map(Book::getName)
//                                .collect(Collectors.toList())
//                ));



    }
}
