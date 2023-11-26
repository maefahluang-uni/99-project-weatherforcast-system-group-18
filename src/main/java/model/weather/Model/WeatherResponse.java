package model.weather.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;



import java.util.ArrayList;
import java.util.List;

// Suppress any unknown properties in JSON to avoid exceptions
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private double lat;
    private double lon;
    private String timezone;
    @JsonProperty("timezone_offset")
    private int timezoneOffset;
    private CurrentWeather current;
    private List<HourlyWeather> hourly;
    private List<DailyWeather> daily;
    // Getters and Setters
    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public String getTimezone() {
        return timezone;
    }
    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
    public int getTimezoneOffset() {
        return timezoneOffset;
    }
    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
    public CurrentWeather getCurrent() {
        return current;
    }
    public void setCurrent(CurrentWeather current) {
        this.current = current;
    }
    public List<HourlyWeather> getHourly() {
        return hourly;
    }
    public void setHourly(List<HourlyWeather> hourly) {
        if (hourly.size() > 24) {
            this.hourly = new ArrayList<>(hourly.subList(0, 24));
        } else {
            this.hourly = hourly;
        }
    }
    public List<DailyWeather> getDaily() {
        return daily;
    }
    public void setDaily(List<DailyWeather> daily) {
        this.daily = daily;
    }

}


