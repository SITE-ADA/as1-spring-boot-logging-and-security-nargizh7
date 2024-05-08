package az.edu.ada.wm2.springbootsecurityframeworkdemo.init;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.User;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DbUsersBootstrapper {

    @Bean
    public ApplicationRunner init(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            createUserIfNotFound(userRepo, encoder, "nsadili", "12345", "nsadili@yourdomain.com", "ROLE_ADMIN");
            createUserIfNotFound(userRepo, encoder, "shrek", "shrek123", "shrek@swamp.com", "ROLE_USER");
        };
    }

    private void createUserIfNotFound(UserRepository repo, PasswordEncoder encoder, String username, String password, String email, String role) {
        Optional<User> existingUser = repo.findByUsername(username);
        if (!existingUser.isPresent()) {
            User user = new User(username, encoder.encode(password), email);
            user.addRole(role);  // Make sure this matches your role setup in SecurityConfig
            repo.save(user);
        }
    }
}

