package com.restaurant.ShangriLa.Lounge.service;

import com.restaurant.ShangriLa.Lounge.model.Reservation;
import com.restaurant.ShangriLa.Lounge.model.ReservationRequest;
import com.restaurant.ShangriLa.Lounge.model.StatusReservation;
import com.restaurant.ShangriLa.Lounge.model.RestaurantTable;
import com.restaurant.ShangriLa.Lounge.repository.ReservationRepository;
import com.restaurant.ShangriLa.Lounge.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository;


    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository=tableRepository;
    }


    public Optional<Reservation> findReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findReservationsByDate(LocalDate date) {
        return reservationRepository.findByDateReservation(date).orElseThrow(() -> new RuntimeException("No reservations found for the given date"));
    }

    public List<Reservation> findReservationsByStatus(StatusReservation status) {
        return reservationRepository.findByStatusReservation(status).orElseThrow(() -> new RuntimeException("No reservations found for the given status"));
    }

    public List<Reservation> findReservationsByCustomer(String firstName, String lastName) {
        return reservationRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new RuntimeException("No reservations found for the given customer"));
    }



    public long countAllReservations() {
        return reservationRepository.count();
    }

    public int countReservationsByDate(LocalDate date) {
        return reservationRepository.countByDateReservation(date);
    }




    public void deleteReservation(Long id) {
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);
        if (reservationOptional.isPresent()) {
            Reservation delReservation = reservationOptional.get();
            RestaurantTable restaurantTable = delReservation.getRestaurantTable();
            if (restaurantTable != null) {
                restaurantTable.setAvailable(true);
            }
            reservationRepository.deleteById(id);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }

    public Reservation createReservation(ReservationRequest reservation) {
        if (!reservation.validateReservation()) {
            throw new RuntimeException("Invalid reservation data");
        }

        List<RestaurantTable> availableRestaurantTables = getAvailableSeats(reservation.getNumbOfPeople());
        if (availableRestaurantTables.isEmpty()) {
            throw new RuntimeException("No available tables in the restaurant");
        }

        // Wybierz pierwszy dostępny stol z listy dostępnych stołów
        RestaurantTable selectedRestaurantTable = availableRestaurantTables.get(0);
        selectedRestaurantTable.setAvailable(false);

        Reservation newReservation=new Reservation();

        newReservation.setDateReservation(reservation.getDateReservation());
        newReservation.setTimeReservation(reservation.getTimeReservation());
        newReservation.setNumbOfPeople(reservation.getNumbOfPeople());
        newReservation.setFirstName(reservation.getFirstName());
        newReservation.setLastName(reservation.getLastName());
        newReservation.setPhoneNumber(reservation.getPhoneNumber());
        newReservation.setEmail(reservation.getEmail());
        newReservation.setStatusReservation(reservation.getStatusReservation());
        newReservation.setComments(reservation.getComments());
        newReservation.setRestaurantTable(selectedRestaurantTable);

        return reservationRepository.save(newReservation);
    }




    public List<RestaurantTable> getAvailableSeats(int numberOfSeats) {
        List<RestaurantTable> restaurantTables = tableRepository.findTablesByNumberOfSeatsGreaterThanEqual(numberOfSeats);
        restaurantTables.removeIf(table -> !table.isAvailable());
        return restaurantTables;
    }

    public Reservation updateReservation(Long id, Reservation newReservationData) {
        Optional<Reservation> existingReservationOptional = reservationRepository.findById(id);
        if (existingReservationOptional.isPresent()) {
            Reservation existingReservation = existingReservationOptional.get();

            // Aktualizuj dane istniejącej rezerwacji
            existingReservation.setDateReservation(newReservationData.getDateReservation());
            existingReservation.setTimeReservation(newReservationData.getTimeReservation());
            existingReservation.setNumbOfPeople(newReservationData.getNumbOfPeople());
            existingReservation.setFirstName(newReservationData.getFirstName());
            existingReservation.setLastName(newReservationData.getLastName());
            existingReservation.setPhoneNumber(newReservationData.getPhoneNumber());
            existingReservation.setEmail(newReservationData.getEmail());
            existingReservation.setStatusReservation(newReservationData.getStatusReservation());
            existingReservation.setComments(newReservationData.getComments());

            // Sprawdź dostępność stołu i przypisz, jeśli jest dostępny
            List<RestaurantTable> availableRestaurantTables = getAvailableSeats(newReservationData.getNumbOfPeople());
            if (!availableRestaurantTables.isEmpty()) {
                RestaurantTable selectedRestaurantTable = availableRestaurantTables.get(0);
                selectedRestaurantTable.setAvailable(false);
                existingReservation.setRestaurantTable(selectedRestaurantTable);
            } else {
                // W przeciwnym razie zwolnij stół z aktualnej rezerwacji, jeśli istnieje
                RestaurantTable currentRestaurantTable = existingReservation.getRestaurantTable();
                if (currentRestaurantTable != null) {
                    currentRestaurantTable.setAvailable(true);
                }
                existingReservation.setRestaurantTable(null);
            }

            return reservationRepository.save(existingReservation);
        } else {
            throw new RuntimeException("Reservation not found");
        }
    }


}
