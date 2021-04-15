package com.telerikfinalproject.photocontest.services.mappers;

import com.telerikfinalproject.photocontest.models.ContestCategory;
import com.telerikfinalproject.photocontest.models.dtomodels.CategoryDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryModelMapper {

    public ContestCategory dtoToCategory(CategoryDto categoryDto) {
        ContestCategory category = new ContestCategory();
        category.setCategory(categoryDto.getCategoryName());
        return category;
    }
}
