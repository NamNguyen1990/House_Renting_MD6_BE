package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House,Long> {
}
