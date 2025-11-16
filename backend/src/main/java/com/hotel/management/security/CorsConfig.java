package com.hotel.management.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {//WebMvcConfigurer is an interface provided by Spring Framework that allows you to customize the Spring MVC configuration without overriding the entire setup.
			
			@Override
			public void addCorsMappings(CorsRegistry registry) { //This allows your backend to accept requests from any frontend, regardless of where it’s hosted.
					registry.addMapping("/**")
							.allowedMethods("GET","POST","PUT","DELETE")
							.allowedOrigins("*");
					
			}
		};
	}

}
