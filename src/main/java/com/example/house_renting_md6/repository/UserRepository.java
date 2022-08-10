package com.example.house_renting_md6.repository;


import com.example.house_renting_md6.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

}