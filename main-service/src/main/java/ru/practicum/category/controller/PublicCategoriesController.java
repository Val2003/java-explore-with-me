package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class PublicCategoriesController {
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestParam(defaultValue = "0") Integer from,
                                                              @RequestParam(defaultValue = "10") Integer size) {

        log.info("Calling GET: /categories with 'from': {}, 'size': {}", from, size);
        return ResponseEntity
                .status(HttpStatus.OK).body(categoryService.getAllCategories(from, size));
    }

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId)  {

        log.info("Calling GET: /categories/{categoryId} with 'categoryId': {}", categoryId);
        return ResponseEntity
                .status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
    }
}