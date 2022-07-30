package com.example.house_renting_md6.service;


import com.example.house_renting_md6.model.Role;


public interface RoleService {
    Iterable<Role> findAll();


    void save(Role role);

    Role findByName(String name);
}
