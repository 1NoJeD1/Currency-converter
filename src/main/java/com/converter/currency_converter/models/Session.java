package com.converter.currency_converter.models;

import java.sql.Date;

public class Session {
    private String id;
    private int user_id;
    private Date expires;

    public String getId() {
        return id;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
