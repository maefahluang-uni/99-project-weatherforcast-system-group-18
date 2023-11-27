package model.weather.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String processSignup(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult result, Model model) {
        // Perform validation on userDto
        if (result.hasErrors()) {
            return "signup";  // Return to the signup page with error messages
        }

        // Check if the username is unique (you might need a service method for this)

        // Create a new user entity
        User newUser = new User();
        newUser.setFirstName(userDto.getFirstName());
        newUser.setLastName(userDto.getLastName());
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Save the user to the database using the UserService
        userService.save(newUser);

        // Redirect to the login page
        return "redirect:/login";
    }
    @PostMapping("/login")
    public String loginredirect() {
        // Logic for handling the login form submission
        // You can add authentication logic here

        // Redirect to the home page after successful login
        return "redirect:/";
    }

}
