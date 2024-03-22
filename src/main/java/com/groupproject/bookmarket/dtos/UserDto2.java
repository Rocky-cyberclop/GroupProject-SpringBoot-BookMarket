package com.groupproject.bookmarket.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto2 {
    private Long id;
    private String username;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private String role;

    private String avatar;
}