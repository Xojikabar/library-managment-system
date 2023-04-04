package com.example.library.rest;

import com.example.library.dto.CategoryDto;
import com.example.library.dto.ResponseDto;
import com.example.library.model.Book;
import com.example.library.service.CateogryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CateogryService cateogryService;


    @PostMapping
    @PreAuthorize("hasAnyAuthority('CREATE','ROLE_ADMIN')")
    public ResponseDto<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
        return cateogryService.addCategory(categoryDto);
    }

    @PatchMapping()
    @PreAuthorize("hasAnyAuthority('UPDATE','ROLE_ADMIN')")

    private ResponseDto<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto) {
        return cateogryService.updateCategory(categoryDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('DELETE','ROLE_SEUPER_ADMIN')")

    public ResponseDto<Void> deleteCategory(@RequestParam String genre) {
        return cateogryService.deleteCategory(genre);
    }

    @GetMapping("get-all-genres")
    @PreAuthorize("hasAnyAuthority('READ')")

    public ResponseDto<Page<CategoryDto>> geAllCategories(@RequestParam String order) {
        return cateogryService.getAllCategories(order);
    }

    @GetMapping("get-all-genres-with-book")
    @PreAuthorize("hasAnyAuthority('READ')")

    private Map<String, List<Book>> getAllGenresWithBookName() {

        return cateogryService.getAllGenresWithBookName();
    }

}
