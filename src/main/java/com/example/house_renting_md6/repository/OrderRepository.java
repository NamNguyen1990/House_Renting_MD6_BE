package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHouse(House house);
}
