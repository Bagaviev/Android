package com.example.secondexample;

import android.widget.ImageView;

public class State {
    private String model;
    private int flagResourse;

    public State(String model, int flagResourse) {
        this.model = model;
        this.flagResourse = flagResourse;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getFlagResourse() {
        return flagResourse;
    }

    public void setFlagResourse(int flagResourse) {
        this.flagResourse = flagResourse;
    }
}
