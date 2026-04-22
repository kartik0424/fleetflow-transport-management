package com.fleetflow.backend.repository;

import com.fleetflow.backend.entity.Booking;
import com.fleetflow.backend.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerName(String name);

    List<Booking> findByStatus(BookingStatus status);
}
