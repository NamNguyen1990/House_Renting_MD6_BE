package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.model.Image;
import com.example.house_renting_md6.model.Order;
import com.example.house_renting_md6.service.impl.CommentServiceImpl;
import com.example.house_renting_md6.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping
    public ResponseEntity<Iterable<Comment>> findAll() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Comment>> findById(@PathVariable long id) {
//        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
//    }

    @PostMapping()
    public ResponseEntity add(@RequestBody Comment comment) {
        List<Order> orderList = orderService.findAllByHouse(comment.getHouse());
        for (int i = 0; i < orderList.size(); i++) {
            if (comment.getUser().getId() == orderList.get(i).getCustomer().getId()) {
                comment.setCreateAt(LocalDateTime.now());
                final Comment add = commentService.add(comment);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable long id) {
        commentService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Iterable<Comment>> findCommentByHouseId(@PathVariable Long id) {
        return new ResponseEntity<>(commentService.findByHouse(id),HttpStatus.OK);
    }
}
