package com.converter.currency_converter.models;

import java.sql.Date;

public class Rate {

    private String id;
    private Date date;
    private int nominal;
    private float rate;

    public Rate() {
    }

    public Rate(String id, Date date, int nominal, float rate) {
        this.id = id;
        this.date = date;
        this.nominal = nominal;
        this.rate = rate;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public float getRate() {
        return this.rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
