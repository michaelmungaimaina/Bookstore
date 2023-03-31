/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.models;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;

public class Book extends Category{
    private int bookId;
    private StringProperty bookName = new SimpleStringProperty();
    private StringProperty bookAuthor = new SimpleStringProperty();
    private Image bookIcon;

    public Book() {
    }

    public Book(String userName, String categoryName, String bookName, String bookAuthor, Image bookIcon) {
        super(userName, categoryName);
        this.bookName.setValue(bookName);
        this.bookAuthor.setValue(bookAuthor);
        this.bookIcon = bookIcon;
    }
    public Book(String userName, String categoryName, String bookName, String bookAuthor) {
        super(userName, categoryName);
        this.bookName.setValue(bookName);
        this.bookAuthor.setValue(bookAuthor);
    }

    public Book(String categoryName, int bookId, String bookName, String bookAuthor) {
        super(categoryName);
        this.bookId = bookId;
        this.bookName.setValue(bookName);
        this.bookAuthor.setValue(bookAuthor);
    }



    public Image getBookIcon() {
        return bookIcon;
    }

    public void setBookIcon(Image bookIcon) {
        this.bookIcon = bookIcon;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName.get();
    }

    public void setBookName(String bookName) {
        this.bookName.setValue(bookName);
    }

    public String getBookAuthor() {
        return bookAuthor.get();
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor.setValue(bookAuthor);
    }

    public StringProperty getBookNameProperty(){
        return bookName;
    }
    public StringProperty getBookAuthorProperty(){
        return bookAuthor;
    }
}
