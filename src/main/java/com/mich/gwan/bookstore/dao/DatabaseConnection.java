package com.mich.gwan.bookstore.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {


        public static Connection ConnectDatabase() {

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Driver loaded");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bookstore", "Admin",
                        "P@$$W0rd");// m!ch43lMung4! P@$$W0rd
                System.out.println("Database connected");
                return connection;
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            return null;
        }

    }