package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Messagee;
import com.example.house_renting_md6.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.mail.Message;

@Repository
public interface MessageRepository extends JpaRepository<Messagee, Long> {
    Iterable<Messagee> findAllByUser (User user);
    Iterable<Messagee> findAllByHouse_Owner (User user);

}
