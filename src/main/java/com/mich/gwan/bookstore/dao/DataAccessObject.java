package com.mich.gwan.bookstore.dao;

import com.mich.gwan.bookstore.models.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.*;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataAccessObject{
    Connection connection = DatabaseConnection.ConnectDatabase();

    Statement statement;
    ResultSet resultSet = null;


    private void updateQuery(String query) {
        try {
            statement.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertQuery(String query) {
        try {
            statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchQuery(String query) {
        try {
            resultSet =
            statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSession(User par) throws SQLException {
        String insert = "insert into session (user_id, user_name, user_profile) values (?,?,?) ";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(insert);
            FileInputStream fileInputStream = new FileInputStream("d:\\icon.png");
            stm.setInt(1,0);
            stm.setString(2, par.getUserName());
            stm.setBinaryStream(3, fileInputStream, fileInputStream.available());
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }
    public void insertUser(User par,File iconFile) throws SQLException {
        String insert = "insert into user (user_id, user_name, user_password, user_profile) values (?,?,?,?) ";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(insert);
            FileInputStream  fis = new FileInputStream(iconFile);
            stm.setInt(1,0);
            stm.setString(2, par.getUserName());
            stm.setString(3, par.getUserPassword());
            stm.setBinaryStream(4, (InputStream) fis, (int) iconFile.length());
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }
    public void insertCart(Cart par) throws SQLException {
        String insert = "insert into sale (sale_id, book_name, book_category, book_author, book_amount, book_icon, user_name) values (?,?,?,?,?,?,?) ";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(insert);
            stm.setInt(1,0);
            stm.setString(2, par.getBookName());
            stm.setString(3, par.getCategoryName());
            stm.setString(4, par.getBookAuthor());
            stm.setInt(5, par.getSaleAmount());
            stm.setString(7, par.getUserName());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) par.getBookIcon(),"png",byteArrayOutputStream);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stm.setBinaryStream(6, inputStream, byteArrayOutputStream.toByteArray().length);
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    public ObservableList<User> getUser(int userId) {
        String query = "select * from user where user_id = ?";
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                Blob blob = rs.getBlob("user_profile");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image icon = SwingFXUtils.toFXImage(bufferedImage,null);
                user.setUserIcon(icon);
                userList.add(user);
            }
            connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public Image getUser(String userName, String password) {
        String query = "select * from user where user_name = ? and user_password = ?";
        Image icon = null;
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                Blob blob = rs.getBlob("user_profile");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                icon = SwingFXUtils.toFXImage(bufferedImage,null);
                user.setUserIcon(icon);
            }
            //connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return icon;
    }
    public ObservableList<User> getSession() {
        String query = "select * from session";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserName(rs.getString("user_name"));
                Blob blob = rs.getBlob("user_profile");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image icon = SwingFXUtils.toFXImage(bufferedImage,null);
                user.setUserIcon(icon);
                list.add(user);
            }
            //connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public Image getSessionImage() {
        String query = "select * from session";
        Image image = null;
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blob blob = rs.getBlob("user_profile");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                image = SwingFXUtils.toFXImage(bufferedImage,null);
            }
            //connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
    public String getSessionName() {
        String query = "select * from session";
        String name = null;
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                name = rs.getString("user_name");
            }
        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        }
        return name;
    }

    /**
     * Fetch all my books
     * @return
     */
    public ObservableList<Book> getMyBooks(String category) {
        String query = "select * from book where book_category = ?";
        ObservableList<Book> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book par = new Book();
                par.setBookId(rs.getInt("book_id"));
                par.setBookName(rs.getString("book_title"));
                par.setBookAuthor(rs.getString("book_author"));
                Blob blob = rs.getBlob("book_icon");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image icon = SwingFXUtils.toFXImage(bufferedImage,null);
                par.setBookIcon(icon);
                list.add(par);
            }
            connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    /**
     * Fetch all Cart
     * @return
     */
    public ObservableList<Cart> getAllCart(String userName, String category) {
        String query = "select * from sale where book_category = ? and user_name = ?";
        ObservableList<Cart> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cart par = new Cart();
                par.setCartId(resultSet.getInt("sale_id"));
                par.setCategoryName(rs.getString("book_category"));
                par.setBookName(rs.getString("book_name"));
                par.setBookAuthor(rs.getString("book_author"));
                par.setSaleAmount(rs.getInt("book_amount"));
                par.setUserName(rs.getString("user_name"));
                Blob blob = rs.getBlob("book_icon");
                InputStream is = blob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(is);
                Image icon = SwingFXUtils.toFXImage(bufferedImage,null);
                par.setBookIcon(icon);
                list.add(par);
            }
            connection.close();

        } catch (SQLException ex){
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE,null,ex);
            ex.getCause();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void updateUser(User par){
        String query="update user set user_name = ?, user_password = ?, user_profile = ? where user_id= ?";
        try{
            PreparedStatement stm=DatabaseConnection.ConnectDatabase().prepareStatement(query);
            stm.setString(1, par.getUserName());
            stm.setString(2, par.getUserPassword());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) par.getUserIcon(),"png",byteArrayOutputStream);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stm.setBinaryStream(3,(InputStream) inputStream,(int)byteArrayOutputStream.toByteArray().length);
            stm.setInt(4,par.getUserId());
            int result=stm.executeUpdate();
        }catch (Exception e){
            e.getCause();
        }
    }

    public void updateCart(Cart par){
        String query="update sale set sale_id = ?, book_name = ?, book_category = ?, book_author = ?, book_amount = ?, book_icon = ?, user_name = ? where book_category = ? and book_name = ?";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(query);
            stm.setInt(1,par.getCartId());
            stm.setString(2, par.getBookName());
            stm.setString(9, par.getBookName());
            stm.setString(3, par.getCategoryName());
            stm.setString(8, par.getCategoryName());
            stm.setString(4, par.getBookAuthor());
            stm.setInt(5, par.getSaleAmount());
            stm.setString(7, par.getUserName());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) par.getBookIcon(),"png",byteArrayOutputStream);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stm.setBinaryStream(6, inputStream, byteArrayOutputStream.toByteArray().length);
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    public boolean checkUser(User user) throws SQLException {
        String query = "select count(1) from user where user_name = '"+user.getUserName()+"' and user_password = '"+user.getUserPassword()+"'";
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }
        statement.close();
        return true;
    }

    public boolean checkUser(String userName) throws SQLException {
        String query = "select count(1) from user where user_name = '"+userName+"'";
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }
        statement.close();
        return true;
    }
    public boolean checkCart(String userName, String category, String bookName) throws SQLException {
        String query = "select count(1) from sale where user_name = '"+userName+"' and book_category = '" +category+"' and book_name = '"+bookName+"'";
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }
        statement.close();
        return true;
    }
    public ObservableList<User> getAllUsers(){
        ObservableList<User> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM user";
        try{
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User par = new User();
                par.setUserId(resultSet.getInt("user_id"));
                par.setUserName(resultSet.getString("user_name"));
                par.setUserPassword(resultSet.getString("user_password"));
                list.add(par);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public void deleteUser(int userId) throws SQLException {
        String query = "delete from user where user_id = '"+userId+"'";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statement.executeUpdate(query);
        statement.close();
    }

    public void insertBook(Book par) throws SQLException {
        String insert = "insert into book (book_id, book_category, book_title, book_author, book_icon) values (?,?,?,?,?) ";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(insert);
            stm.setInt(1,0);
            stm.setString(2, par.getCategoryName());
            stm.setString(3, par.getBookName());
            stm.setString(4, par.getBookAuthor());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write((RenderedImage) par.getBookIcon(),"png",byteArrayOutputStream);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            stm.setBinaryStream(5, inputStream, byteArrayOutputStream.toByteArray().length);
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }
    public void insertBook(Book par, File iconFile) throws SQLException {
        String insert = "insert into book (book_id, book_category, book_title, book_author, book_icon) values (?,?,?,?,?) ";
        try {
            PreparedStatement stm = DatabaseConnection.ConnectDatabase().prepareStatement(insert);
            FileInputStream  fis = new FileInputStream(iconFile);
            stm.setInt(1,0);
            stm.setString(2, par.getCategoryName());
            stm.setString(3, par.getBookName());
            stm.setString(4, par.getBookAuthor());
            stm.setBinaryStream(5, (InputStream) fis, (int) iconFile.length());
            stm.executeUpdate();
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    public boolean checkBook(Book par) throws SQLException {
        String query = "select count(1) from book where book_title = '"+par.getBookName()+"' and book_category = '"+par.getCategoryName()+"'";
        try {
            assert connection != null;
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            return resultSet.getInt(1) == 1;
        }
        statement.close();
        return true;
    }


    public void deleteBook(String categoryName, String bookName) throws SQLException {
        String query = "delete from book where book_name = '"+bookName+"' and book_category = '"+categoryName+"'";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statement.executeUpdate(query);
        statement.close();
    }
    public void deleteSession() throws SQLException {
        String query = "delete from session";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statement.executeUpdate(query);
        statement.close();
    }
    public void deleteCart(String categoryName, String bookName) throws SQLException {
        String query = "delete from cart where book_name = '"+bookName+"' and book_category = '"+categoryName+"'";
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        statement.executeUpdate(query);
        statement.close();
    }
}
