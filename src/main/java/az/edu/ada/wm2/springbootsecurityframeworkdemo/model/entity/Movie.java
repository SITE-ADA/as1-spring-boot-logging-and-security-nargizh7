package az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity;

import lombok.*;
import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String country;

    private Integer wins;

    public Movie(String name, String country, Integer wins) {
        this.name = name;
        this.country = country;
        this.wins = wins;
    }

    public Movie(String name, Integer wins) {
        this(name, name, wins);
    }

    @Override
    public String toString() {
        return "Movie: " + this.name + ": " + this.country;
    }

    public Movie(Long id) {
        this.id = id;
    }
}
