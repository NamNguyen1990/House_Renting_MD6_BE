package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
