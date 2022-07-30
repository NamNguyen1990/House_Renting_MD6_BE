package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.House;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HouseService extends IService<House>{
//    Iterable<House> findAllByCategory_Id(Long id);
    Page<House> findAllByCategory_Id(Long id, Pageable pageable);
    Iterable<House> findByOwnerId (Long owner_id);
    House findLastHouse();

    Page<House> findAllByBedroom(int bedroom,Pageable pageable);

    Page<House>findAllByBathroom (int bathroom,Pageable pageable);
    Page<House> findByCategory (int category, Pageable pageable);

    Iterable<House> findTop2();




    Page<House>findAllByBathroomAndBedroom(int bathroom,int bedroom,Pageable pageable);

//    Page<House>findAllByStatus(int status,Pageable pageable);

    Iterable<House>findByAll(String address, int start, int end, int bathroom, int bedroom, LocalDate cus_begin, LocalDate cus_end);
}
