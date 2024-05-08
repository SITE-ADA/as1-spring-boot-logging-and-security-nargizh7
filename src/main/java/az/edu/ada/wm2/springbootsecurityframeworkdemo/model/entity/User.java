package az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String roles = "ROLE_USER";  // Default role

    @Transient
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));  // Default role
    }

    public User addRole(String role) {
        if (!roles.contains(role)) {
            roles += ";" + role;
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return this;
    }
    @PrePersist
    @PreUpdate
    private void saveRoles() {
        roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(";"));
    }

    @PostLoad
    private void readRoles() {
        authorities = Arrays.asList(roles.split(";")).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}