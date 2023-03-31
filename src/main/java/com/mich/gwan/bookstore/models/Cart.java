/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.models;

public class Cart extends Book{
    private int cartId;
    private int saleAmount;

    public int getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(int saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Cart(){}

    public Cart(String userName, String categoryName, String bookName, String bookAuthor) {
        super(userName, categoryName, bookName, bookAuthor);
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
