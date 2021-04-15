package com.telerikfinalproject.photocontest.services;

import com.telerikfinalproject.photocontest.exceptions.DuplicateEntityException;
import com.telerikfinalproject.photocontest.models.ContestCategory;
import com.telerikfinalproject.photocontest.repositories.contracts.ContestCategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telerikfinalproject.photocontest.services.Helpers.createCategory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContestCategoryServiceImplTests {

    @Mock
    ContestCategoryRepository contestCategoryRepository;

    @InjectMocks
    ContestCategoryServiceImpl contestCategoryService;

    @Test
    public void getAllCategories_Should_Call_Repository_WhenValid() {
        contestCategoryService.getAllCategories();

        verify(contestCategoryRepository, times(1)).getAllCategories();
    }

    @Test
    public void getCategoryById_Should_Call_Repository_WhenValid() {
        contestCategoryService.getCategoryById(Mockito.anyInt());

        verify(contestCategoryRepository, times(1)).getCategoryById(Mockito.anyInt());
    }

    @Test
    public void addCategory_Should_Call_Repository_WhenValid() {
        ContestCategory category = createCategory();

        when(contestCategoryRepository.categoryExist(category)).thenReturn(false);
        contestCategoryService.addCategory(category);

        verify(contestCategoryRepository, times(1)).addCategory(category);

    }

    @Test
    public void addCategory_Should_Throw_When_CategoryExist() {

        ContestCategory category = createCategory();
        when(contestCategoryRepository.categoryExist(category)).thenReturn(true);

        assertThrows(DuplicateEntityException.class, () -> contestCategoryService.addCategory(category));
    }


}
