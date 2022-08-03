package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHouse(House house);
}
