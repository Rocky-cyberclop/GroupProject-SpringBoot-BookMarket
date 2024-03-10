package com.groupproject.bookmarket.services.impl;

import com.groupproject.bookmarket.dtos.UserDto;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.User;
import com.groupproject.bookmarket.repositories.UserRepository;
import com.groupproject.bookmarket.responses.Pagination;
import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
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
