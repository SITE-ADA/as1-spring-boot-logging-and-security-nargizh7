package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @Autowired
    @Qualifier("greetText")
    private String welcomeMessage;

    @Autowired
    @Qualifier("byeText")
    private String farewellMessage;

    @GetMapping("/")
    public String getWelcomePage(Model model){
        model.addAttribute("message", sanitize(welcomeMessage)); // Sanitize the message to prevent XSS
        return "index";
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
