package com.hotel.management.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.management.entity.Bookings;

public interface BookingsRepository extends JpaRepository<Bookings, Long> {

	
	List<Bookings> findByRoomId(Long roomId);
	
	Optional<Bookings> findBybookingConfirmationCode(String confirmationCode);

	List<Bookings> findByUserId(Long userId);

}
	