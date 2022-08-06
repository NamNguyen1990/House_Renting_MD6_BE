package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOrderService<T> {
    List<Order> findAllByHouse(House house);

    List<Order> totalMoneyByMonth(Long id, int month ,int year) throws CustomException;

    Order save(Order order);

    Optional<Order> findById(Long id);

    void remove(Long id);

    Page<Order> findAll(Pageable pageable);
    Iterable<Order> findOderByCustomerId (Long customer_id);
}
