package com.javarush.lesson08;

import com.javarush.lesson07.Cnn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DemoPreparedStatement {

    public static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id=?";

    public static void main(String[] args) throws SQLException {
        for (long id = 1; id <= 4; id++) {
            printById(id);
        }

    }

    private static void printById(long queryId) throws SQLException {
        Connection connection = CnnPool.getConnection();
        try (connection;
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            preparedStatement.setLong(1, queryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                System.out.println(id + " " + login + " " + password + " " + role);
            }
        }
    }
}
