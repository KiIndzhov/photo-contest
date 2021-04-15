package com.telerikfinalproject.photocontest.repositories.contracts;

import com.telerikfinalproject.photocontest.models.ContestCategory;

import java.util.List;

public interface ContestCategoryRepository {

    List<ContestCategory> getAllCategories();

    ContestCategory getCategoryById(int id);

    void addCategory(ContestCategory category);

    boolean categoryExist(ContestCategory category);
}
