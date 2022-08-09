package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.House;

import java.time.LocalDate;


public interface HouseService extends IService<House> {
    House findLastHouse();

    Iterable<House> findByOwnerId (Long customer_id);

    Iterable<House> findTop5();

    Iterable<House>findByManyThing(String address, int start, int end, int bathroom, int bedroom, LocalDate dateBegin, LocalDate dateEnd);
}
