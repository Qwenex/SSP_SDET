package org.example.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectSqlDB {

    private final String url;
    private final String user;
    private final String password;

    public ConnectSqlDB(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения к БД", e);
        }
    }

    public <T> List<T> getData(String sql, ResultSetMapper<T> mapper) {
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

    public Integer setData(String sql) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка выполнения обновления: " + sql, e);
        }
    }
}
