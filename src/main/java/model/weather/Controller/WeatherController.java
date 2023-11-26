package model.weather.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import model.weather.Model.Location;
import model.weather.Model.LocationRepository;
import model.weather.Model.WeatherResponse;
import model.weather.Service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WeatherController {

    private String getname;
    private double getLat;
    private double getLon;
    
    private final RestTemplate restTemplate;
    private final LocationRepository locationRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @Autowired
    private LocationService locationService;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public WeatherController(RestTemplate restTemplate, LocationRepository locationRepository) {
        this.restTemplate = restTemplate;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/")
    public String getWeather(
        @RequestParam(value = "bookmarkId", required = false) Long bookmarkId,
        Model model) {
        
        double lat;
        double lon;
        String city;

        // If a bookmark ID is provided, fetch that location
        if (bookmarkId != null) {
            Location location = locationRepository.findById(bookmarkId).orElse(null);

            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                city = location.getName(); // Assuming Location has a 'name' field
            } else {
                // Fallback to default location if the bookmarked location is not found
                lat = 13.7563; // Latitude for Bangkok
                lon = 100.5018; // Longitude for Bangkok
                city = "Bangkok"; // Default city name
            }
        } else {
            // Default to Bangkok if no bookmark ID is provided
            lat = 13.7563; // Latitude for Bangkok
            lon = 100.5018; // Longitude for Bangkok
            city = "Bangkok"; // Default city name
        }

        // Construct the API URL with the provided or default coordinates
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        // Add the weather response and the city name to the model
        model.addAttribute("weather", response);
        model.addAttribute("city", city);

        // Fetch all bookmarks to display as a list, so user can select another location
        model.addAttribute("bookmarks", locationRepository.findAll());

        return "weather"; // View for displaying weather
    }   

    @GetMapping("/search")
    public String getSearchData(
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "placeName", required = false) String placeName,
            Model model) {

        if (latitude != null && longitude != null) {
            try {
                String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey + "&units=metric";
                WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
                List<Location> locations = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
                model.addAttribute("locations", locations);

                model.addAttribute("weather", response);
                model.addAttribute("city", placeName);

                // Fetch all bookmarks to display as a list, so the user can select another location
                model.addAttribute("bookmarks", locationRepository.findAll());

                return "weather"; // View for displaying weather
            } catch (Exception e) {
                // Log the exception
                logger.error("Error fetching weather data", e);

                // Handle API call errors, log the exception, and display an error message to the user
                model.addAttribute("error", "Error fetching weather data. Please try again later.");
                return "error"; // Create an "error.html" template to display the error message
            }
        }

        return "search";
    }


    @PostMapping("/addLocation")
    public String addLocation(
            @RequestParam(value = "latitude", required = true) Double latitude,
            @RequestParam(value = "longitude", required = true) Double longitude,
            @RequestParam(value = "placeName", required = true) String placeName,
            Model model) {

        // Check if the location with the given latitude and longitude already exists
        List<Location> existingLocations = locationRepository.findByLatitudeAndLongitude(latitude, longitude);

        if (existingLocations.isEmpty()) {
            // Save the location details to the database
            Location location = new Location();
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location.setName(placeName);
            locationRepository.save(location);
        } else {
            // Handle the case where the location already exists (e.g., show an error message)
            model.addAttribute("error", "Location with the provided latitude and longitude already exists.");
            return "error"; // You might want to create an "error.html" template to display the error message
        }

        // Redirect to the search page or any other appropriate page
        return "redirect:/search";
    }

    @GetMapping("/locationForm")
    public String showLocationForm(Model model) {
        Location location = locationService.getLocationById(1L);
        if (location == null) {
            location = new Location();
        }
        model.addAttribute("location", location);
        return "locationForm";
    }


    

    @PostMapping("/saveLocation")
    public String saveLocation(@ModelAttribute Location location) {
        locationRepository.save(location);
        return "redirect:/bookmarks";
        }

    
    
    
    

    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        // ... (existing code)

        return "error"; // Redirect to the "error.html" template
    }
    

    
}
