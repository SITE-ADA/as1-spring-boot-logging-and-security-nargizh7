package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.SignupDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.User;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("signupDto", new SignupDto());
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@Valid @ModelAttribute("signupDto") SignupDto signupDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        User user = new User(signupDto.getUsername(),
                passwordEncoder.encode(signupDto.getPassword()),
                signupDto.getEmail());
        user.addRole("ROLE_USER");
        userRepository.save(user);
        return "redirect:/login";
    }
}
