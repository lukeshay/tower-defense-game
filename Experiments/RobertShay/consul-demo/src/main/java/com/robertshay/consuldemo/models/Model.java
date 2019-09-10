package com.robertshay.consuldemo.models;

public class Model {
    private String string;

    private Model() {}

    public Model(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
