package com.hotel.management.service.interfaces;

import com.hotel.management.dto.Response;
import com.hotel.management.entity.Bookings;

public interface IBookingService {
	
	Response saveBookings(Long roomId,Long userId,Bookings bookingRequest);
	
	Response findBookingByConfirmationCode(String confirmationCode);
	
	Response getAllBookings();
	
	Response cancelBooking(Long bookingId);

}
