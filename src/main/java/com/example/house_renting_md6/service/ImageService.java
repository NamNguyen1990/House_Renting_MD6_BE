package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.Image;

import java.util.Optional;

public interface ImageService extends IService<Image>{
    Iterable<Image> findAll();

    Iterable<Image> findAllImg();
    Iterable<Image> findByHouse(Long id);

    Optional<Image> findCardByHouse(Long id);
}
