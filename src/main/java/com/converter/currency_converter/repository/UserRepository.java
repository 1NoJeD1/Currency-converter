package com.converter.currency_converter.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.converter.currency_converter.models.User;

import org.springframework.stereotype.Component;

@Component
public class UserRepository implements JDBCRepository<Integer, User> {

    private static final String URL = "jdbc:postgresql://localhost:5432/converter_db";

    private static final String USERNAME = "converter_user";

    private static final String PASSWORD = "converter";

    private static final String driver = "org.postgresql.Driver";

    private static Connection connection;
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getByUsernameAndByPassword(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users WHERE login=? AND password=?");

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public boolean save(User entity) {
        return false;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

}
