package az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;
import lombok.Data;

@Data
public class MovieDto {

    private Long id;
    private String name;
    private String country;
    private Integer wins;

    // Converts this DTO to the Movie entity
    public Movie toMovie() {
        Movie movie = new Movie();
        movie.setId(this.id);
        movie.setName(this.name);
        movie.setCountry(this.country);
        movie.setWins(this.wins);
        return movie;
    }

    // Static method to create a DTO from a Movie entity
    public static MovieDto fromMovie(Movie movie) {
        MovieDto dto = new MovieDto();
        dto.setId(movie.getId());
        dto.setName(movie.getName());
        dto.setCountry(movie.getCountry());
        dto.setWins(movie.getWins());
        return dto;
    }
}
