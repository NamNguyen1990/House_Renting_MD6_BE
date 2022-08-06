package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HouseRepository extends JpaRepository<House, Long> {
    @Query(value = "select * from house where status = 1", nativeQuery = true)
    Page<House> findAll(Pageable pageable);

    @Query(value = "select * from house order by id desc limit 1", nativeQuery = true)
    House findLastHouse();

    @Query(value = "select * from house where owner_id = :owner_id and status >= 1",nativeQuery = true)
    Iterable<House> findByOwnerId(@Param("owner_id") Long owner_id);

    @Query(value = "select id, name, address, bedroom, bathroom, description, category_id, price, owner_id, category_id, avatar_house,status  from house\n" +
            "join\n" +
            "(select houserenting_md6.orders.house_id, count(houserenting_md6.orders.house_id) as dem\n" +
            "from orders\n" +
            "join house h on houserenting_md6.orders.house_id = h.id\n" +
            "where h.status <> 0\n" +
            "group by houserenting_md6.orders.house_id\n" +
            "order by dem desc\n" +
            "limit 5) as abc on abc.house_id = house.id",nativeQuery = true)
    Iterable<House> findTop5();

}


