package com.converter.currency_converter.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.converter.currency_converter.models.Rate;

import org.springframework.stereotype.Component;

@Component
public class RateRepository implements JDBCRepository<String, Rate> {

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

    public boolean existsByDate(Date date) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT count(*) FROM rate WHERE date=?");
            preparedStatement.setDate(1, date);
            ResultSet checkOnDate = preparedStatement.executeQuery();

            checkOnDate.next();
            int count = checkOnDate.getInt(1);

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countRow(Rate rate) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT count(*) FROM rate WHERE id=? AND date=?");
            preparedStatement.setString(1, rate.getId());
            preparedStatement.setDate(2, rate.getDate());
            ResultSet checkOnId = preparedStatement.executeQuery();
            checkOnId.next();
            int count = checkOnId.getInt(1);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Rate> getAll() {
        return null;
    }

    public Rate getByIdAndDate(String id, Date date) {
        try {
            int count = countRow(new Rate(id, date, 0, 0f));
            if (count == 0)
                return null;

            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rate WHERE id=? AND date=?");
            preparedStatement.setString(1, id);
            preparedStatement.setDate(2, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Rate rate = new Rate();
                rate.setId(resultSet.getString("id"));
                rate.setDate(resultSet.getDate("date"));
                rate.setNominal(resultSet.getInt("nominal"));
                rate.setRate(resultSet.getFloat("rate"));
                return rate;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Rate getById(String id) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM rate WHERE id=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Rate rate = new Rate();
                rate.setId(resultSet.getString("id"));
                rate.setDate(resultSet.getDate("date"));
                rate.setNominal(resultSet.getInt("nominal"));
                rate.setRate(resultSet.getFloat("rate"));
                return rate;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Rate entity) {
        try {
            int count = countRow(entity);

            PreparedStatement preparedStatement;
            if (count == 0) {
                preparedStatement = connection.prepareStatement("INSERT INTO rate VALUES (?,?,?,?)");

                preparedStatement.setString(1, entity.getId());
                preparedStatement.setDate(2, entity.getDate());
                preparedStatement.setInt(3, entity.getNominal());
                preparedStatement.setFloat(4, entity.getRate());
            } else {
                preparedStatement = connection.prepareStatement("UPDATE rate SET date=?, nominal=?, rate=? WHERE id=?");

                preparedStatement.setDate(1, entity.getDate());
                preparedStatement.setInt(2, entity.getNominal());
                preparedStatement.setFloat(3, entity.getRate());
                preparedStatement.setString(4, entity.getId());
            }
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Rate entity) {
        return false;
    }

}
