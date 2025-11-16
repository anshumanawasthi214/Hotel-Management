package com.hotel.management.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel.management.dto.BookingsDto;
import com.hotel.management.dto.Response;
import com.hotel.management.entity.Bookings;
import com.hotel.management.entity.Room;
import com.hotel.management.entity.User;
import com.hotel.management.exception.OurException;
import com.hotel.management.repository.BookingsRepository;
import com.hotel.management.repository.RoomRepository;
import com.hotel.management.repository.UserRepository;
import com.hotel.management.service.interfaces.IBookingService;
import com.hotel.management.utils.Utils;

@Service
public class BookingService implements IBookingService {
	
	@Autowired private BookingsRepository bookingRepo;
		
	@Autowired private RoomRepository roomRepo;
	
	@Autowired private UserRepository userRepo;
	

	@Override
	public Response saveBookings(Long roomId, Long userId, Bookings bookingRequest) {
		Response response=new Response();
		
		try {
			if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("Check out date must come after Check in date.");
			}
			
			Room room=roomRepo.findById(roomId).orElseThrow(()->new OurException("Room not found"));
			
			User user=userRepo.findById(userId).orElseThrow(()->new OurException("User not found"));
			
			List<Bookings> existingBookings=room.getBookings();
			
			
			if(!roomIsAvailable(bookingRequest,existingBookings)) {
				throw new OurException("Rooms not Available for Selected Date Range");
			}
			
			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			
			String bookingConfirmationCode=Utils.generateConfirmationCode(10);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			bookingRepo.save(bookingRequest);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setBookingConfirmationCode(bookingConfirmationCode);
		
		}
		catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Saving in booking "+e.getMessage());
		}
		return response;
	}

	private boolean roomIsAvailable(Bookings bookingRequest, List<Bookings> existingBookings) {
		 return existingBookings.stream()
	                .noneMatch(existingBooking ->
	                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
	                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
	                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
	                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
	                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

	                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
	                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

	                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

	                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
	                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

	                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
	                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
	                );
	
}

	@Override
	public Response findBookingByConfirmationCode(String confirmationCode) {
Response response=new Response();
		
		try {
			Bookings booking=bookingRepo.findBybookingConfirmationCode(confirmationCode).orElseThrow(()->new OurException("No bookings found with this confirmation Code"));
			response.setStatusCode(200);
			response.setMessage("Successfull");
			BookingsDto bookingDto=Utils.mapBookingEntityToBookingDTOPlusBookedRooms(booking,true);
			response.setBooking(bookingDto);
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Saving in booking "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllBookings() {
		Response response=new Response();
		
		try {
			List<Bookings> booking=bookingRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
			response.setStatusCode(200);
			response.setMessage("Successfull");
			List<BookingsDto> bookingDto=Utils.mapBookingListEntityToBookingListDTO(booking);
			response.setBookingList(bookingDto);
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error Getting all booking "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response cancelBooking(Long bookingId) {
	Response response=new Response();
		
		try {
			bookingRepo.findById(bookingId).orElseThrow(()->new OurException("No bookings found "));
			bookingRepo.deleteById(bookingId);
			response.setStatusCode(200);
			response.setMessage("Deletion Successfull");
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in booking cancelation "+e.getMessage());
		}
		return response;
	}

}
