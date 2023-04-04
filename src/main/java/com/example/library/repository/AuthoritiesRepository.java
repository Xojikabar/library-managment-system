package com.example.library.repository;

import com.example.library.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthoritiesRepository extends JpaRepository<Authorities,Integer> {
}
