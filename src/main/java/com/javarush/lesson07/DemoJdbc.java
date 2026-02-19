package com.javarush.lesson07;

import java.sql.*;
import java.util.StringJoiner;

public class DemoJdbc {

    public static final String ONE_CELL_FORMAT = "  %-13s";
    public static final String SQL_READ_ALL = "SELECT * FROM users ORDER BY id DESC";
    public static final String SQL_INSERT_USERS = """
            INSERT INTO users (id, login, password, role)
            VALUES (DEFAULT, 'Carl2', 'admin', 'ADMIN'),
                   (DEFAULT, 'Alisa2', 'qwerty', 'USER'),
                   (DEFAULT, 'Bob2', '123', 'GUEST');
            """;
    public static final String SQL_REMOVE_USERS = "DELETE FROM users WHERE id>3";

    public static void main(String[] args) throws SQLException {
        try (
                Connection connection = Cnn.get();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(SQL_READ_ALL + " ");
        ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            String lineFormat = ONE_CELL_FORMAT.repeat(columnCount) + "%n";
            String line = "=".repeat(15 * columnCount);
            System.out.println(line);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                System.out.printf(ONE_CELL_FORMAT, columnName);
            }
            System.out.println("\n" + line);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                System.out.printf(lineFormat, id, login, password, role);
            }
            System.out.println(line);

            int rowCount = statement.executeUpdate(SQL_INSERT_USERS, Statement.RETURN_GENERATED_KEYS);
            StringJoiner ids = new StringJoiner(", ", "[", "]");
            ResultSet generatedKeys = statement.getGeneratedKeys();
            while (generatedKeys.next()) {
                String newId = generatedKeys.getString(1);
                ids.add(newId);
            }
            System.out.println("Row added. Count=" + rowCount);
            System.out.println("Generated ids: " + ids);

            int deleteCount = statement.executeUpdate(SQL_REMOVE_USERS);
            System.out.println("Deleted " + deleteCount + " rows");

        }
    }
}
