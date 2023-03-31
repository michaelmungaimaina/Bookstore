package com.mich.gwan.bookstore.controllers;

import com.mich.gwan.bookstore.dao.DataAccessObject;
import com.mich.gwan.bookstore.models.Book;
import com.mich.gwan.bookstore.models.BookListCell;
import com.mich.gwan.bookstore.models.Cart;
import com.mich.gwan.bookstore.models.User;
import com.mich.gwan.bookstore.validation.InputValidation;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class MainController implements Initializable {

    public AnchorPane anchorPane;
    public Button logOut;
    public ImageView userIconImageView;
    public Label userName;
    public Label titleLabel;
    public TextField bookFilterTextField;
    public Button storeButton;
    public Button libraryButton;
    public Button buttonAddMyBook;
    public Button buttonPracticalManual;
    public Button buttonHandwrittenBook;
    public Button buttonTextBook;
    public ListView<Book> textBooksListView;
    public ListView<Book> handWrittenListView;
    public ListView<Book> practicalBooksListView;
    public ListView<Book> myBooksListView;
    public HBox myBookHbox;
    private SceneController sceneController;
    private Stage stage;
    private Parent parent;
    private Scene scene;
    private static ObservableList<User> loggedUser;
    private ObservableList<Book> myBook, practicalBooks, handWrittenBooks, textBooks;
    private DataAccessObject dataAccessObject;
    private BookListCell bookListCell;
    private Circle clip;
    private  User userLogged;
    private InputValidation inputValidation;
    String userLogger;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataAccessObject = new DataAccessObject();
        bookListCell = new BookListCell();
        userLogged = new User();
        inputValidation = new InputValidation();

        clip  = new Circle(300,200,200);
        // get logged user
        userLogger = dataAccessObject.getSessionName();

        myBook = FXCollections.observableArrayList();
        myBook.addAll(dataAccessObject.getMyBooks("MY BOOK"));
        practicalBooks = FXCollections.observableArrayList();
        practicalBooks.addAll(dataAccessObject.getMyBooks("PRACTICAL BOOK"));
        handWrittenBooks = FXCollections.observableArrayList();
        handWrittenBooks.addAll(dataAccessObject.getMyBooks("HANDWRITTEN BOOK"));
        textBooks = FXCollections.observableArrayList();
        textBooks.addAll(dataAccessObject.getMyBooks("TEXT BOOK"));

        // display user icon
        // display user_name
        userName.setText(dataAccessObject.getSessionName());
        userIconImageView.setImage(dataAccessObject.getSessionImage());
        userIconImageView.setPreserveRatio(true);

        // Create new cell for listView
        myBooksListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> param) {
                ListCell<Book> cell = new ListCell<Book>(){

                    @Override
                    protected void updateItem(Book par, boolean empty){
                        /**
                         * layout for listView
                         */
                        final ImageView bookImageView = new ImageView();
                        final Label bookTitle = new Label();
                        final Label bookAuthor = new Label();
                        final Button bookSell = new Button("SELL");
                        final VBox layout = new VBox(bookImageView,bookTitle,bookAuthor,bookSell);

                        bookTitle.setStyle("-fx-font-size: 10px; -fx-font-name: System Bold");
                        bookTitle.setAlignment(Pos.CENTER);
                        bookTitle.setPrefHeight(16.0);
                        bookTitle.setPrefWidth(60.0);
                        bookTitle.setWrapText(true);

                        bookAuthor.setStyle("-fx-font-size: 10px;");
                        bookAuthor.setAlignment(Pos.CENTER);
                        bookAuthor.setPrefHeight(9.0);
                        bookAuthor.setPrefWidth(60.0);
                        bookAuthor.setWrapText(true);

                        bookSell.setMnemonicParsing(false);
                        bookSell.setPrefHeight(8.0);
                        bookSell.setPrefWidth(60.0);
                        bookSell.setStyle("-fx-background-color: blue;");

                        bookImageView.setStyle("-fx-border-color: darkblue; -fx-border-insets: 3; -fx-border-radius: 7; -fx-border-width: 1.0");
                        bookImageView.setPreserveRatio(true);
                        bookImageView.setFitHeight(40.0);
                        bookImageView.setFitWidth(45.0);
                        bookImageView.setPickOnBounds(true);

                        layout.setPrefWidth(60);
                        layout.setPrefHeight(90);
                        layout.setAlignment(Pos.CENTER);
                        super.updateItem(par,empty);
                        if (par != null){
                            bookTitle.setText(par.getBookName());
                            bookAuthor.setText(par.getBookAuthor());
                            bookImageView.setImage(par.getBookIcon());
                            setGraphic(layout);

                            bookSell.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Point2D point2D = bookSell.localToScene(0.0,0.0);
                                    double x = stage.getX() + point2D.getX();
                                    double y = stage.getY() + point2D.getY() + 48;
                                    showContextMenu(myBooksListView.getSelectionModel().getSelectedIndex() - 1, myBook, x, y);
                                }
                            });
                        } else{
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });

        practicalBooksListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> param) {
                ListCell<Book> cell = new ListCell<Book>(){

                    @Override
                    protected void updateItem(Book par, boolean empty){
                        /**
                         * layout for listView
                         */
                        final ImageView bookImageView = new ImageView();
                        final Label bookTitle = new Label();
                        final Label bookAuthor = new Label();
                        final Button bookSell = new Button("SELL");
                        final VBox layout = new VBox(bookImageView,bookTitle,bookAuthor,bookSell);

                        bookTitle.setStyle("-fx-font-size: 10px; -fx-font-name: System Bold");
                        bookTitle.setAlignment(Pos.CENTER);
                        bookTitle.setPrefHeight(16.0);
                        bookTitle.setPrefWidth(60.0);
                        bookTitle.setWrapText(true);

                        bookAuthor.setStyle("-fx-font-size: 10px;");
                        bookAuthor.setAlignment(Pos.CENTER);
                        bookAuthor.setPrefHeight(9.0);
                        bookAuthor.setPrefWidth(60.0);
                        bookAuthor.setWrapText(true);

                        bookSell.setMnemonicParsing(false);
                        bookSell.setPrefHeight(8.0);
                        bookSell.setPrefWidth(60.0);
                        bookSell.setStyle("-fx-background-color: blue;");

                        bookImageView.setStyle("-fx-border-color: darkblue; -fx-border-insets: 3; -fx-border-radius: 7; -fx-border-width: 1.0");
                        bookImageView.setPreserveRatio(true);
                        bookImageView.setFitHeight(40.0);
                        bookImageView.setFitWidth(45.0);
                        bookImageView.setPickOnBounds(true);

                        layout.setPrefWidth(60);
                        layout.setPrefHeight(90);
                        layout.setAlignment(Pos.CENTER);
                        super.updateItem(par,empty);
                        if (par != null){
                            bookTitle.setText(par.getBookName());
                            bookAuthor.setText(par.getBookAuthor());
                            bookImageView.setImage(par.getBookIcon());
                            setGraphic(layout);

                            bookSell.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Point2D point2D = bookSell.localToScene(0.0,0.0);
                                    double x = stage.getX() + point2D.getX();
                                    double y = stage.getY() + point2D.getY() + 48;
                                    showContextMenu(practicalBooksListView.getSelectionModel().getSelectedIndex() - 1, practicalBooks, x, y);
                                }
                            });

                        } else{
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        textBooksListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> param) {
                ListCell<Book> cell = new ListCell<Book>(){

                    @Override
                    protected void updateItem(Book par, boolean empty){
                        /**
                         * layout for listView
                         */
                        final ImageView bookImageView = new ImageView();
                        final Label bookTitle = new Label();
                        final Label bookAuthor = new Label();
                        final Button bookSell = new Button("SELL");
                        final VBox layout = new VBox(bookImageView,bookTitle,bookAuthor,bookSell);

                        bookTitle.setStyle("-fx-font-size: 10px; -fx-font-name: System Bold");
                        bookTitle.setAlignment(Pos.CENTER);
                        bookTitle.setPrefHeight(16.0);
                        bookTitle.setPrefWidth(60.0);
                        bookTitle.setWrapText(true);

                        bookAuthor.setStyle("-fx-font-size: 10px;");
                        bookAuthor.setAlignment(Pos.CENTER);
                        bookAuthor.setPrefHeight(9.0);
                        bookAuthor.setPrefWidth(60.0);
                        bookAuthor.setWrapText(true);

                        bookSell.setMnemonicParsing(false);
                        bookSell.setPrefHeight(8.0);
                        bookSell.setPrefWidth(60.0);
                        bookSell.setStyle("-fx-background-color: blue;");

                        bookImageView.setStyle("-fx-border-color: darkblue; -fx-border-insets: 3; -fx-border-radius: 7; -fx-border-width: 1.0");
                        bookImageView.setPreserveRatio(true);
                        bookImageView.setFitHeight(40.0);
                        bookImageView.setFitWidth(45.0);
                        bookImageView.setPickOnBounds(true);

                        layout.setPrefWidth(60);
                        layout.setPrefHeight(90);
                        layout.setAlignment(Pos.CENTER);
                        super.updateItem(par,empty);
                        if (par != null){
                            bookTitle.setText(par.getBookName());
                            bookAuthor.setText(par.getBookAuthor());
                            bookImageView.setImage(par.getBookIcon());
                            setGraphic(layout);

                            bookSell.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Point2D point2D = bookSell.localToScene(0.0,0.0);
                                    double x = stage.getX() + point2D.getX();
                                    double y = stage.getY() + point2D.getY() + 48;
                                    showContextMenu(textBooksListView.getSelectionModel().getSelectedIndex() - 1, textBooks, x, y);
                                }
                            });

                        } else{
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        handWrittenListView.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
            @Override
            public ListCell<Book> call(ListView<Book> param) {
                ListCell<Book> cell = new ListCell<Book>(){

                    @Override
                    protected void updateItem(Book par, boolean empty){
                        /**
                         * layout for listView
                         */
                        final ImageView bookImageView = new ImageView();
                        final Label bookTitle = new Label();
                        final Label bookAuthor = new Label();
                        final Button bookSell = new Button("SELL");
                        final VBox layout = new VBox(bookImageView,bookTitle,bookAuthor,bookSell);

                        bookTitle.setStyle("-fx-font-size: 10px; -fx-font-name: System Bold");
                        bookTitle.setAlignment(Pos.CENTER);
                        bookTitle.setPrefHeight(16.0);
                        bookTitle.setPrefWidth(60.0);
                        bookTitle.setWrapText(true);

                        bookAuthor.setStyle("-fx-font-size: 10px;");
                        bookAuthor.setAlignment(Pos.CENTER);
                        bookAuthor.setPrefHeight(9.0);
                        bookAuthor.setPrefWidth(60.0);
                        bookAuthor.setWrapText(true);

                        bookSell.setMnemonicParsing(false);
                        bookSell.setPrefHeight(8.0);
                        bookSell.setPrefWidth(60.0);
                        bookSell.setStyle("-fx-background-color: blue;");

                        bookImageView.setStyle("-fx-border-color: darkblue; -fx-border-insets: 3; -fx-border-radius: 7; -fx-border-width: 1.0");
                        bookImageView.setPreserveRatio(true);
                        bookImageView.setFitHeight(40.0);
                        bookImageView.setFitWidth(45.0);
                        bookImageView.setPickOnBounds(true);

                        layout.setPrefWidth(60);
                        layout.setPrefHeight(90);
                        layout.setAlignment(Pos.CENTER);
                        super.updateItem(par,empty);
                        if (par != null){
                            bookTitle.setText(par.getBookName());
                            bookAuthor.setText(par.getBookAuthor());
                            bookImageView.setImage(par.getBookIcon());
                            setGraphic(layout);

                            bookSell.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                    Point2D point2D = bookSell.localToScene(0.0,0.0);
                                    double x = stage.getX() + point2D.getX();
                                    double y = stage.getY() + point2D.getY() + 48;
                                    showContextMenu(handWrittenListView.getSelectionModel().getSelectedIndex() - 1, handWrittenBooks, x, y);
                                }
                            });

                        } else{
                            setText("");
                            setGraphic(null);
                        }
                    }
                };
                return cell;
            }
        });
        handWrittenListView.setItems(handWrittenBooks);
        textBooksListView.setItems(textBooks);
        practicalBooksListView.setItems(practicalBooks);
        myBooksListView.setItems(myBook);
        //myBooksListView.se
    }

    private void showContextMenu(int postion, ObservableList<Book> book, double x, double y) {
        Popup popup = new Popup();
        VBox content = new VBox();
        TextField input = new TextField();
        Button confirm  = new Button("CONFIRM");

        input.setStyle("-fx-font-size: 9px; -fx-font-name: System Bold");
        input.setAlignment(Pos.CENTER);
        input.setPrefHeight(13.0);
        input.setPromptText("Amount");
        input.setPrefWidth(80.0);

        confirm.setMnemonicParsing(false);
        confirm.setPrefHeight(8.0);
        confirm.setPrefWidth(80.0);
        confirm.setStyle("-fx-background-color: green;");

        confirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cart par = new Cart();
                par.setCartId(0);
                par.setBookName(book.get(postion).getBookName());
                par.setBookAuthor(book.get(postion).getBookAuthor());
                par.setBookIcon(book.get(postion).getBookIcon());
                par.setCategoryName(book.get(postion).getCategoryName());
                par.setUserName(userLogger);
                if (!inputValidation.isTextFieldEmpty(input)){
                    popup.hide();
                } else {
                    par.setSaleAmount(Integer.parseInt(input.getText()));
                    try {
                        dataAccessObject.insertCart(par);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    myBook.remove(postion);
                    try {
                        dataAccessObject.deleteBook(par.getCategoryName(),par.getBookName());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    input.setText("");
                }
                popup.hide();
            }
        });
        content.getChildren().addAll(input,confirm);
        popup.getContent().add(content);
        popup.setY(y);
        popup.setX(x);
        popup.setAutoHide(true);
        popup.show(stage);
    }
    public void loggedUser(User user){
        userLogged.setUserName(user.getUserName());
        userLogged.setUserIcon(user.getUserIcon());
    }

    public void libraryView(ActionEvent actionEvent) throws IOException {
    }

    public void storeView(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/StoreWindow.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    public void filterLibraryBooks(KeyEvent keyEvent) {
        filter(myBook,myBooksListView);
        filter(practicalBooks,practicalBooksListView);
        filter(textBooks,textBooksListView);
        filter(handWrittenBooks,handWrittenListView);
    }

    private void filter(ObservableList<Book> list, ListView<Book> listView){
        FilteredList<Book> filtereData= new FilteredList<>(list, e->true);
        bookFilterTextField.setOnKeyReleased(e->{

            bookFilterTextField.textProperty().addListener((observable,oldValue,newValue) -> {
                filtereData.setPredicate((Predicate<? super Book>) par->{

                    if(newValue==null||newValue.isEmpty()||newValue.isBlank()){
                        return true;
                    }
                    String toLowerCase = newValue.toLowerCase();


                    if(par.getBookAuthor().toLowerCase().contains(toLowerCase)){
                        return true;
                    } else return par.getBookName().toLowerCase().contains(toLowerCase);
                });
            });
            final SortedList<Book> sortedList =new SortedList<>(filtereData);
            //sortedList.comparatorProperty().bind(myBooksListView.comparatorProperty());
            listView.setItems(sortedList);
        });
    }

    public void logOut(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/login.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void addMyBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/RegisterMyBook.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void addPracticalManualBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/RegisterPracticalBook.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void addHandWrittenBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/RegisterHandwrittenBook.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void addTextBook(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mich/gwan/bookstore/RegisterTextBook.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}
