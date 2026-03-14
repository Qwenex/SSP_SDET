package org.example.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectMySqlDB {

    private static Connection getConnection() {
        ReadProperty properties = new ReadProperty("authMySqlDB");
            String url = properties.get("db.url");
            String user = properties.get("db.user");
            String password = properties.get("db.password");
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public static <T> List<T> getData(String sql, ResultSetMapper<T> mapper) {
        List<T> results = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                results.add(mapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения запроса: " + sql, e);
        }
        return results;
    }

    public static Integer setData(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения обновления: " + sql, e);
        }
    }
}
