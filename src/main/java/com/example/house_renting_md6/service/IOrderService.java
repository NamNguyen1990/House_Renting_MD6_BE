package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IOrderService<T> {
    List<Order> findAllByHouse(House house);

    Order save(Order order);

    Optional<Order> findById(Long id);

    void remove(Long id);

    Page<Order> findAll(Pageable pageable);
}
