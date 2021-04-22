package com.telerikfinalproject.photocontest.models.dtomodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDto {

    @NotNull
    @Size(min = 2, max = 100, message = "Category should be between 2 and 100")
    private String categoryName;

    public CategoryDto() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
