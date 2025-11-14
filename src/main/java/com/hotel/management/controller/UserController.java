package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hotel.management.dto.Response;
import com.hotel.management.service.impl.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired UserService userService;

	
	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllUsers(){
		Response response=userService.getAllUsers();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-by-id/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getUserById(@PathVariable String userId){
		Response response=userService.getUserById(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@DeleteMapping("/delete/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteUser(@PathVariable String userId){
		Response response=userService.deleteUser(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-logged-in-profile-info")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> getLoggedInProfileInfo(){
		Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
		String email=authentication.getName();
		Response response=userService.getMyInfo(email);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping("/get-user-bookings/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> getUserBookingHistory(@PathVariable String userId){
		Response response=userService.getUserBookingHistory(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}

}
