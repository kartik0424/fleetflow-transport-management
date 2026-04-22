package com.fleetflow.backend.service;

import com.fleetflow.backend.entity.*;
import com.fleetflow.backend.repository.BookingRepository;
import com.fleetflow.backend.repository.DriverRepository;
import com.fleetflow.backend.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepo;
    private final VehicleRepository vehicleRepo;
    private final DriverRepository driverRepo;

    public BookingService(BookingRepository bookingRepo,
                          VehicleRepository vehicleRepo,
                          DriverRepository driverRepo) {
        this.bookingRepo = bookingRepo;
        this.vehicleRepo = vehicleRepo;
        this.driverRepo = driverRepo;
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // Get booking by id
    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    // Search by customer name
    public List<Booking> getBookingByCustomerName(String name) {
        return bookingRepo.findByCustomerName(name);
    }

    // Get by status
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepo.findByStatus(status);
    }

    // Create booking
    public Booking saveBooking(Booking booking) {

        if (booking.getWeight() <= 0) {
            throw new RuntimeException("Invalid weight");
        }

        if (booking.getDeliveryDate().isBefore(booking.getBookingDate())) {
            throw new RuntimeException("Delivery date invalid");
        }

        if (booking.getBookingDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Booking date cannot be in past");
        }

        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        return bookingRepo.save(booking);
    }

    // Delete booking
    public void deleteBooking(Long id) {

        Booking booking = getBookingById(id);

        bookingRepo.delete(booking);
    }

    // Assign vehicle
    public Booking assignVehicle(Long bookingId, Long vehicleId) {

        Booking booking = getBookingById(bookingId);

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
            throw new RuntimeException("Vehicle not available");
        }

        booking.setVehicle(vehicle);
        booking.setStatus(BookingStatus.ASSIGNED);

        vehicle.setStatus(VehicleStatus.ON_TRIP);
        vehicleRepo.save(vehicle);

        return bookingRepo.save(booking);
    }

    // Assign driver
    public Booking assignDriver(Long bookingId, Long driverId) {

        Booking booking = getBookingById(bookingId);

        Driver driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (driver.getStatus() != DriverStatus.AVAILABLE) {
            throw new RuntimeException("Driver not available");
        }

        booking.setDriver(driver);
        booking.setStatus(BookingStatus.ASSIGNED);

        driver.setStatus(DriverStatus.ON_TRIP);
        driverRepo.save(driver);

        return bookingRepo.save(booking);
    }

    // Update booking status
    public Booking updateBookingStatus(Long bookingId, BookingStatus status) {

        Booking booking = getBookingById(bookingId);

        booking.setStatus(status);

        if (status == BookingStatus.DELIVERED ||
                status == BookingStatus.CANCELLED) {

            if (booking.getDriver() != null) {
                Driver driver = booking.getDriver();
                driver.setStatus(DriverStatus.AVAILABLE);
                driverRepo.save(driver);
            }

            if (booking.getVehicle() != null) {
                Vehicle vehicle = booking.getVehicle();
                vehicle.setStatus(VehicleStatus.AVAILABLE);
                vehicleRepo.save(vehicle);
            }
        }

        return bookingRepo.save(booking);
    }

    // Start trip
    public Booking startTrip(Long bookingId) {

        Booking booking = getBookingById(bookingId);

        if (booking.getDriver() == null || booking.getVehicle() == null) {
            throw new RuntimeException("Assign driver and vehicle first");
        }

        booking.setStatus(BookingStatus.IN_TRANSIT);

        return bookingRepo.save(booking);
    }
}