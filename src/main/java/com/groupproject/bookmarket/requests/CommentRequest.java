package com.groupproject.bookmarket.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupproject.bookmarket.models.Book;
import com.groupproject.bookmarket.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private Long id;
    private Short rating;
    private String content;
    private String fullNameUser;
    private String emailUser;
    private String avatarUser;
}
