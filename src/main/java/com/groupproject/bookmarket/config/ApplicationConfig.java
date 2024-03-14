package com.groupproject.bookmarket.config;

import com.groupproject.bookmarket.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
    private final UserRepository userRepository;

//    @Bean
////    public UserDetailsService userDetailsService(){
////        return email -> userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Email not found"));
////    }

}
