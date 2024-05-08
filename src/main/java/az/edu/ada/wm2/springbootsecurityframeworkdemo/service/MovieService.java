package az.edu.ada.wm2.springbootsecurityframeworkdemo.service;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MovieService {
     Page<MovieDto> listDto(int pageNo, String sortField, String sortDir, String filterField, String filterValue);
    MovieDto save(MovieDto movieDto);
    MovieDto getById(Long id);
    void deleteById(Long id);
    List<MovieDto> getAllWebMovies(String keyword);
    List<MovieDto> getAllMovies();  // Return a List of MovieDto

    MovieDto getDtoById(Long id);  // Additional method to handle DTO conversions explicitly if required
}
