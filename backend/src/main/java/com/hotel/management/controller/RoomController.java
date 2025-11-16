package com.hotel.management.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hotel.management.dto.Response;
import com.hotel.management.service.impl.BookingService;
import com.hotel.management.service.impl.RoomService;

@RestController
@RequestMapping("/rooms")
public class RoomController {
	
	@Autowired RoomService roomService;
	@Autowired BookingService bookingService;
	
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addNewRoom(
			@RequestParam(required=false) MultipartFile photo,
			@RequestParam(required=false)  String roomType,
			@RequestParam(required=false)  BigDecimal roomPrice,
			@RequestParam(required=false)  String roomDescription
			){
		
		
		if(photo==null || photo.isEmpty() || roomType==null || roomType.isBlank() || roomPrice==null) {
			Response response=new Response();
			response.setStatusCode(400);
			response.setMessage("Please Provide Values for all fields");
			return ResponseEntity.status(response.getStatusCode()).body(response);
			
		}
		Response response=roomService.addNewRoom(photo,roomType,roomPrice,roomDescription);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping("/all")
	public ResponseEntity<Response> getAllRooms(){
		Response response=roomService.getAllRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping("/types")
	public List<String> getRoomTypes(){
		return roomService.getAllRoomTypes();
	}
	
	@GetMapping("/room-by-id/{roomId}")
	public ResponseEntity<Response> getRoomById(@PathVariable Long roomId){
		Response response=roomService.getRoomById(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping("/all-available-rooms")
	public ResponseEntity<Response> getAvailableRooms(){
		Response response=roomService.getAvailableRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/available-rooms-by-date-and-type")
	public ResponseEntity<Response> getAvailableRoomByDatesAndType(
			@RequestParam(required=false)   @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate checkInDate,
			@RequestParam(required=false)   @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
			@RequestParam(required=false)  String roomType
			){
	if(checkInDate==null || checkOutDate==null || roomType==null || roomType.isBlank()) {
		Response response=new Response();
		response.setStatusCode(400);
		response.setMessage("Please Provide Values for all fields");
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}
	Response response=roomService.getAvailableRoomsByDateAndType(checkInDate, checkOutDate, roomType);
	return ResponseEntity.status(response.getStatusCode()).body(response);	
	}
	
	
	@PutMapping("/update/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addNewRoom(
			@RequestParam(required=false)  MultipartFile photo,
			@RequestParam(required=false)  String roomType,
			@RequestParam(required=false)  BigDecimal roomPrice,
			@RequestParam(required=false)  String roomDescription, @PathVariable Long roomId
			){
		Response response=roomService.updateRoom(roomId,roomType,roomPrice,roomDescription,photo);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@DeleteMapping("/delete/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteRoomById(@PathVariable Long roomId){
		Response response=roomService.deleteRoom(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	

}
