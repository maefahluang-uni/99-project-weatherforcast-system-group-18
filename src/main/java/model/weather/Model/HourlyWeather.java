package model.weather.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HourlyWeather {

    @JsonAlias("dt")
    private long dateTime;

    private int temp;

    @JsonAlias("feels_like")
    private int feelsLike;

    private int pressure;
    private int humidity;

    @JsonAlias("dew_point")
    private int dewPoint;

    private int uvi;
    private int clouds;
    private int visibility;

    @JsonAlias("wind_speed")
    private int windSpeed;

    @JsonAlias("wind_deg")
    private int windDegree;

    @JsonAlias("wind_gust")
    private int windGust;

    private List<WeatherCondition> weather;

    @JsonAlias("pop")
    private int probabilityOfPrecipitation;

    private Rain rain;

    // Standard getters and setters

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherCondition {
        private int id;
        private String main;
        private String description;
        private String icon;
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getMain() {
            return main;
        }
        public void setMain(String main) {
            this.main = main;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }

        // Getters and setters
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("hh a", Locale.ENGLISH);
        try {
            return formatter.format(new Date(this.dateTime * 1000));
        } catch (Exception e) {
            // Log the exception
            return "Time Error";
        }
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(int dewPoint) {
        this.dewPoint = dewPoint;
    }

    public int getUvi() {
        return uvi;
    }

    public void setUvi(int uvi) {
        this.uvi = uvi;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(int windDegree) {
        this.windDegree = windDegree;
    }

    public int getWindGust() {
        return windGust;
    }

    public void setWindGust(int windGust) {
        this.windGust = windGust;
    }

    public List<WeatherCondition> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherCondition> weather) {
        this.weather = weather;
    }

    public int getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public void setProbabilityOfPrecipitation(int probabilityOfPrecipitation) {
        this.probabilityOfPrecipitation = probabilityOfPrecipitation;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {

        @JsonAlias("1h")
        private int rain1h;

        public int getRain1h() {
            return rain1h;
        }

        public void setRain1h(int rain1h) {
            this.rain1h = rain1h;
        }
        
        // Getters and setters
    }

    // Getters and setters
}

