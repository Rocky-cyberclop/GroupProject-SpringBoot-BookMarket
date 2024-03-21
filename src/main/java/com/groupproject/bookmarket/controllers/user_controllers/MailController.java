package com.groupproject.bookmarket.controllers.user_controllers;

import com.groupproject.bookmarket.models.MailStructure;
import com.groupproject.bookmarket.services.MailService;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail){
        mailService.sendMail(mail);
        return "Successfully send mail";
    }

    @GetMapping("/valid/{mail}/{code}")
    public ResponseEntity<String> forgotPass(@PathVariable String mail, @PathVariable String code){
        return userService.forgotPass(code,mail);
    }
}