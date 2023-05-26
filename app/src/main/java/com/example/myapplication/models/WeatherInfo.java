package com.example.myapplication.models;

import com.google.gson.annotations.SerializedName;

public class WeatherInfo {
    @SerializedName("temp")
    private double temperature;

    public double getTemperature() {
        return temperature;
    }
}
