package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.AppUser;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.model.Time;
import com.example.house_renting_md6.repository.OrderRepository;
import com.example.house_renting_md6.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService<Order> {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    HouseServiceImpl houseService;

    @Override
    public List<Order> findAllByHouse(House house) {
        return orderRepository.findAllByHouse(house);
    }

    @Override
    public List<Order> totalMoneyByMonth(Long id, Time time) {
        List<Order> orders = orderRepository.findAllByHouse_Id(id);
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getStartTime().minusDays(1).getYear() == Integer.parseInt(time.getYear())) {
                if (orders.get(i).getStartTime().minusDays(1).getMonthValue() == Integer.parseInt(time.getMonth())) {
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

    @Override
    public Page<Order> findOderByCustomerId1(AppUser appUser, Pageable pageable) {
        return orderRepository.findOderByCustomer(appUser,pageable);
    }

    @Override
    public Page<Order> findOderByHouseId1(Long idHouse, Pageable pageable) {
        return orderRepository.findAllByHouse_Id(pageable,idHouse);
    }

    @Override
    public List<Order> findOderByHouseId(Long idHouse) {
        return orderRepository.findAllByHouse_Id(idHouse);
    }

    @Override
    public void updateStatus() {
        LocalDate now = LocalDate.now();
        List<Order> orders = findAll1();
        for (int i = 0; i < orders.size(); i++) {
            if (now.compareTo(orders.get(i).getEndTime()) > 0) {
                orders.get(i).setStatus(3);
                Optional<House> house = houseService.findById(orders.get(i).getHouse().getId());
                house.get().setStatus(1);
                save(orders.get(i));
            } else {
                if (now.compareTo(orders.get(i).getStartTime()) >= 0) {
                    orders.get(i).setStatus(2);
                    Optional<House> house = houseService.findById(orders.get(i).getHouse().getId());
                    house.get().setStatus(2);
                    save(orders.get(i));
                }
            }
        }
    }

    @Override
    public List<Order> findAll1() {
        return orderRepository.findAll();
    }
}
