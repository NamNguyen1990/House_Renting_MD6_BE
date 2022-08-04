package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.model.ResponseBody;
import com.example.house_renting_md6.model.ResponseMessage;
import com.example.house_renting_md6.model.User;
import com.example.house_renting_md6.service.UserService;
import com.example.house_renting_md6.service.impl.HouseServiceImpl;
import com.example.house_renting_md6.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public ResponseEntity<?> orderHome(@RequestBody Order order, @PathVariable Long idHome, @PathVariable Long idCustomer) {
        Optional<House> house = houseService.findById(idHome);
        Optional<User> user = userService.findById(idCustomer);
        List<Order> orders = orderService.findAllByHouse(house.get());
        if (house.get().getOwner().getId() == idCustomer) {
            return new ResponseEntity<>(new ResponseMessage("not rent your house!"), HttpStatus.OK);
        }
        if (orders.isEmpty()) {
            order.setCustomer(user.get());
            order.setHouse(house.get());
            orderService.save(order);
            return new ResponseEntity<>(new ResponseBody("0001","Order Success",orderService.save(order)), HttpStatus.CREATED);
        } else {
            if (!order.getEndTime().isAfter(order.getStartTime())) {
                return new ResponseEntity<>(new ResponseBody("0001","The start time must be less than the end time!"), HttpStatus.OK);
            }
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getEndTime().isAfter(order.getStartTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getStartTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001","already had a tenant before you!"), HttpStatus.OK);
                    }
                }
                if (orders.get(i).getEndTime().isAfter(order.getEndTime())) {
                    if (orders.get(i).getStartTime().isBefore(order.getEndTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001","already had a tenant before you!"), HttpStatus.OK);
                    }
                }
                if (orders.get(i).getStartTime().isAfter(order.getStartTime()) || orders.get(i).getStartTime().isEqual(order.getStartTime())) {
                    if (orders.get(i).getEndTime().isBefore(order.getEndTime()) || orders.get(i).getEndTime().isEqual(order.getEndTime())) {
                        return new ResponseEntity<>(new ResponseBody("0001","already had a tenant before you!"), HttpStatus.OK);
                    }
                }
            }
            order.setCustomer(user.get());
            order.setHouse(house.get());
            return new ResponseEntity<>(new ResponseBody("0001","Order Success",orderService.save(order)), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<?> cancelRent(@PathVariable Long idOrder) {
        Optional<Order> order = orderService.findById(idOrder);
        LocalDate localDate = LocalDate.now();
        LocalDate cancelTime = order.get().getStartTime().minusDays(1);
        if (cancelTime.isEqual(localDate) || cancelTime.isAfter(localDate)) {
            orderService.remove(idOrder);
//            order.get().setStatus(2);
//            orderService.save(order.get());

            return new ResponseEntity<>(new ResponseMessage("ok"),HttpStatus.OK);
        } else
            return new ResponseEntity<>(new ResponseMessage("Không thể hủy! khách hàng chỉ có thể hủy thuê 1 ngày trước ngày bắt đầu"),HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<Page<Order>>findAllHouse(@PageableDefault(value=8) Pageable pageable) {
        Page<Order>orders=orderService.findAll(pageable);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Optional<Order> houseOptional = orderService.findById(id);
        if (!houseOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(houseOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/find-order-by-customer_id")  // Tìm theo id User đăng nhập để ra số hóa đơn  của id đó!
    public ResponseEntity<Iterable<Order>> findOrderByOwnerId(@RequestParam(value = "customer_id") Long customer_id) {
        List<Order> orders = (List<Order>) orderService.findOderByCustomerId(customer_id);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

}

