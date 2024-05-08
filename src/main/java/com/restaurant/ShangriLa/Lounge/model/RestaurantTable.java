package com.restaurant.ShangriLa.Lounge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private int tableNumber;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private int numberOfSeats;

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", tableNumber=" + tableNumber +
                ", available=" + available +
                ", numberOfSeats=" + numberOfSeats +
                '}';
    }



}
