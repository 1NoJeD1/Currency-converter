package com.converter.currency_converter.models;

import java.sql.Date;

public class History {
    private int id;
    private int user_id;
    private String source;
    private String target;
    private Float source_value;
    private Float target_value;
    private Date rate_date;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getRate_date() {
        return rate_date;
    }

    public void setRate_date(Date rate_date) {
        this.rate_date = rate_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Float getTarget_value() {
        return target_value;
    }

    public void setTarget_value(Float target_value) {
        this.target_value = target_value;
    }

    public Float getSource_value() {
        return source_value;
    }

    public void setSource_value(Float source_value) {
        this.source_value = source_value;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
