package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.House;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface HouseService extends IService<House> {
    House findLastHouse();

    House save(Long id, House house) throws CustomException;

    Iterable<House> findByOwnerId (Long customer_id);

    Iterable<House> findTop5();

//    Iterable<House>findByManyThing(String address, int start, int end, int bathroom, int bedroom, String dateBegin, String dateEnd, Pageable pageable);
}
