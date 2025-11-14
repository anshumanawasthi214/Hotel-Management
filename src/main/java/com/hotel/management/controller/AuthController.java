package com.hotel.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.management.dto.LoginRequestDto;
import com.hotel.management.dto.Response;
import com.hotel.management.entity.User;
import com.hotel.management.service.impl.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody User user){
		Response response=userService.register(user);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequestDto loginRequest){
		Response response=userService.login(loginRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
}
