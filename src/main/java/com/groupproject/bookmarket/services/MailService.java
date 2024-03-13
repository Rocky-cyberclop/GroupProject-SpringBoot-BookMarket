package com.groupproject.bookmarket.services;

import com.groupproject.bookmarket.services.CodeTmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private CodeTmpService codeTmpService;

    @Value("${spring.mail.username}")
    private String fromMail;


    public void sendMail(String mail){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("You valid code");
        simpleMailMessage.setText(codeTmpService.generateCodeTmp(mail));
        simpleMailMessage.setTo(mail);
        javaMailSender.send(simpleMailMessage);
    }


}
