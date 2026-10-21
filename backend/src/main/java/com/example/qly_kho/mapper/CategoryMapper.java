package com.example.qly_kho.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.qly_kho.dto.response.category.CategoryResponse;
import com.example.qly_kho.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "id", target = "categoryId")
    CategoryResponse toCategoryResponse(Category category);
}
