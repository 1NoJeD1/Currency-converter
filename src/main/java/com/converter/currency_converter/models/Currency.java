package com.converter.currency_converter.models;

public class Currency {

    private String id;
    private String num_code;
    private String char_code;
    private String name;
    private Rate rate;

    public Currency() {
    }

    public Currency(String id,
            String num_code,
            String char_code,
            String name,
            Rate rate) {
        this.id = id;
        this.num_code = num_code;
        this.char_code = char_code;
        this.name = name;
        this.setRate(rate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChar_code() {
        return char_code;
    }

    public void setChar_code(String char_code) {
        this.char_code = char_code;
    }

    public String getNum_code() {
        return num_code;
    }

    public void setNum_code(String num_code) {
        this.num_code = num_code;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }
}
