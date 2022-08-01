package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
