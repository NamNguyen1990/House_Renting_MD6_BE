package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.repository.OrderRepository;
import com.example.house_renting_md6.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService<Order> {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public List<Order> findAllByHouse(House house) {
        return orderRepository.findAllByHouse(house);
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }
}
