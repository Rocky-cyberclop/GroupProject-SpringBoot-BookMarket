package com.groupproject.bookmarket.dtos;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public interface UserDto {
    public Long getId();
    public String getUsername();
    public String getFullName();
    public String getEmail();
    public String getPhone();
    public String getAddress();
    public String getRole();
    public String getAvatar();
}
