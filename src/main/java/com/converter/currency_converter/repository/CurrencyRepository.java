package com.converter.currency_converter.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.converter.currency_converter.models.Currency;
import com.converter.currency_converter.models.Rate;

import org.springframework.stereotype.Component;

@Component
public class CurrencyRepository implements JDBCRepository<String, Currency> {

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

    @Override
    public List<Currency> getAll() {
        List<Currency> result = new ArrayList<>();

        try {
            Date date = new Date(new java.util.Date().getTime());

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT currency.*, rate.date, rate.nominal, rate.rate FROM currency INNER JOIN rate ON currency.id=rate.id WHERE rate.date=?");
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                String id = resultSet.getString("id");
                currency.setId(id);
                currency.setNum_code(resultSet.getString("num_code"));
                currency.setChar_code(resultSet.getString("char_code"));
                currency.setName(resultSet.getString("name"));

                Rate rate = new Rate();
                rate.setId(id);
                rate.setDate(resultSet.getDate("date"));
                rate.setNominal(resultSet.getInt("nominal"));
                rate.setRate(resultSet.getFloat("rate"));

                currency.setRate(rate);

                result.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Currency getById(String id) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT currency.*, rate.date, rate.nominal, rate.rate FROM currency INNER JOIN rate ON currency.id=rate.id WHERE currency.id=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(id);
                currency.setNum_code(resultSet.getString("num_code"));
                currency.setChar_code(resultSet.getString("char_code"));
                currency.setName(resultSet.getString("name"));

                Rate rate = new Rate();
                rate.setId(id);
                rate.setDate(resultSet.getDate("date"));
                rate.setNominal(resultSet.getInt("nominal"));
                rate.setRate(resultSet.getFloat("rate"));

                currency.setRate(rate);

                return currency;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Currency entity) {
        try {
            PreparedStatement preparedStatementCheckCount = connection.prepareStatement(
                    "SELECT count(*) FROM currency WHERE id=?");
            preparedStatementCheckCount.setString(1, entity.getId());
            ResultSet checkCount = preparedStatementCheckCount.executeQuery();
            checkCount.next();
            int count = checkCount.getInt(1);

            PreparedStatement preparedStatement;
            if (count == 0) {
                preparedStatement = connection.prepareStatement("INSERT INTO currency VALUES (?,?,?,?)");

                preparedStatement.setString(1, entity.getId());
                preparedStatement.setString(2, entity.getNum_code());
                preparedStatement.setString(3, entity.getChar_code());
                preparedStatement.setString(4, entity.getName());
            } else {
                preparedStatement = connection
                        .prepareStatement("UPDATE currency SET num_code=?, char_code=?, name=? WHERE id=?");

                preparedStatement.setString(1, entity.getNum_code());
                preparedStatement.setString(2, entity.getChar_code());
                preparedStatement.setString(3, entity.getName());
                preparedStatement.setString(4, entity.getId());
            }
            preparedStatement.executeUpdate();

            RateRepository rateRepo = new RateRepository();
            rateRepo.save(entity.getRate());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(Currency entity) {
        return false;
    }

}
