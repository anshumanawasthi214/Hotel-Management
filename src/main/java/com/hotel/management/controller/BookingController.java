package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.management.dto.Response;
import com.hotel.management.entity.Bookings;
import com.hotel.management.service.impl.BookingService;

@RestController
@RequestMapping("/bookings")
public class BookingController {

	
	@Autowired BookingService bookingService;
	
	@PostMapping("/book-room/{roomId}/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> saveBookings(@PathVariable Long roomId,
												 @PathVariable Long userId,
												 @RequestBody Bookings bookingRequest){
		
		Response response=bookingService.saveBookings(roomId, userId, bookingRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllBookings(){
		Response response=bookingService.getAllBookings();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-by-confirmation-code/{confirmationCode}")
	public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
		Response response=bookingService.findBookingByConfirmationCode(confirmationCode);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@DeleteMapping("/cancel/{bookingId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId){
		Response response=bookingService.cancelBooking(bookingId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
	
	
	
}
