package az.edu.ada.wm2.springbootsecurityframeworkdemo.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select m from Movie m where lower(m.country) like %:keyword%")
    List<Movie> findByCountryContainingIgnoreCase(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM movies m WHERE m.description LIKE %:keyword%", nativeQuery = true)
    List<Movie> findByDescriptionContaining(@Param("keyword") String keyword);

    Page<Movie> findAll(Specification<Movie> movieSpecification, Pageable pageable);

    @Query(value = "SELECT * FROM movies m WHERE m.wins > :minWins AND m.country = :country", nativeQuery = true)
    List<Movie> findMoviesByWinsAndCountry(@Param("minWins") int minWins, @Param("country") String country);
}
