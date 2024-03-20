package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.AuthRequest;
import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.services.CodeTmpService;
import com.groupproject.bookmarket.services.JwtService;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.web.multipart.MultipartFile;

import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UserServiceImpl implements UserService{


	@Autowired
	private CloudinaryService cloudinaryService;
	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private CodeTmpService codeTmpService;

	@Autowired
	private JwtService jwtService;
	@Override
	public boolean addUser(AuthRequest authRequest) {
        Optional<User> users = userRepository.findByEmail(authRequest.getUsername());

        if(users.isEmpty()){
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
        }
        else{
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
		user.setPassword(bCryptPasswordEncoder.encode(newPass));
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

	@Override
	public String Authentication(String token){
		if(token != null && token.startsWith("Bearer ")){
			token = token.substring(7);
		}
		String username = jwtService.extractUsername(token);
		Optional<User> user = userRepository.findByEmail(username);
		if(user.isPresent()){
			return username;
		}
		else {
			return null;
		}

	}

	@Override
	public ResponseEntity<UserDto> getProfile(String token) {
		String email = Authentication(token);
		User user = userRepository.findByEmail(email).orElse(null);
		if(user != null){
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setUsername(user.getUsername());
			userDto.setEmail(user.getEmail());
			userDto.setFullName(user.getFullName());
			userDto.setPhone(user.getPhone());
			userDto.setAvatar(user.getAvatar());
			userDto.setAddress(user.getAddress());
			return ResponseEntity.ok(userDto);
		}
		else{
			return null;
		}
	}

	@Override
	public boolean saveProfile(String username, String fullname, String phone, String address, MultipartFile avatar, String token) throws IOException, IOException {
		if(avatar != null){
			String email = Authentication(token);
			User user = userRepository.findByEmail(email).orElse(null);
			if(user != null){
				user.setUsername(username);
				user.setFullName(fullname);
				user.setPhone(phone);
				user.setAddress(address);
				user.setAvatar(cloudinaryService.uploadImage(avatar));
				userRepository.save(user);
				return true;
			}
			else {
				return false;
			}
		}
		else{
			String email = Authentication(token);
			User user = userRepository.findByEmail(email).orElse(null);
			if(user != null){
				user.setUsername(username);
				user.setFullName(fullname);
				user.setPhone(phone);
				user.setAddress(address);
				userRepository.save(user);
				return true;
			}
			else {
				return false;
			}
		}

	}

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

}
