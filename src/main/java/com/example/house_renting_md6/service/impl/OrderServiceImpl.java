package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.repository.OrderRepository;
import com.example.house_renting_md6.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<Order> totalMoneyByMonth(Long id, int month, int year)  {
        List<Order> orders = orderRepository.findAllByHouse_Id(id);
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getStartTime().minusDays(1).getYear()==year){
                if (orders.get(i).getStartTime().minusDays(1).getMonthValue()==month){
                     orderList.add(orders.get(i));
                }
            }
        }
        return orderList;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Iterable<Order> findOderByCustomerId(Long customer_id) {
        return orderRepository.findOderByCustomerId(customer_id);
    }
}
