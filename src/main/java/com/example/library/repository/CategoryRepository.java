package com.example.library.repository;

import com.example.library.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Optional<Category> findFirstByGenreName (String genreName);
}
