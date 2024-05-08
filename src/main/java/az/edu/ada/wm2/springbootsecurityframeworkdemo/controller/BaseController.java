package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;

@Controller
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    protected void logInfo(String message) {
        logger.info(message);
    }
    @Autowired
    @Qualifier("greetText")
    private String welcomeMessage;

    @Autowired
    @Qualifier("byeText")
    private String farewellMessage;

    @GetMapping("/")
    public String getWelcomePage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/registration";  // Redirect to registration if not logged in
        }
        return "index";  // Show index if logged in
    }


    @GetMapping("/bye")
    public String getFarewellPage(Model model){
        model.addAttribute("message", sanitize(farewellMessage)); // Sanitize the message to prevent XSS
        return "index";
    }

    // Method to sanitize messages
    private String sanitize(String input) {
        // Implement sanitization logic here to escape HTML characters
        return input.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
    }
}
