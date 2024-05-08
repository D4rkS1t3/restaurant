package com.restaurant.ShangriLa.Lounge.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    @Column(nullable = false,updatable = false)
    private long id;
    @NonNull
    private LocalDate dateReservation;
    @NonNull
    private LocalTime timeReservation;
    private int numbOfPeople;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReservation statusReservation;
    private String comments;
    @OneToOne
    private RestaurantTable restaurantTable;




    public String convertToTextFormat() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reservation details:\n");
        sb.append("ID: ").append(id).append("\n");
        sb.append("Date: ").append(dateReservation).append("\n");
        sb.append("Time: ").append(timeReservation).append("\n");
        sb.append("Number of people: ").append(numbOfPeople).append("\n");
        sb.append("Customer: ").append(firstName).append(" ").append(lastName).append("\n");
        sb.append("Phone number: ").append(phoneNumber).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Status: ").append(statusReservation).append("\n");
        sb.append("Comments: ").append(comments).append("\n");
        return sb.toString();
    }


    public double calculateReservationCost(double baseCost, double additionalServicesCost) {
        // Obliczenia na podstawie liczby osób i dodatkowych usług
        double totalCost = baseCost * numbOfPeople + additionalServicesCost;
        return totalCost;
    }










}
