package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;

import java.util.List;

public interface IOrderService<T> {
    List<Order> findAllByHouse(House house);

    void save(Order order);
}
