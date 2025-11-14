package com.hotel.management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel.management.dto.LoginRequestDto;
import com.hotel.management.dto.Response;
import com.hotel.management.dto.UserDto;
import com.hotel.management.entity.User;
import com.hotel.management.exception.OurException;
import com.hotel.management.repository.UserRepository;
import com.hotel.management.service.interfaces.IUserService;
import com.hotel.management.utils.JWTUtils;
import com.hotel.management.utils.Utils;


@Service

public class UserService implements IUserService {

	@Autowired private UserRepository userRepo;
	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private JWTUtils jwtUtils;
	@Autowired private AuthenticationManager authenticationManager;

	
	
	@Override
	public Response register(User user) {
		Response response=new Response();
		try {
			if(user.getRole()==null || user.getRole().isBlank()) {
				user.setRole("USER");
			}
			
			if(userRepo.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail()+ "email already exist....");
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User savedUser=userRepo.save(user);
			UserDto userDto=	Utils.mapUserEntityToUserDTO(savedUser);
			response.setStatusCode(200);
			response.setUser(userDto);
			
		}catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());

			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured during Execurtion... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response login(LoginRequestDto loginRequests) {
		Response response=new Response();
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequests.getEmail(), loginRequests.getPassword()));
			
			var user=userRepo.findByEmail(loginRequests.getEmail()).orElseThrow(()->new OurException("User not found..."));
			
			var jwt=jwtUtils.generateToken(user);
			response.setStatusCode(200);
			response.setToken(jwt);
			response.setRole(user.getRole());
			response.setExpirationTime("7 Days");
			response.setMessage("Successfull");
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());

			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured during User Login... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getAllUsers() {
		Response response=new Response();
		try {
			
			List<User> user=userRepo.findAll();
			List<UserDto> userListDto=Utils.mapUserListEntityToUserListDTO(user);
			
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setUserList(userListDto);	
	
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in getting all Users... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getUserBookingHistory(String userId) {
		Response response=new Response();
		try {
			User user=userRepo.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
			UserDto userDto=Utils.mapUserEntityToUserDTOPlusUserBookingsAndRoom(user);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setUser(userDto);
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in getting all Users... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response deleteUser(String userId) {
		Response response=new Response();
		try {
			userRepo.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
			userRepo.deleteById(Long.valueOf(userId));
			response.setStatusCode(200);
			response.setMessage("Successfull");
			
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in getting all Users... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getUserById(String userId) {
		Response response=new Response();
		try {
			User user=userRepo.findById(Long.valueOf(userId)).orElseThrow(()->new OurException("User not found"));
			UserDto userDto=Utils.mapUserEntityToUserDTO(user);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setUser(userDto);	
		
		
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in getting Users... "+e.getMessage());			

		}
		return response;
	}

	@Override
	public Response getMyInfo(String email) {
		Response response=new Response();
		try {
			User user=userRepo.findByEmail(email).orElseThrow(()->new OurException("User not found"));
			UserDto userDto=Utils.mapUserEntityToUserDTO(user);
			response.setStatusCode(200);
			response.setMessage("Successfull");
			response.setUser(userDto);	
		
		
		}catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
			
		}catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occured in getting Users... "+e.getMessage());			

		}
		return response;
	}

}
