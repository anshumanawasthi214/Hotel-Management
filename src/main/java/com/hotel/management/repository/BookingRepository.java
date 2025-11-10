package com.hotel.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.entity.Bookings;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

}
