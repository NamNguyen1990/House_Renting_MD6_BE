package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.Category;

public interface CategoryService extends IService<Category>{
    Iterable<Category> findAllCategory();
}
