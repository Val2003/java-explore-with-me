package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAllCategories(Integer from, Integer size);

    CategoryDto getCategory(Long categoryId);

    CategoryDto addCategory(NewCategoryDto body);

    void delete(Long catId);

    CategoryDto update(Long categoryId, NewCategoryDto newCategoryDto);
}
