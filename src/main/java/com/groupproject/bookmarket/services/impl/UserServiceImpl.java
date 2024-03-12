package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.requests.AuthRequest;
import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.CodeTmpService;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CodeTmpService codeTmpService;
    @Override
    public ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage) {
        if ( q == null || q.isEmpty()) {
            q = "%";
        } else {
            q = "%" + q + "%";
        }
        Pageable pageable = PageRequest.of(cPage - 1, size);
        Page<UserDto> page = userRepository.findByFullNameLikeIgnoreCaseOrEmailLikeIgnoreCaseOrAddressLikeIgnoreCase(pageable, q, q, q);
        Pagination pagination = Pagination.builder()
                .currentPage(cPage)
                .size(size)
                .totalPage(page.getTotalPages())
                .totalResult((int) page.getTotalElements())
                .build();
        PaginationResponse paginationResponse = PaginationResponse.builder()
                .data(page.getContent())
                .pagination(pagination)
                .build();
        return new ResponseEntity<>(paginationResponse, HttpStatus.OK);
    }

    @Override
    public boolean addUser(AuthRequest authRequest) {
        User user = new User();
        user.setEmail(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRole("USER");
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseEntity<String> forgotPass(String code, String mail){
        if (codeTmpService.validateCode(mail, code)){
            return ResponseEntity.status(HttpStatus.OK).body("200");
        }
        else{
//			codeTmpService.deleteCode(mail);
            return ResponseEntity.status(HttpStatus.OK).body("400");
        }
    }

    @Override
    public ResponseEntity<String> changePass(String mail, String newPass){
        User user = userRepository.findByEmail(mail).get();
        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("200");
    }

}
