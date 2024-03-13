package com.groupproject.bookmarket.services;

import org.springframework.stereotype.Service;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.requests.AuthRequest;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

@Service
public interface UserService {
	User saveUser(User user);
    ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage);
    boolean addUser(AuthRequest authRequest);
    public ResponseEntity<String> forgotPass(String code, String mail);
    public ResponseEntity<String> changePass(String mail, String newPass);
}
