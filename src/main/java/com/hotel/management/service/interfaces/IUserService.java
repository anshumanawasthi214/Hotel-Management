package com.hotel.management.service.interfaces;

import com.hotel.management.dto.LoginRequestDto;
import com.hotel.management.dto.Response;
import com.hotel.management.entity.User;

public interface IUserService {

	
	Response register(User user);
	
	Response login(LoginRequestDto loginRequests);
	
	Response getAllUsers();
	
	Response getUserBookingHistory(String userId);
	
	Response deleteUser(String userId);
	
	Response getUserById(String userId);
	
	Response getMyInfo(String email);
}
