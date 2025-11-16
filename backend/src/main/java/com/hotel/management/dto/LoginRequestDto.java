package com.hotel.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

	@NotBlank(message="Email cant be blank")
	private String email;
	@NotBlank(message="Password is required")
	private String password;
}
