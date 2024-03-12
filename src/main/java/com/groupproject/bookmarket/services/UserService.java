package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.requests.AuthRequest;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage);

    boolean addUser(AuthRequest authRequest);
    public ResponseEntity<String> forgotPass(String code, String mail);
    public ResponseEntity<String> changePass(String mail, String newPass);
}
