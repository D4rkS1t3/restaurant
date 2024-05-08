package com.restaurant.ShangriLa.Lounge.repository;

import com.restaurant.ShangriLa.Lounge.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(int tableNumber);
    List<RestaurantTable> findTablesByNumberOfSeatsGreaterThanEqual(int numberOfSeats);

}
