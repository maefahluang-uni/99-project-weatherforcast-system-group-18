package model.weather.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.tomcat.jni.Address;

public class GetCurrentLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Or another appropriate strategy
    private Long id;

    private String lat;
    private String lon;
    private String display_name;

    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLon() {
        return lon;
    }
    public void setLon(String lon) {
        this.lon = lon;
    }
    public String getDisplay_name() {
        return display_name;
    }
    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
        // Split the display name by comma
        String[] parts = display_name.split(",");
        // Check if there is at least one element after the split
        if (parts.length > 0) {
            // Set the first element as the city name
            this.city = parts[1].trim(); // trim() removes any leading/trailing whitespace
        }
    }
    
    // Remember to declare the 'city' field in your class
    private String city;
    
    // And create a getter for the city
    public String getCity() {
        return city;
    }
    
}
