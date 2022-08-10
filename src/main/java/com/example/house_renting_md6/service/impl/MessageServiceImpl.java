package com.example.house_renting_md6.service.impl;

import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Messagee;
import com.example.house_renting_md6.model.User;
import com.example.house_renting_md6.repository.CommentRepository;
import com.example.house_renting_md6.repository.MessageRepository;
import com.example.house_renting_md6.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public Page<Messagee> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Messagee> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public void save(Messagee messagee) {
        messageRepository.save(messagee);
    }

    @Override
    public void remove(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Iterable<Messagee> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public Iterable<Messagee> findAllByUser(User user) {
        return messageRepository.findAllByUser(user);
    }

    @Override
    public Iterable<Messagee> findAllByHouse_Owner(User user) {
        return messageRepository.findAllByHouse_Owner(user);
    }
}
