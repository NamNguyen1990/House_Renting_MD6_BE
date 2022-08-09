package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.*;
import com.example.house_renting_md6.model.ResponseBody;
import com.example.house_renting_md6.service.UserService;
import com.example.house_renting_md6.service.impl.HouseServiceImpl;
import com.example.house_renting_md6.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    HouseServiceImpl houseService;
    @Autowired
    UserService userService;
    @Autowired
    OrderServiceImpl orderService;

    @PostMapping("/{idHome}/{idCustomer}")
    public ResponseEntity<?> orderHome(@RequestBody Order order, @PathVariable Long idHome, @PathVariable Long idCustomer, BindingResult bindingResult) {
        Optional<House> house = houseService.findById(idHome);
        Optional<User> user = userService.findById(idCustomer);
        List<Order> orders = orderService.findAllByHouse(house.get());
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(new ResponseBody("0001", "Invalid input parameter!"), HttpStatus.BAD_REQUEST);
        }
        if (house.get().getOwner().getId() == idCustomer) {
            return new ResponseEntity<>(new ResponseBody("0001", "Not rent your house!"), HttpStatus.OK);
        }
        if (order.getStartTime().compareTo(LocalDate.now())<0){
            return new ResponseEntity<>(new ResponseBody("0001", "start time must be greater than current time!"), HttpStatus.OK);
        }
        if (orders.isEmpty()) {
            order.setCustomer(user.get());
            order.setHouse(house.get());
            order.setStatus(1);
            order.setTotal(order.getHouse().getPrice() * ChronoUnit.DAYS.between(order.getStartTime(), order.getEndTime()));
            return new ResponseEntity<>(new ResponseBody("0000", "Order Success", orderService.save(order)), HttpStatus.CREATED);
        } else {
            if (!order.getEndTime().isAfter(order.getStartTime())) {
                return new ResponseEntity<>(new ResponseBody("0001", "The start time must be less than the end time!"), HttpStatus.OK);
            }
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getEndTime().isAfter(order.getStartTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getStartTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001", "Already had a tenant before you!"), HttpStatus.OK);
                    }
                }
                if (orders.get(i).getEndTime().isAfter(order.getEndTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getEndTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001", "Already had a tenant before you!"), HttpStatus.OK);
                    }
                }
                if (orders.get(i).getStartTime().isAfter(order.getStartTime()) || orders.get(i).getStartTime().isEqual(order.getStartTime())) {
                    if (orders.get(i).getEndTime().isBefore(order.getEndTime()) || orders.get(i).getEndTime().isEqual(order.getEndTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001", "Already had a tenant before you!"), HttpStatus.OK);
                    }
                }
            }
            order.setCustomer(user.get());
            order.setHouse(house.get());
            order.setStatus(1);
            order.setTotal(order.getHouse().getPrice() * ChronoUnit.DAYS.between(order.getStartTime(), order.getEndTime()));
            return new ResponseEntity<>(new ResponseBody("0000", "Order Success", orderService.save(order)), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<?> cancelRent(@PathVariable Long idOrder) {
        Optional<Order> order = orderService.findById(idOrder);
        LocalDate localDate = LocalDate.now();
        LocalDate cancelTime = order.get().getStartTime().minusDays(1);
        if ( cancelTime.isAfter(localDate)) {
            orderService.remove(idOrder);
            return new ResponseEntity<>(new ResponseMessage("Ok"), HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ResponseMessage("Can't cancel! Customers can only cancel the rental 1 day before the start date"), HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<Page<Order>> findAllHouse(@PageableDefault(value = 8) Pageable pageable) {
        Page<Order> orders = orderService.findAll(pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Optional<Order> houseOptional = orderService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Iterable<Order>> findOrderByOwnerId(@RequestParam(value = "customer_id") Long customer_id) {
        List<Order> orders = (List<Order>) orderService.findOderByCustomerId(customer_id);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/find1")
    public ResponseEntity<Page<Order>> findOrderByOwnerId1(@PageableDefault(value = 9, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,@RequestParam Long customer_id) {
        Optional<User> user = userService.findById(customer_id);
        Page<Order> orders =  orderService.findOderByCustomerId1(user.get(),pageable);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("/total/{idHouse}")
    public ResponseEntity<ResponseBody> totalMoneyByMonth(@PathVariable Long idHouse,@RequestBody Time time) {
        if (orderService.totalMoneyByMonth(idHouse,time).isEmpty()) {
            return new ResponseEntity<>(new ResponseBody("0001", "No orders!"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseBody("0000", "OK", orderService.totalMoneyByMonth(idHouse,time)), HttpStatus.OK);
        }
    }

    @GetMapping("/find-by-house/{id}")
    public ResponseEntity<ResponseBody> findOrderByHouseId(@PathVariable Long id) {
        List<Order> orders = orderService.findOderByHouseId(id);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(new ResponseBody("0001", "House don't have any orders yet!"),HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseBody("0000", "OK",orders), HttpStatus.OK);
    }
    @GetMapping("/update-order")
    public ResponseEntity<?> updateStatusOrder(){
        orderService.updateStatus();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

