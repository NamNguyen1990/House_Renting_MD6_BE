package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(User user) throws CustomException;

    Iterable<User> findAll();

    User findByUsername(String username);

    User getCurrentUser();

    Optional<User> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(User user);

    boolean isRegister(User user);

    boolean isCorrectConfirmPassword(User user);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);
}
