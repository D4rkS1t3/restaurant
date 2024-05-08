package com.restaurant.ShangriLa.Lounge.repository;

import com.restaurant.ShangriLa.Lounge.model.Reservation;
import com.restaurant.ShangriLa.Lounge.model.StatusReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
//zajmuje się operacjami na bazie danych generuje automatycznie zapytania po nazwie metody
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<List<Reservation>> findByDateReservation(LocalDate date);

    Optional<List<Reservation>> findByStatusReservation(StatusReservation status);

    Optional<List<Reservation>> findByFirstNameAndLastName(String firstName, String lastName);

    int countByDateReservation(LocalDate date);

}

