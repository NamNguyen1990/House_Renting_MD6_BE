package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.model.Image;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface CommentService extends IService<Comment>{
     Iterable<Comment> findAll();
     Iterable<Comment> findByHouse(Long id);
}
