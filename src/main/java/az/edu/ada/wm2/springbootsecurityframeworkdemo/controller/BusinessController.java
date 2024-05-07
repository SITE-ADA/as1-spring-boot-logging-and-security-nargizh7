package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessController {

    @GetMapping("/users")
    @PreAuthorize("hasRole('USER')") // Ensure that only users with the 'USER' role can access this path
    public String getUsersHomePage(Model model){
        model.addAttribute("message", "This is home for USERS");
        return "index";
    }

    @GetMapping("/admins")
    @PreAuthorize("hasRole('ADMIN')") // Ensure that only users with the 'ADMIN' role can access this path
    public String getAdminsHomePage(Model model){
        model.addAttribute("message", "This is home for ADMINS");
        return "index";
    }
}
