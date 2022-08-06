package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHouse(House house);
    List<Order> findAllByHouse_Id(Long id);
    @Modifying
    @Query(value = "select * from orders where customer_id = :customer_id and status >= 1",nativeQuery = true)
    Iterable<Order> findOderByCustomerId(@Param("customer_id") Long customer_id);
}
