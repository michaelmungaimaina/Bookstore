/*
 * Copyright (c) 2023 Maina Michael. All rights reserved
 */

package com.mich.gwan.bookstore.models;

import javafx.scene.image.Image;

public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private Image userIcon;

    public User() {
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Image getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Image userIcon) {
        this.userIcon = userIcon;
    }

    public static User loggedUser;

    /**
     *
     * @return The User Using The System
     */
    public static User getLoggedUser() {
        return loggedUser;
    }

    /**
     *
     * @param loggedUser The User Using The System
     */
    public   void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
