package model.weather.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import model.weather.Model.Location;
import model.weather.Model.LocationRepository;
import java.util.List;

@Controller
public class BookmarkController {

    private final LocationRepository locationRepository;

    public BookmarkController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Handler method to display the main page with a list of bookmarks
    @GetMapping("/bookmarks")
    public String listBookmarks(Model model) {
        model.addAttribute("bookmarks", locationRepository.findAll());
        return "bookmarks"; // A view that lists all bookmarks
    }

    // Additional methods for bookmark management could go here
}

