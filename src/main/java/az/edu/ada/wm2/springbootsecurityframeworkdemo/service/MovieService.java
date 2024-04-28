package az.edu.ada.wm2.springbootsecurityframeworkdemo.service;

import org.springframework.data.domain.Page;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie save(Movie movie);

    Movie getById(Long id);

    void deleteById(Long id);

    List<Movie> getAllWebMovies(String keyword);

    List<Movie> getAllMovies(); // This method is retained for fetching all movies

    Page<Movie> list(int pageNo, String sortField, String sortDir, String filterField, String filterValue);

}
