package az.edu.ada.wm2.springbootsecurityframeworkdemo.service;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepo;

    public MovieServiceImpl(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    @Override
    public Page<Movie> list(int pageNo, String sortField, String sortDir, String filterField, String filterValue) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        PageRequest pageable = PageRequest.of(pageNo - 1, 5, sort);
        return movieRepo.findAll(pageable); // Simplified to use pageable only, filter logic can be added if needed
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public Movie getById(Long id) {
        return movieRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        movieRepo.deleteById(id);
    }

    @Override
    public List<Movie> getAllWebMovies(String keyword) {
        return (List<Movie>) movieRepo.getAllWebMoviesUsingJPAQuery(keyword);
    }
}
