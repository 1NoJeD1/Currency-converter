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
import com.converter.currency_converter.models.History;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoryRepository implements JDBCRepository<Integer, History> {

    private static final String URL = "jdbc:postgresql://localhost:5432/converter_db";

    private static final String USERNAME = "converter_user";

    private static final String PASSWORD = "converter";

    private static final String driver = "org.postgresql.Driver";

    @Autowired
    private CurrencyRepository currencyRepository;

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

    public List<History> getBySessionId(String token) {

        List<History> historyList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT history.* FROM history INNER JOIN users_sessions on users_sessions.id=? WHERE history.user_id=users_sessions.user_id");
            preparedStatement.setString(1, token);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                History history = new History();
                Currency source = currencyRepository.getById(result.getString("source"));
                Currency target = currencyRepository.getById(result.getString("target"));
                history.setSource(source.getChar_code() + " (" + source.getName() + ")");
                history.setTarget(target.getChar_code() + " (" + target.getName() + ")");
                history.setSource_value(result.getFloat("source_value"));
                history.setTarget_value(result.getFloat("target_value"));
                history.setRate_date(result.getDate("rate_date"));

                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    public List<History> getBySessionIdAndDateAndSourceAndTarget(String token, Date date, String source,
            String target) {

        List<History> historyList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT history.* FROM history INNER JOIN users_sessions on users_sessions.id=? WHERE history.user_id=users_sessions.user_id AND history.source=? AND history.target=? AND history.rate_date=?");
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, source);
            preparedStatement.setString(3, target);
            preparedStatement.setDate(4, date);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                History history = new History();
                Currency source_c = currencyRepository.getById(result.getString("source"));
                Currency targe_c = currencyRepository.getById(result.getString("target"));
                history.setSource(source_c.getChar_code() + " (" + source_c.getName() + ")");
                history.setTarget(targe_c.getChar_code() + " (" + targe_c.getName() + ")");
                history.setSource_value(result.getFloat("source_value"));
                history.setTarget_value(result.getFloat("target_value"));
                history.setRate_date(result.getDate("rate_date"));

                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    @Override
    public List<History> getAll() {
        return null;
    }

    @Override
    public History getById(Integer id) {
        return null;
    }

    @Override
    public boolean save(History entity) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO history (user_id, source, target, source_value, target_value, rate_date) VALUES (?,?,?,?,?,?)");
            preparedStatement.setInt(1, entity.getUser_id());
            preparedStatement.setString(2, entity.getSource());
            preparedStatement.setString(3, entity.getTarget());
            preparedStatement.setFloat(4, entity.getSource_value());
            preparedStatement.setFloat(5, entity.getTarget_value());
            preparedStatement.setDate(6, entity.getRate_date());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean remove(History entity) {
        return false;
    }

}
