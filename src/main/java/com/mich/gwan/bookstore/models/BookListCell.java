/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.models;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BookListCell extends ListCell<Book> {
    /**
     * layout for listView
     */
    final ImageView bookImageView = new ImageView();
    final Label bookTitle = new Label();
    final Label bookAuthor = new Label();
    final Button bookSell = new Button("SELL");
    final VBox layout = new VBox();

    public BookListCell(){
        super();
        // apply style
        bookTitle.setStyle("-fx-font-size: 14px; -fx-font-name: System Bold");
        bookTitle.setAlignment(Pos.CENTER);
        bookTitle.setPrefHeight(17.0);
        bookTitle.setPrefWidth(189.0);
        bookTitle.setText("BOOK TITLE");
        bookTitle.setWrapText(true);

        bookAuthor.setStyle("-fx-font-size: 14px;");
        bookAuthor.setAlignment(Pos.CENTER);
        bookAuthor.setPrefHeight(17.0);
        bookAuthor.setPrefWidth(189.0);
        bookAuthor.setText("AUTHOR NAME");
        bookAuthor.setWrapText(true);

        bookSell.setMnemonicParsing(false);
        bookSell.setPrefHeight(16.0);
        bookSell.setPrefWidth(60.0);
        bookSell.setStyle("-fx-background-color: blue;");
        bookSell.setText("SELL");

        bookImageView.setStyle("-fx-border-color: darkblue; -fx-border-insets: 3; -fx-border-radius: 7; -fx-border-width: 1.0");
        bookImageView.setPreserveRatio(true);
        bookImageView.setFitHeight(170.0);
        bookImageView.setFitWidth(135.0);
        bookImageView.setPickOnBounds(true);

        layout.setPrefWidth(142);
        layout.setPrefHeight(245);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().add(bookImageView);
        layout.getChildren().add(bookTitle);
        layout.getChildren().add(bookAuthor);
        layout.getChildren().add(bookSell);
        //layout.setMaxHeight() maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="245.0" prefWidth="142.0"
    }
        @Override
        protected void updateItem(Book par, boolean empty){
            super.updateItem(par,empty);
            setText(null);
            if (empty || par == null){
                setText(null);
                bookImageView.setImage(null);
                bookTitle.setText(null);
                bookAuthor.setText(null);
                setGraphic(null);
            } else {
                bookImageView.setImage(par.getBookIcon());
                bookTitle.setText(par.getBookName());
                bookAuthor.setText(par.getBookAuthor());
                setGraphic(layout);
            }
        }
}

