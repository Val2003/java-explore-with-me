package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> postCategory(@RequestBody @Valid NewCategoryDto newCategoryDto) {

        log.info("[Admin Controller] received a request POST /admin/categories with name {}", newCategoryDto.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED).body(categoryService.addCategory(newCategoryDto));
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long categoryId,
                                      @RequestBody @Valid NewCategoryDto newCategoryDto) {

        log.info("[Admin Controller] received a request PATCH /admin/categories/{}", newCategoryDto.toString());
        return ResponseEntity
                .status(HttpStatus.OK).body(categoryService.update(categoryId, newCategoryDto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<HttpStatus>  deleteCategory(@PathVariable Long categoryId) {

        log.info("[AdminCategoryController] received a request DELETE /admin/categories/{}", categoryId);
        categoryService.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
