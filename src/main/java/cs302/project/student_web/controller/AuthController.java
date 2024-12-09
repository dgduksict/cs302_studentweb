package cs302.project.student_web.controller;

import cs302.project.student_web.model.dto.UserLoginDto;
import cs302.project.student_web.model.dto.UserRegistrationDto;
import cs302.project.student_web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null &&
                auth.isAuthenticated() &&
                !(auth instanceof AnonymousAuthenticationToken);

        logger.info("Is user authenticated: {}", isAuthenticated);
        if (isAuthenticated) {
            logger.info("Current user: {}", auth.getName());
        }

        return "auth/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("users") UserLoginDto userLoginDto,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            // Authenticate user
            boolean isAuthenticated = userService.authenticateUser(userLoginDto.getUsername(), userLoginDto.getPassword());

            if (isAuthenticated) {
                // Redirect to the home page or dashboard
                redirectAttributes.addFlashAttribute("success", "Login successful!");
                return "redirect:/journal/list";
            } else {
                // If authentication fails
                model.addAttribute("error", "Invalid username or password");
                return "auth/login";
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("users") UserRegistrationDto userDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(userDto);
            redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}
