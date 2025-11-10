package com.hotel.management.dto;

import java.util.List;

import lombok.Data;

@Data
public class Response {
 
	private int statusCode;
	private String message;
	private String token;
	private String role;
	private String expirationTime;
	private int totalNoOfGuest;
	private String bookingConfirmationCode;
	private BookingsDto booking;
	private UserDto user;
	private RoomDto room;
	private List<UserDto> userList;
	private List<RoomDto> roomList;
	private List<BookingsDto> bookingList;

}
