package com.elnaz.Application.Services.Implementations;

import com.elnaz.Application.Data.Enitites.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category createCategory(String name);
    List<Category> listAllCategories();
    Optional<Category> findByName(String name);
}
