package model.weather.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import model.weather.Model.Location;
import model.weather.Model.LocationRepository;
import model.weather.Security.UserService;
import model.weather.Service.LocationService;
import model.weather.Security.User; 

import java.util.List;

@Controller
public class BookmarkController {

    private final LocationRepository locationRepository;
    private final UserService userService;
    private final LocationService locationService; // Add this line to inject LocationService

    @Autowired
    public BookmarkController(LocationRepository locationRepository, UserService userService, LocationService locationService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
        this.locationService = locationService; // Initialize LocationService
    }

    @GetMapping("/bookmarks")
    public String listBookmarks(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = (User) userService.loadUserByUsername(username);
    
        // Logging to check the user and its bookmarks
        System.out.println("Authenticated User: " + user.getUsername());
    
        // Retrieve bookmarks for the user
        List<Location> bookmarks = locationRepository.findByUser_Id(user.getId()); // Use modified repository method
    
        // Logging to check the retrieved bookmarks
        System.out.println("Bookmarks: " + bookmarks);
    
        model.addAttribute("bookmarks", bookmarks); // Add the bookmarks to the model
    
        return "bookmarks";
    }
    
    

    // Additional methods for bookmark management could go here
}
