package com.example.house_renting_md6.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IService<T>{
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(Long id);
    void save(T t);
    void remove(Long id);
}