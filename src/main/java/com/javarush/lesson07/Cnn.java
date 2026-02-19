package com.javarush.lesson07;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Cnn {

    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(
                Configuration.getString(DB_URL),
                Configuration.getString(DB_USERNAME),
                Configuration.getString(DB_PASSWORD)
        );
    }
}
