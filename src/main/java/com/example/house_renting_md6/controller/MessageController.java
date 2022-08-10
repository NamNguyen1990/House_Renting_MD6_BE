package com.example.house_renting_md6.controller;


import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Messagee;
import com.example.house_renting_md6.model.User;
import com.example.house_renting_md6.service.impl.HouseServiceImpl;
import com.example.house_renting_md6.service.impl.MessageServiceImpl;
import com.example.house_renting_md6.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageServiceImpl messageService;

    @Autowired
    HouseServiceImpl houseService;

    @Autowired
    UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<Iterable<Messagee>> findAll() {
        return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Messagee>> findById(@PathVariable long id) {
        return new ResponseEntity<>(messageService.findById(id), HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<Messagee> add(@RequestBody Messagee message) {
        message.setContent("Already have tenants");
        messageService.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/findByUser/{id}")
//    public ResponseEntity<Iterable<Messagee>> findByUser(@PathVariable Long id){
//        Optional<User> user = userService.findById(id);
//        return new ResponseEntity<>(messageService.findAllByUser(user.get()), HttpStatus.OK);
//    }

    @GetMapping("/findByUser/{id}")
    public ResponseEntity<Iterable<Messagee>> findByUser(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        return new ResponseEntity<>(messageService.findAllByHouse_Owner(user.get()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messagee> delete(@PathVariable Long id) {
        messageService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
