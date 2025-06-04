package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.Category;
import com.elnaz.Application.Data.Repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Initializes the password manager by allowing the user to view existing categories
 * and optionally create a new one. This method interacts with the user via the console
 * and updates the database accordingly.
 */

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepository categoryRepository;

    // Konstruktor-Injektion
    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name) {
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> listAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
