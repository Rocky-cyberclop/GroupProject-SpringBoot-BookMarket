package com.groupproject.bookmarket.responses;

import com.groupproject.bookmarket.models.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse  implements Serializable {
    private Long cartId;
    private Integer cartQuantity;
    private ListBook book;

}
