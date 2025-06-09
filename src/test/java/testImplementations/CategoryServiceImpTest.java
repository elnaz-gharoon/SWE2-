package testImplementations;


import com.elnaz.Application.Data.Enitites.Category;
import com.elnaz.Application.Data.Repositories.CategoryRepository;
import com.elnaz.Application.Services.Implementations.CategoryServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    class CategoryServiceImpTest {

        @Mock
        private CategoryRepository categoryRepository;

        @InjectMocks
        private CategoryServiceImp categoryService;


    // Checks if all categories are returned from the repository
        @Test
        void listAllCategories_shouldReturnAllCategories() {
            List<Category> categories = List.of(new Category("Work"), new Category("Personal"));

            when(categoryRepository.findAll()).thenReturn(categories);

            List<Category> result = categoryService.listAllCategories();

            assertEquals(2, result.size());
        }

    // Checks if a category is found by its name"
        @Test
        void findByName_shouldReturnCategoryIfExists() {
            Category category = new Category("Travel");

            when(categoryRepository.findByName("Travel")).thenReturn(Optional.of(category));

            Optional<Category> result = categoryService.findByName("Travel");

            assertTrue(result.isPresent());
            assertEquals("Travel", result.get().getName());
        }
    }


