package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.House;



public interface HouseService extends IService<House>{
    House findLastHouse();

}