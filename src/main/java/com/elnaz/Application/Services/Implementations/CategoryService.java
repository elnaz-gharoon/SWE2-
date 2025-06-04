package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
/**
 * Service interface for managing password categories.
 *
 * This interface provides methods to create new categories,
 * retrieve all existing categories, and search for a category by its name.
 * Implementations of this interface should handle all business logic
 * related to category management.
 */

public interface CategoryService {
    Category createCategory(String name);
    List<Category> listAllCategories();
    Optional<Category> findByName(String name);
}
