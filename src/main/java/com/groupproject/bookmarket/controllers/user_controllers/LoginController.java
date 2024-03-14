package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.payload.ResponseData;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.requests.AuthRequest;
import com.groupproject.bookmarket.services.JwtService;
import com.groupproject.bookmarket.services.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                Object principal = authentication.getPrincipal();
                UserDetails userDetails = (UserDetails) principal;
                Optional<User> user = repository.findByEmail(userDetails.getUsername());
                String token = jwtService.generateToken(authRequest.getUsername(), user.get().getRole());
                System.out.println(user.get().getRole());
                return ResponseEntity.status(HttpStatus.OK).body(token);
            } else {
                throw new UsernameNotFoundException("Tài khoản hoặc mật khẩu không chính xác");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Tài khoản hoặc mật khẩu không chính xác", e);
        }
    }
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<String> handleAuthenticationException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tài khoản hoặc mật khẩu không chính xác");
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@RequestBody AuthRequest authRequest){
        ResponseData responseData = new ResponseData();
        responseData.setData(userService.addUser(authRequest));
        return new ResponseEntity<>(responseData,HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<String> changePass(@RequestBody Map<String,String> resData){
        String password = resData.get("password");
        String mail = resData.get("username");
        return userService.changePass(mail,password);
    }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return repository.findAll();
    }
}
