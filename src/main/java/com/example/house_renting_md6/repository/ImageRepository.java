package com.example.house_renting_md6.repository;
import com.example.house_renting_md6.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Query(value = "select * from image where house_id = :id", nativeQuery = true)
    Iterable<Image> findImageByHouseId(@Param("id") Long id);

    @Query(value = "select * from image where house_id = :id group by house_id", nativeQuery = true)
    Optional<Image> findCardByHouseId(@Param("id") Long id);
}
