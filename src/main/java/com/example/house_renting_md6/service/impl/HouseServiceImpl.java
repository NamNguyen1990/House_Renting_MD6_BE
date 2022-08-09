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
    public void save(House house) {
        houseRepository.save(house);
    }

    @Override
    public void remove(Long id) {
        houseRepository.deleteById(id);
    }

    @Override
    public House findLastHouse() {
        return houseRepository.findLastHouse();
    }

    @Override
    public Iterable<House> findByOwnerId(Long owner_id) {
        return houseRepository.findByOwnerId(owner_id);
    }

    @Override
    public Iterable<House> findTop5() {
        return houseRepository.findTop5();
    }
    @Override
    public Iterable<House> findByManyThing(String address, int startPrice, int endPrice, int bathroom, int bedroom, LocalDate dateBegin, LocalDate dateEnd) {
        System.out.println(address);
        System.out.println(startPrice);
        System.out.println(endPrice);

//        return houseRepository.findByManyThing1();
        return houseRepository.findByManyThing(address, startPrice, endPrice, bathroom, bedroom, dateBegin, dateEnd);
    }

}
