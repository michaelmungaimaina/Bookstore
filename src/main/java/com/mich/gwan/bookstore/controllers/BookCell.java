/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.controllers;

import com.mich.gwan.bookstore.dao.DataAccessObject;
import com.mich.gwan.bookstore.models.Book;
import com.mich.gwan.bookstore.models.Category;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookCell extends ListCell<Book> {

    public ImageView iconView;
    public Label bookLabel;
    public Label authorLabel;
    public Button sellButton;

    public BookCell(){
        loadFxml();
    }

    private void loadFxml() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mich/gwan/bookstore/BookCell.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException ex){
            //throw new RuntimeException(ex);
            Logger.getLogger(BookCell.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        }
    }

    @Override
    protected void updateItem(Book par, boolean empty){
        super.updateItem(par,empty);
        if (empty || par == null || par.getBookName() == null){
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        } else{
            bookLabel.setText(par.getBookName());
            authorLabel.setText(par.getBookAuthor());
            iconView.setImage(par.getBookIcon());
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}
