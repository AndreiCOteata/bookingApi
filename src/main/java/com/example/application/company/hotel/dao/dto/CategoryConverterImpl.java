package com.example.application.company.hotel.dao.dto;

import com.example.application.company.hotel.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryConverterImpl{

    public CategoryDto createFrom(Category entity) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName(entity.getName());
        return categoryDto;
    }
}
