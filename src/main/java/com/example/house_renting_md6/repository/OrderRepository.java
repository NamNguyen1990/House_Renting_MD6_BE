package com.example.house_renting_md6.repository;

import com.example.house_renting_md6.model.AppUser;
import com.example.house_renting_md6.model.House;
import com.example.house_renting_md6.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByHouse(House house);

    List<Order> findAllByHouse_Id(Long id);

    @Modifying
    @Query(value = "select * from orders where customer_id = :customer_id and status >= 1",nativeQuery = true)
    Iterable<Order> findOderByCustomerId(@Param("customer_id") Long customer_id);

    Page<Order> findOderByCustomer(AppUser appUser, Pageable pageable);

    Page<Order> findAllByHouse_Id(Pageable pageable, Long idHouse);

    @Query(value = "(select * from orders  where (start_time between :dateBegin and :dateEnd) or (end_time between :dateBegin and :dateEnd))", nativeQuery = true)
    ArrayList<Order> findOrderByManyThing(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

}
