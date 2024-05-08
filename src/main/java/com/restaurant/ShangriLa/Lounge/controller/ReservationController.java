package com.restaurant.ShangriLa.Lounge.controller;

import com.restaurant.ShangriLa.Lounge.model.Reservation;
import com.restaurant.ShangriLa.Lounge.model.ReservationRequest;
import com.restaurant.ShangriLa.Lounge.model.StatusReservation;
import com.restaurant.ShangriLa.Lounge.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService=reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations=reservationService.findAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable("id") Long id) {
        Optional<Reservation> reservationOptional = reservationService.findReservationById(id);
        if (reservationOptional.isPresent()) {
            Reservation reservation = reservationOptional.get();
            return new ResponseEntity<>(reservation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reservation not found", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Reservation createdReservation=reservationService.createReservation(reservationRequest);
        return new ResponseEntity<>(createdReservation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        Reservation updatedReservation=reservationService.updateReservation(id, reservation);
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllReservations() {
        long count = reservationService.countAllReservations();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/countByDate")
    public ResponseEntity<Integer> countReservationsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int count = reservationService.countReservationsByDate(date);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/byDate")
    public ResponseEntity<List<Reservation>> findReservationsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Reservation> reservations = reservationService.findReservationsByDate(date);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/byStatus")
    public ResponseEntity<List<Reservation>> findReservationsByStatus(@RequestParam("status") StatusReservation status) {
        List<Reservation> reservations = reservationService.findReservationsByStatus(status);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/byCustomer")
    public ResponseEntity<List<Reservation>> findReservationsByCustomer(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName) {
        List<Reservation> reservations = reservationService.findReservationsByCustomer(firstName, lastName);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }



}
