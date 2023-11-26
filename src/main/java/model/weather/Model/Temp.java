package model.weather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Temp {

    private int day;
    private int min;
    private int max;
    private int night;
    private int eve;
    private int morn;
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getMin() {
        return min;
    }
    public void setMin(int min) {
        this.min = min;
    }
    public int getMax() {
        return max;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getNight() {
        return night;
    }
    public void setNight(int night) {
        this.night = night;
    }
    public int getEve() {
        return eve;
    }
    public void setEve(int eve) {
        this.eve = eve;
    }
    public int getMorn() {
        return morn;
    }
    public void setMorn(int morn) {
        this.morn = morn;
    }
}
