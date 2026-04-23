package com.fleetflow.backend.controller;

import com.fleetflow.backend.entity.Booking;
import com.fleetflow.backend.entity.BookingStatus;
import com.fleetflow.backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@CrossOrigin("*")
public class BookingController {
    private final BookingService service;

    public BookingController(BookingService service){
        this.service = service;
    }

    @PostMapping
    public Booking createBooking(@RequestBody Booking booking){
        return service.saveBooking(booking);
    }

    @GetMapping
    public List<Booking> getAllBookings(){
        return service.getAllBookings();
    }

    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable Long id){
        return service.getBookingById(id);
    }

    @GetMapping("/customer")
    public List<Booking> getBookingByCustomerName(@RequestParam String name){
        return service.getBookingByCustomerName(name);
    }

    @GetMapping("/status")
    public List<Booking> getBookingByStatus(@RequestParam BookingStatus status){
        return service.getBookingsByStatus(status);
    }

    @PutMapping("/{bookingId}/assign-vehicle/{vehicleId}")
    public Booking assignVehicle(@PathVariable Long bookingId, @PathVariable Long vehicleId){
        return service.assignVehicle(bookingId, vehicleId);
    }

    @PutMapping("/{bookingId}/assign-driver/{driverId}")
    public Booking assignDriver(@PathVariable Long bookingId, @PathVariable Long driverId){
        return service.assignDriver(bookingId, driverId);
    }

    @PutMapping("/{bookingId}/start-trip")
    public Booking startTrip(@PathVariable Long bookingId){
        return service.startTrip(bookingId);
    }

    @PutMapping("/{bookingId}/status")
    public Booking updateStatus(@PathVariable Long bookingId, @RequestParam BookingStatus status){
        return service.updateBookingStatus(bookingId, status);
    }

    @DeleteMapping("/{bookingId}")
    public String deleteBooking(@PathVariable Long bookingId){
        service.deleteBooking(bookingId);
        return "Booking Reference Deleted Successfully";
    }


}
