package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order,Long> {
}
