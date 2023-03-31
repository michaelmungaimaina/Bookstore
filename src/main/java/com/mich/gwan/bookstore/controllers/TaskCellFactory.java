/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.controllers;

import com.mich.gwan.bookstore.models.Book;
import com.mich.gwan.bookstore.models.Category;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class TaskCellFactory implements Callback<ListView<Book>, ListCell<Book>> {
    @Override
    public ListCell<Book> call(ListView<Book> param) {
        return null;
    }
}
