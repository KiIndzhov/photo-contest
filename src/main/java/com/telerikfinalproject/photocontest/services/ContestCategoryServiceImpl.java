package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.models.ContestCategory;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestCategoryRepository;
import com.telerikfinalproject.photocontest.services.contracts.ContestCategoryService;
import com.telerikfinalproject.photocontest.services.mappers.CategoryModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContestCategoryServiceImpl implements ContestCategoryService {

    private final ContestCategoryRepository contestCategoryRepository;

    @Autowired
    public ContestCategoryServiceImpl(ContestCategoryRepository contestCategoryRepository) {
        this.contestCategoryRepository = contestCategoryRepository;

    }

    @Override
    public List<ContestCategory> getAllCategories() {
        return contestCategoryRepository.getAllCategories();
    }

    @Override
    public ContestCategory getCategoryById(int id) {
        return contestCategoryRepository.getCategoryById(id);
    }

    @Override
    public void addCategory(ContestCategory category) {
        if(exist(category)){
            throw new DuplicateEntityException("Category exist");
        }
        contestCategoryRepository.addCategory(category);
    }

    @Override
    public boolean exist(ContestCategory category) {
        return contestCategoryRepository.categoryExist(category);
    }
}
