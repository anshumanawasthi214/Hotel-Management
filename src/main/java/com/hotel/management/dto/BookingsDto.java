package com.hotel.management.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingsDto {

private long id;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	private int numberOfAdults;
	
	private int numberOfChildren;
	
	private int totalNumberOfGuest;
	
	private String bookingConfirmationCode;
	
	
	private UserDto user;
	
	private RoomDto room;
	
}
