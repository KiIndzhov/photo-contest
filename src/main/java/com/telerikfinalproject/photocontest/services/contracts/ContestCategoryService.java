package com.telerikfinalproject.photocontest.services.contracts;

import com.telerikfinalproject.photocontest.models.ContestCategory;

import java.util.List;

public interface ContestCategoryService {
    List<ContestCategory> getAllCategories();

    ContestCategory getCategoryById(int id);

    void addCategory(ContestCategory category);

    boolean exist(ContestCategory category);
}
