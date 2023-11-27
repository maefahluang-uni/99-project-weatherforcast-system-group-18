package model.weather.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private LocationService locationService;

    @Autowired
    public BookmarkController(LocationRepository locationRepository, UserService userService) {
        this.locationRepository = locationRepository;
        this.userService = userService;
    }

    @GetMapping("/bookmarks")
    public String listBookmarks(Model model, @AuthenticationPrincipal User currentUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            // Handle unauthenticated case as needed
            return null;
        }
        Long UserID = currentUser.getId();
        System.out.println(UserID);
        model.addAttribute("usercurrent", UserID);
        List<Location> userLocations = locationService.getLocationsByUserid(UserID);
        model.addAttribute("userLocations", userLocations);

        return "bookmarks";
    }
    
    

    // Additional methods for bookmark management could go here
}
