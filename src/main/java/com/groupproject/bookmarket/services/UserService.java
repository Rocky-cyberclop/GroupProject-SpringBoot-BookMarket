package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.dtos.AuthRequest;
import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

	boolean addUser (AuthRequest authRequest);
	ResponseEntity<String> forgotPass(String code, String mail);

	ResponseEntity<String> changePass(String mail, String newPass);

	String Authentication(String token);

	ResponseEntity<UserDto> getProfile(String token);

	boolean saveProfile(String username, String fullname, String phone, String address, MultipartFile avatar, String token) throws IOException;

	ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage);
}
