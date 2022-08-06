package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comments where house_id = :id", nativeQuery = true)
    Iterable<Comment> findCommentByHouseId(@Param("id") Long id);

}
