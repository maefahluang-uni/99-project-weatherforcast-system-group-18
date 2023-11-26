package model.weather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeelsLike {

    private int day;
    private int night;
    private int eve;
    private int morn;
    
    public double getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getNight() {
        return night;
    }
    public void setNight(int night) {
        this.night = night;
    }
    public double getEve() {
        return eve;
    }
    public void setEve(int eve) {
        this.eve = eve;
    }
    public double getMorn() {
        return morn;
    }
    public void setMorn(int morn) {
        this.morn = morn;
    }

    // Standard getters and setters...
}

