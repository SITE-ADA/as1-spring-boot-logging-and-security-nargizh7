package az.edu.ada.wm2.springbootsecurityframeworkdemo.config;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.User;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import java.util.Optional;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository repo) {
        return username -> {
            Optional<User> user = repo.findByUsername(username);
            return user.orElseThrow(() ->
                    new UsernameNotFoundException("User not found: " + username));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/registration", "/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/movie/**").hasRole("USER")
                        .anyRequest().authenticated())
                .formLogin(loginConfigurer -> loginConfigurer
                        .loginPage("/login")
                        .defaultSuccessUrl("/movie/list", true)
                        .permitAll())
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(exceptions -> exceptions
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                response.sendRedirect("/403")));

        return http.build();
    }
}
