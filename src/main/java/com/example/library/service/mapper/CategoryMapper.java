package com.example.library.service.mapper;

import com.example.library.dto.CategoryDto;
import com.example.library.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends CommonMapper<CategoryDto, Category> {
}
