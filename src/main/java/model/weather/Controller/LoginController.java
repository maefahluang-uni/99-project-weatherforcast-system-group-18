package model.weather.Controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import model.weather.Security.User;
import model.weather.Security.UserDto;
import model.weather.Security.UserService; // Import UserService

@Controller
public class LoginController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService; // Inject UserService

    public LoginController(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(UserDto userDto, Model model) {
        // Perform validation on userDto (e.g., check if username is unique)

        // Create a new user entity
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save the user to the database using the UserService
        userService.save(newUser);

        // Redirect to the login page
        return "redirect:/login";
    }
}
