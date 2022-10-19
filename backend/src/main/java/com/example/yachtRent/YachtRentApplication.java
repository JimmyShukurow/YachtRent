package com.example.yachtRent;

import com.example.yachtRent.entity.UserEntity;
import com.example.yachtRent.request.RegisterRequest;
import com.example.yachtRent.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YachtRentApplication {

	public static void main(String[] args) {
		SpringApplication.run(YachtRentApplication.class, args);
	}

//	@Bean
//	CommandLineRunner runner(UserService userService) {
//		return args -> {
//			var userRequest = new RegisterRequest("admin", "test", "admin@gmail.com", "123456");
//			userService.register(userRequest);
//			userService.saveRole("admin");
//			userService.addRoleToUser(1L,1L);
//
//		};
//	}
}


