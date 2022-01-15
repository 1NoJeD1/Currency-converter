package com.converter.currency_converter.components;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

import com.converter.currency_converter.models.Session;
import com.converter.currency_converter.models.User;
import com.converter.currency_converter.repository.SessionsRepository;

import org.springframework.stereotype.Component;

@Component
public class Security {

    SessionsRepository repo = new SessionsRepository();
    private int daysBeforeEnd = 7;

    public boolean isAuth(String token) {
        return repo.existsSession(token);
    }

    public Session getSessionData(String token) {
        return repo.getById(token);
    }

    public Session addSession(User user) {
        Session session = new Session();

        Date now = new Date(new java.util.Date().getTime());

        session.setId(UUID.randomUUID().toString());
        session.setUser_id(user.getId());
        session.setExpires(subtractDays(now, daysBeforeEnd));

        repo.save(session);

        return session;
    }

    public static Date subtractDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
}
