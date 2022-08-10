package com.example.house_renting_md6.service;

import com.example.house_renting_md6.model.Comment;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Messagee;
import com.example.house_renting_md6.model.User;

import javax.mail.Message;

public interface MessageService extends IService<Messagee>{

    Iterable<Messagee> findAll();
    //tìm thông báo theo nhà
    Iterable<Messagee> findAllByUser (User user);
}
