package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.repository.CommentRepository;
import com.example.house_renting_md6.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CommentServiceImpl implements CommentService {
  @Autowired
  CommentRepository commentReponsitory;

    @Override
    public Iterable<Comment> findAll() {
       return commentReponsitory.findAll();
    }

    @Override
    public Iterable<Comment> findByHouse(Long id) {
        return commentReponsitory.findCommentByHouseId(id);
    }

    // comment không dùng page
    @Override
    public Page<Comment> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentReponsitory.findById(id);
    }

    @Override
    public void save(Comment comment) {
        commentReponsitory.save(comment);
    }

    public Comment add(Comment comment) {
        return commentReponsitory.save(comment);
    }

    @Override
    public void remove(Long id) {
        commentReponsitory.deleteById(id);

    }


}
