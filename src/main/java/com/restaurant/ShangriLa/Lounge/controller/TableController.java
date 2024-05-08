package com.restaurant.ShangriLa.Lounge.controller;

import com.restaurant.ShangriLa.Lounge.model.RestaurantTable;
import com.restaurant.ShangriLa.Lounge.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTable>> getAllTables() {
        List<RestaurantTable> restaurantTables = tableService.getAllTables();
        return new ResponseEntity<>(restaurantTables, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTable> getTableById(@PathVariable("id") Long id) {
        return tableService.getTableById(id)
                .map(table -> new ResponseEntity<>(table, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/number/{tableNumber}")
    public ResponseEntity<RestaurantTable> getTableByNumber(@PathVariable("tableNumber") int tableNumber) {
        return tableService.getTableByTableNumber(tableNumber)
                .map(table -> new ResponseEntity<>(table, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/seats/{numberOfSeats}")
    public ResponseEntity<List<RestaurantTable>> getTablesByNumberOfSeats(@PathVariable("numberOfSeats") int numberOfSeats) {
        List<RestaurantTable> restaurantTables = tableService.getTablesByNumberOfSeats(numberOfSeats);
        return new ResponseEntity<>(restaurantTables, HttpStatus.OK);
    }



}
