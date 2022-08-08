package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.House;


public interface HouseService extends IService<House> {
    House findLastHouse();

    House save(Long id, House house) throws CustomException;

    Iterable<House> findByOwnerId (Long customer_id);

    Iterable<House> findTop5();
}
