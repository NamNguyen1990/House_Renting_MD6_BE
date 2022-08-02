package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.service.impl.OrderServiceIplm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
   OrderServiceIplm orderServiceIplm;

    @GetMapping
    public ResponseEntity<Page<Order>>findAllHouse(@PageableDefault(value=8) Pageable pageable) {
        Page<Order>orders=orderServiceIplm.findAll(pageable);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Optional<Order> houseOptional = orderServiceIplm.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.OK);
    }


}
