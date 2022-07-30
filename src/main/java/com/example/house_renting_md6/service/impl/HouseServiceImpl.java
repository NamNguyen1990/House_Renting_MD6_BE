package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.repository.HouseRepository;
import com.example.house_renting_md6.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    HouseRepository houseRepository;

    @Override
    public Page<House> findAll(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Override
    public Optional<House> findById(Long id) {
        return houseRepository.findById(id);
    }

    @Override
    public Iterable<House> findAll() {
        return null;
    }

    @Override
    public void save(House house) {

    }

    @Override
    public void remove(Long id) {

    }
//
//    @Override
//    public Iterable<House> findAll() {
//        return null;
//    }
//
//    @Override
//    public void save(House house) {
//
//    }
//
//    @Override
//    public void remove(Long id) {
//
//    }
//
//
//    @Override
//    public Page<House> findAllByCategory_Id(Long id, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Iterable<House> findByOwnerId(Long owner_id) {
//        return null;
//    }
//
//    @Override
//    public House findLastHouse() {
//        return null;
//    }
//
//    @Override
//    public Page<House> findAllByBedroom(int bedroom, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Page<House> findAllByBathroom(int bathroom, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Page<House> findByCategory(int category, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Iterable<House> findTop2() {
//        return null;
//    }
//
//    @Override
//    public Page<House> findAllByBathroomAndBedroom(int bathroom, int bedroom, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Iterable<House> findByAll(String address, int start, int end, int bathroom, int bedroom, LocalDate cus_begin, LocalDate cus_end) {
//        return null;
//    }
}
