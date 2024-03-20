package com.groupproject.bookmarket.controllers.admin_controllers;

import com.groupproject.bookmarket.responses.PaginationResponse;
import com.groupproject.bookmarket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/admin/user")
public class UserController {
    @Autowired
    private UserService customerService;

    @GetMapping("/search")
    public ResponseEntity<PaginationResponse> fetchPaginateUserByFullNameAndEmail(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                                                                       @RequestParam(value = "size", required = false, defaultValue = "6") int size,
                                                                       @RequestParam(value = "cPage", required = false, defaultValue = "1") int cPage) {
        return customerService.searchPaginateUserByFullNameAndEmail(q, size, cPage);
    }
}
