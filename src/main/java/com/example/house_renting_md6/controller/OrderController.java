package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.model.User;
import com.example.house_renting_md6.service.UserService;
import com.example.house_renting_md6.service.impl.HouseServiceImpl;
import com.example.house_renting_md6.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/order")
public class OrderController {
    @Autowired
    HouseServiceImpl houseService;
    @Autowired
    UserService userService;
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/{idHome}/{idCustomer}")
    public ResponseEntity<?> orderHome(@RequestBody Order order, @PathVariable Long idHome, @PathVariable Long idCustomer) {
        Optional<House> house = houseService.findById(idHome);
        Optional<User> user = userService.findById(idCustomer);
        List<Order> orders = orderService.findAllByHouse(house.get());
        boolean check = true;
        if (orders.isEmpty()) {
            order.setCustomer(user.get());
            order.setHouse(house.get());
            orderService.save(order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            if (!order.getEndTime().isAfter(order.getStartTime())) {
                check = false;
            }
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getEndTime().isAfter(order.getStartTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getStartTime())) {
                        check = false;
                    }
                }
                if (orders.get(i).getEndTime().isAfter(order.getEndTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getEndTime())) {
                        check = false;
                    }
                }
                if (orders.get(i).getStartTime().isAfter(order.getStartTime()) || orders.get(i).getStartTime().isEqual(order.getStartTime())) {
                    if (orders.get(i).getEndTime().isBefore(order.getEndTime()) || orders.get(i).getEndTime().isEqual(order.getEndTime())) {
                        check = false;
                    }
                }

            }
            if (check) {
                order.setCustomer(user.get());
                order.setHouse(house.get());
                orderService.save(order);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }


    }
}
