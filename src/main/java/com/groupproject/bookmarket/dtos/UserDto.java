package com.groupproject.bookmarket.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupproject.bookmarket.models.CartItem;
import com.groupproject.bookmarket.models.Comment;
import com.groupproject.bookmarket.models.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
