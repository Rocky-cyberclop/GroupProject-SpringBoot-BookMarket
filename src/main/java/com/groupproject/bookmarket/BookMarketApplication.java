package com.groupproject.bookmarket;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableJpaRepositories
@EnableWebSecurity
public class BookMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMarketApplication.class, args);
	}
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
//			User user = new User();
//			user.setUsername("Long111");
//			user.setFullName("LeHoangLong");
//			user.setPassword("longvipzzz");
//			user.setEmail("hoanglong@gmail.com");
//			user.setPhone("0900402");
//			user.setAddress("ffffff");
//			userService.saveUser(user);

//			User user1 = new User();
//			user1.setUsername("Long112");
//			user1.setFullName("LeHoangLong");
//			user1.setPassword("longvipzzz");
//			user1.setEmail("hoanglong@gmail1.com");
//			user1.setPhone("0900402");
//			user1.setAddress("ffffff");
//			userService.saveUser(user1);
			System.out.println("Done");
		};
	}

}
