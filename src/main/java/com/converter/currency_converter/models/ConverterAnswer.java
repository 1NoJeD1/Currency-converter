package com.converter.currency_converter.models;

public class ConverterAnswer {
    private Float source = 0.0f;
    private Float target = 0.0f;

    public ConverterAnswer() {

    }

    public ConverterAnswer(Float source, Float target) {
        this.source = source;
        this.target = target;
    }

    public float getSource() {
        return source;
    }

    public void setSource(Float source) {
        this.source = source;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }
}
