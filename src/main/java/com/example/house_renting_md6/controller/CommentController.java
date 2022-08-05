package com.example.house_renting_md6.controller;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin("*")
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;

    @GetMapping
    public ResponseEntity<Iterable<Comment>> findAll() {
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> findById(@PathVariable long id) {
        return new ResponseEntity<>(commentService.findById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity add(@RequestBody Comment comment) {
        final Comment add = commentService.add(comment);
        return new ResponseEntity<>(add, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> update(@PathVariable long id) {
        commentService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
