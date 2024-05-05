package az.edu.ada.wm2.springbootsecurityframeworkdemo.init;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.User;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbUsersBootstrapper {

    @Bean
    public ApplicationRunner init(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (!userRepo.findByUsername("admin").isPresent()) {
                userRepo.save(new User("admin", encoder.encode("admin"), "admin_user@ada.edu.az").addRole("ROLE_ADMIN"));
            }
            if (!userRepo.findByUsername("user").isPresent()) {
                userRepo.save(new User("user", encoder.encode("user"), "user@ada.edu.az").addRole("ROLE_USER"));
            }
        };
    }
}
