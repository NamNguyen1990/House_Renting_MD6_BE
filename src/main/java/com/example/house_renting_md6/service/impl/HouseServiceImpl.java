package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.Category;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.repository.HouseRepository;
import com.example.house_renting_md6.service.CategoryService;
import com.example.house_renting_md6.service.HouseService;
import com.example.house_renting_md6.utils.Constants;
import com.example.house_renting_md6.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class HouseServiceImpl implements HouseService {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    CategoryService categoryService;

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
    public House save(Long id, House house) throws CustomException {
        House houseOld = findById(id).orElseThrow(() -> new CustomException("House Not Found"));
        Category category = categoryService.findById(house.getCategory().getId()).orElseThrow(() -> new CustomException("Category Not Found"));
        if ( houseOld.getStatus() == 2
                ) {
            throw new CustomException("The house has been rented. Unable to edit status!");
        }
        if(house.getImages().size()!=0){
            houseOld.setImages(house.getImages());
        }else
        houseOld.setName(house.getName());
        houseOld.setAddress(house.getAddress());
        houseOld.setBedroom(house.getBedroom());
        houseOld.setBathroom(house.getBathroom());
        houseOld.setDescription(house.getDescription());
        houseOld.setPrice(house.getPrice());
        houseOld.setCategory(category);
        houseOld.setStatus(house.getStatus());
        if (!StringUtils.isNullOrEmpty(house.getAvatarHouse())) {
            houseOld.setAvatarHouse(house.getAvatarHouse());
        }
        return houseRepository.save(houseOld);
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

        return houseRepository.findByManyThing(address,startPrice,endPrice,bathroom,bedroom,dateBegin,dateEnd);
    }


}
