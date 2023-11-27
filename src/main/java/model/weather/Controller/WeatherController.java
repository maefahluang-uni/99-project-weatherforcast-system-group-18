package model.weather.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import model.weather.Model.GetCurrentLocation;
import model.weather.Model.Location;
import model.weather.Model.LocationRepository;
import model.weather.Model.WeatherResponse;
import model.weather.Security.User;
import model.weather.Security.UserRepository;
import model.weather.Service.LocationService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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

    @Autowired
    private UserRepository userRepository;


    @Value("${api.key}")
    private String apiKey;

    @Autowired
    public WeatherController(RestTemplate restTemplate, LocationRepository locationRepository) {
        this.restTemplate = restTemplate;
        this.locationRepository = locationRepository;
    }

    @GetMapping("/")
    public String getWeather(
        @RequestParam(value = "lat", required = false) Double latitude,
        @RequestParam(value = "lon", required = false) Double longitude,
        Model model) {

        // If the latitude and longitude parameters are not provided, set default values
        double lat = (latitude != null) ? latitude : 13.7563; // Default latitude for Bangkok
        double lon = (longitude != null) ? longitude : 100.5018; // Default longitude for Bangkok

        // Construct the API URL with the provided or default coordinates
        String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        String url_locate = "https://geocode.maps.co/reverse?lat="+lat+"&lon="+lon;
        // Use RestTemplate to fetch the weather data
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
        GetCurrentLocation getCurrentLocation = restTemplate.getForObject(url_locate, GetCurrentLocation.class);

        if (response != null) {
            // Extract the city name from the timezone field
            String[] timezoneParts = response.getTimezone().split("/");
            String city = timezoneParts.length > 1 ? timezoneParts[1] : "Unknown City";
            model.addAttribute("city", city); // Add the extracted city name to the model
        }

        // Add the complete weather data to the model
        model.addAttribute("weather", response);
        model.addAttribute("location", getCurrentLocation);


        // Return the name of the view template, which is 'weather' in this case
        return "weather";
    }




    @GetMapping("/location/{id}")
    public String getLocationWeather(@PathVariable Long id, Model model) {
        // Fetch the location by ID
        Optional<Location> optionalLocation = locationRepository.findById(id);

        if (optionalLocation.isPresent()) {
            Location location = optionalLocation.get();

            // Fetch weather data for the location
            String url = "https://api.openweathermap.org/data/3.0/onecall?lat=" + location.getLatitude() +
                    "&lon=" + location.getLongitude() +
                    "&appid=" + apiKey +
                    "&units=metric";

            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

            // Add the weather response and the city name to the model
            model.addAttribute("weather", response);
            model.addAttribute("city", location.getName());

            // Fetch all bookmarks to display as a list, so the user can select another location
            model.addAttribute("bookmarks", locationRepository.findAll());

            return "weather"; // View for displaying weather
        } else {
            // Handle the case where the location is not found
            model.addAttribute("error", "Location not found.");
            return "error"; // You might want to create an "error.html" template to display the error message
        }
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

    @GetMapping("/locationForm")
    public String showLocationForm(Model model,@AuthenticationPrincipal User currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle unauthenticated case as needed
            return null;
        }
        Long UserID = currentUser.getId();
        System.out.println(UserID);
        model.addAttribute("usercurrent", UserID);

        Location location = locationService.getLocationById(1L);
        if (location == null) {
            location = new Location();
        }
        model.addAttribute("location", location);
        return "locationForm";
    }

    
    @PostMapping("/saveLocation")
    @Transactional
    public String saveLocation(@RequestParam double latitude, @RequestParam double longitude, @RequestParam String name, @AuthenticationPrincipal User currentUser, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                // Handle unauthenticated case as needed
                return null;
            }
            Long UserID = currentUser.getId();
            System.out.println(UserID);
            model.addAttribute("usercurrent", UserID);
        // Check if the location already exists for the user
        List<Location> existingLocations = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
        // Create a new location
        if (existingLocations.isEmpty()) {
            // Save the location details to the database
            Location location = new Location(latitude, longitude, name, UserID);
            locationRepository.save(location);
        } else {
            // Handle the case where the location already exists (e.g., show an error message)
            model.addAttribute("error", "Location with the provided latitude and longitude already exists.");
            return "error"; // You might want to create an "error.html" template to display the error message
        }
    
        // Redirect to the user's profile or another appropriate page
        return "redirect:/bookmarks";
    }
    



    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        // ... (existing code)

        return "error"; // Redirect to the "error.html" template
    }
    

    
}
