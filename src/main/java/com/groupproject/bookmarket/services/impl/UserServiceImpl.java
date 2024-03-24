package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.AuthRequest;
import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.dtos.UserDto2;
import com.groupproject.bookmarket.requests.RegisterAdminRequest;
import com.groupproject.bookmarket.responses.MyResponse;
import com.groupproject.bookmarket.services.CodeTmpService;
import com.groupproject.bookmarket.services.JwtService;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.groupproject.bookmarket.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.context.Context;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CodeTmpService codeTmpService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String OTP_CHARACTERS = "0123456789";
    private static final String PASS_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM!@#$%^&*";

    public String generateRandomString(int length, String characters) {
        // a class in java.security
        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }
        return result.toString();
    }

    @Override
    public boolean addUser(AuthRequest authRequest) {
        Optional<User> users = userRepository.findByEmail(authRequest.getUsername());

        if (users.isEmpty()) {
            User user = new User();
            user.setEmail(authRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));
            user.setRole("USER");
            try {
                userRepository.save(user);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public ResponseEntity<String> forgotPass(String code, String mail) {
        if (codeTmpService.validateCode(mail, code)) {
            return ResponseEntity.status(HttpStatus.OK).body("200");
        } else {
//			codeTmpService.deleteCode(mail);
            return ResponseEntity.status(HttpStatus.OK).body("400");
        }
    }

    @Override
    public ResponseEntity<String> changePass(String mail, String newPass) {
        User user = userRepository.findByEmail(mail).get();
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("200");
    }

    @Override
    public String Authentication(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String username = jwtService.extractUsername(token);
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return username;
        } else {
            return null;
        }

    }

    @Override
    public ResponseEntity<UserDto2> getProfile(String token) {
        String email = Authentication(token);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            UserDto2 userDto = new UserDto2();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setFullName(user.getFullName());
            userDto.setPhone(user.getPhone());
            userDto.setAvatar(user.getAvatar());
            userDto.setAddress(user.getAddress());
            return ResponseEntity.ok(userDto);
        } else {
            return null;
        }
    }

    @Override
    public boolean saveProfile(String username, String fullname, String phone, String address, MultipartFile avatar, String token) throws IOException, IOException {
        if (avatar != null) {
            String email = Authentication(token);
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.setUsername(username);
                user.setFullName(fullname);
                user.setPhone(phone);
                user.setAddress(address);
                user.setAvatar(cloudinaryService.uploadImage(avatar));
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        } else {
            String email = Authentication(token);
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.setUsername(username);
                user.setFullName(fullname);
                user.setPhone(phone);
                user.setAddress(address);
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        }

    }

	@Override
	public Long getUserId(String token) {
		String email = Authentication(token);
		User user = userRepository.findByEmail(email).orElse(null);
		if(user != null){
			return user.getId();
		}
		return null;
	}

	@Override
    public ResponseEntity<PaginationResponse> searchPaginateUserByFullNameAndEmail(String q, int size, int cPage) {
        if (q == null || q.isEmpty()) {
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
    @Transactional
    public ResponseEntity<MyResponse> addNewAdminUser(AuthRequest authRequest) {
        MyResponse myResponse = new MyResponse();
        Optional<User> users = userRepository.findByEmail(authRequest.getUsername());

        if (users.isEmpty()) {
            User user = new User();
            user.setEmail(authRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(authRequest.getPassword()));
            user.setRole("ADMIN");

            userRepository.save(user);
            myResponse.setMessage("Register successfully, check your email for more detail!");

        } else {
            myResponse.setMessage("This username is already exist!");
            myResponse.setRspCode("400");
            myResponse.setState("error");
        }
        return new ResponseEntity<>(myResponse, HttpStatus.OK);
    }

    @Override
    @Cacheable(value = "codeTmp", key = "#email")
    public ResponseEntity<MyResponse> sendOtpRegisterCode(String email) {
        MyResponse responseMessage = new MyResponse();
        Optional<User> user = userRepository.findByEmail(email);
        // create otp code
        String code = generateRandomString(6, OTP_CHARACTERS);

        // create context for send mail
        Context context = new Context();
        context.setVariable("code", code);
        if (user.isEmpty()) {
            context.setVariable("message", "Thank you for choosing BOOK STORE. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes.");
            responseMessage.setMessage("Receive otp code in your email!");
            mailService.sendEmailWithHtmlTemplate(email, "Code OTP", "otpCode", context);
        } else {
            if (!user.get().getRole().equals("ADMIN")) {
                context.setVariable("message", "Thank you for choosing Moon Movie. Use the following OTP to complete your Update your account to business role procedures. OTP is valid for 5 minutes.");
                responseMessage.setMessage("This email already has an account! We will update it to admin account.");
                mailService.sendEmailWithHtmlTemplate(email, "Code OTP", "otpCode", context);
            } else {
                context.setVariable("message", "Thank you for choosing BOOK STORE. We would like to inform you that this email has a ADMIN account. You can return to the login page and start working right away.");
                responseMessage.setMessage("This email already has an ADMIN account!");
                mailService.sendEmailWithHtmlTemplate(email, "BOOK STORE Inform", "normalMail", context);
            }
        }
        // send mail and save otp code in redis
        redisTemplate.opsForValue().set(email, code, 6, TimeUnit.MINUTES);
        return ResponseEntity.ok(responseMessage);
    }

    @Override
    @Transactional
    public ResponseEntity<MyResponse> createNewBusinessAccount(RegisterAdminRequest request) {
        MyResponse responseMessage = new MyResponse();
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        String code = redisTemplate.opsForValue().get(request.getEmail());
        if (request.getCode()!= null && request.getCode().equals(code)) {
            redisTemplate.delete(request.getEmail());
            String username = request.getEmail();
            String password = generateRandomString(8, PASS_CHARACTERS);
            Context context = new Context();
            if (user.isEmpty()) {
                User newUser = User.builder()
                        .avatar("http://localhost:8080/uploads/images/avatar/no_image.png")
                        .email(request.getEmail())
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .role("ADMIN")
                        .build();
                userRepository.save(newUser);

                //send info to user through mail
                context.setVariable("username", username);
                context.setVariable("password", password);
                mailService.sendEmailWithHtmlTemplate(request.getEmail(), "Your business account", "adminResMail", context);
            } else {
                user.get().setRole("ADMIN");
                userRepository.save(user.get());
                context.setVariable("message", "Your account has been upgraded to a business account. Now you can start selling with us.");
                mailService.sendEmailWithHtmlTemplate(request.getEmail(), "Your business account", "normalMail", context);
            }
            responseMessage.setMessage("The otp code is valid");
        } else {
            responseMessage.setMessage("The opt code is invalid");
            responseMessage.setState("error");
            responseMessage.setRspCode("400");
        }
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

}
