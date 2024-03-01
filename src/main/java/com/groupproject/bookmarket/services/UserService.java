package com.groupproject.bookmarket.services;

import org.springframework.stereotype.Service;

import com.groupproject.bookmarket.models.User;

@Service
public interface UserService {
	User saveUser(User user);
}
