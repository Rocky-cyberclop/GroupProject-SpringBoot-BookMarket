package com.groupproject.bookmarket.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {

    @NotBlank(message = "Email không được để trống")
    @Size(min = 5, max = 160, message = "Email phải từ 5 đến 160 ký tự")
    @Email(message = "Email không hợp lệ")
    private String username ;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 160, message = "Mật khẩu phải từ 6 đến 160 ký tự")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}