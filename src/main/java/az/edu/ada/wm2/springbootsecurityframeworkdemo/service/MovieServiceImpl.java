package az.edu.ada.wm2.springbootsecurityframeworkdemo.service;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.repo.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);

    private final MovieRepository movieRepo;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepo) {
        this.movieRepo = movieRepo;
    }

    @Override
    public Page<MovieDto> listDto(int pageNo, String sortField, String sortDir, String filterField, String filterValue) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortField);
        PageRequest pageRequest = PageRequest.of(pageNo - 1, 10, sort);
        Page<Movie> moviePage = movieRepo.findAll(pageRequest);
        return moviePage.map(this::convertToDto);
    }

    @Override
    public MovieDto save(MovieDto movieDto) {
        Movie movie = convertToEntity(movieDto);
        movie = movieRepo.save(movie);
        return convertToDto(movie);
    }

    @Override
    public MovieDto getById(Long id) {
        return movieRepo.findById(id).map(this::convertToDto).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        movieRepo.deleteById(id);
    }

    @Override
    public List<MovieDto> getAllWebMovies(String keyword) {
        List<Movie> movies = movieRepo.findByCountryContainingIgnoreCase(keyword);
        return movies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        return movies.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private MovieDto convertToDto(Movie movie) {
        return MovieDto.fromMovie(movie);
    }

    private Movie convertToEntity(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setName(movieDto.getName());
        movie.setCountry(movieDto.getCountry());
        movie.setWins(movieDto.getWins());
        return movie;
    }

    @Override
    public MovieDto getDtoById(Long id) {
        Movie movie = movieRepo.findById(id).orElse(null);
        if (movie != null) {
            return convertToDto(movie);
        }
        return null;
    }
}
