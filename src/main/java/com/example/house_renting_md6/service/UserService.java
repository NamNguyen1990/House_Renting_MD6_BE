package com.example.house_renting_md6.service;

import com.example.house_renting_md6.CustomException;
import com.example.house_renting_md6.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    AppUser save(AppUser appUser) throws CustomException;

    Iterable<AppUser> findAll();

    AppUser findByUsername(String username);

    AppUser getCurrentUser();

    Optional<AppUser> findById(Long id);

    UserDetails loadUserById(Long id);

    boolean checkLogin(AppUser appUser);

    boolean isRegister(AppUser appUser);

    boolean isCorrectConfirmPassword(AppUser appUser);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);
}
