package com.restaurant.ShangriLa.Lounge.service;

import com.restaurant.ShangriLa.Lounge.model.RestaurantTable;
import com.restaurant.ShangriLa.Lounge.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository=tableRepository;
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    public Optional<RestaurantTable> getTableById(Long id) {
        return tableRepository.findById(id);
    }

    public Optional<RestaurantTable> getTableByTableNumber(int tableNumber) {
        return tableRepository.findByTableNumber(tableNumber);
    }

    public List<RestaurantTable> getTablesByNumberOfSeats(int numberOfSeats) {
        return tableRepository.findTablesByNumberOfSeatsGreaterThanEqual(numberOfSeats);
    }







}
