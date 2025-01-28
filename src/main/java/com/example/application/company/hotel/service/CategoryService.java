package com.example.application.company.hotel.service;

import com.example.application.common.exceptions.ResourceNotFoundException;
import com.example.application.company.hotel.dao.CategoryRepository;
import com.example.application.company.hotel.dao.dto.CategoryConverterImpl;
import com.example.application.company.hotel.dao.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverterImpl categoryConverter;

    public CategoryDto getCategory(String name) {
        return categoryRepository.findByName(name)
                .map(categoryConverter::createFrom)
                .orElseThrow(() -> new ResourceNotFoundException("Category with name " + name +" not found"));
    }
}
