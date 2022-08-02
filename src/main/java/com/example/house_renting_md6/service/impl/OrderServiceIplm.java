package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.Order;

import com.example.house_renting_md6.repository.OrderRepository;
import com.example.house_renting_md6.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class OrderServiceIplm implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }
    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);

    }
}
