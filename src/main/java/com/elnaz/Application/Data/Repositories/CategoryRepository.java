package com.elnaz.Application.Data.Repositories;

import com.elnaz.Application.Data.Enitites.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
/**
 * Repository interface for accessing and managing Category entities.
 *
 * This interface extends JpaRepository to provide CRUD operations and
 * includes a custom query method for finding categories by name.
 * It interacts directly with the database layer.
 */


public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);

}
