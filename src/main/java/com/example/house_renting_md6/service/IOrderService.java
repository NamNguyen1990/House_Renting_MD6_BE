package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.model.Time;
import com.example.house_renting_md6.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IOrderService<T> {
    List<Order> findAllByHouse(House house);

    List<Order> totalMoneyByMonth(Long id, Time time) throws CustomException;

    Order save(Order order);

    Optional<Order> findById(Long id);

    void remove(Long id);

    Page<Order> findAll(Pageable pageable);

    Iterable<Order> findOderByCustomerId (Long customer_id);

    Iterable<Order> findOderByHouseId(Long idHouse);

    void updateStatus();

    List<Order> findAll1();

    Page<Order> findOderByCustomerId1(User user, Pageable pageable);

    Page<Order> findOderByHouseId1(Long idHouse, Pageable pageable);
}
