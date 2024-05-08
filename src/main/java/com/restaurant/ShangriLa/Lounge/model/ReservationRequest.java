package com.restaurant.ShangriLa.Lounge.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {
    @NonNull
    private LocalDate dateReservation;
    @NonNull
    private LocalTime timeReservation;
    @NonNull
    private int numbOfPeople;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String phoneNumber;
    @NonNull
    private String email;
    @NonNull
    private StatusReservation statusReservation;
    private String comments;


    public boolean validateReservation() {
        // Pobierz bieżącą datę i godzinę
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Sprawdź, czy data rezerwacji jest w przyszłości
        LocalDateTime reservationDateTime = dateReservation.atTime(timeReservation);
        if (reservationDateTime.isBefore(currentDateTime)) {
            return false;
        }
        // Sprawdź, czy liczba osób jest dodatnia
        if (numbOfPeople < 1) {
            return false;
        }
        return true;
    }
}

