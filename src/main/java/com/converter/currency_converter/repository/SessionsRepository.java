package com.converter.currency_converter.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.converter.currency_converter.models.Session;

import org.springframework.stereotype.Component;

@Component
public class SessionsRepository implements JDBCRepository<String, Session> {

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

    public boolean existsSession(String id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT count(*) FROM users_sessions WHERE id=?");
            preparedStatement.setString(1, id);
            ResultSet checkCount = preparedStatement.executeQuery();
            checkCount.next();
            int count = checkCount.getInt(1);

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Session> getAll() {
        return null;
    }

    @Override
    public Session getById(String id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM users_sessions WHERE id=?");
            preparedStatement.setString(1, id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();

            Session session = new Session();
            session.setId(result.getString("id"));
            session.setExpires(result.getDate("expires"));
            session.setUser_id(result.getInt("user_id"));

            return session;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Session entity) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO users_sessions (id, user_id, expires) VALUES (?,?,?)");
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setInt(2, entity.getUser_id());
            preparedStatement.setDate(3, entity.getExpires());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Session entity) {
        return false;
    }

}
