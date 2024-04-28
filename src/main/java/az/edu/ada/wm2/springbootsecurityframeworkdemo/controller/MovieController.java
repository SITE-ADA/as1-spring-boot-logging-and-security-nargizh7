package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping({"", "/", "/list"})
    public String getMovies(Model model,
                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "sortField", defaultValue = "name") String sortField,
                            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                            @RequestParam(name = "filterField", defaultValue = "") String filterField,
                            @RequestParam(name = "filterValue", defaultValue = "") String filterValue) {
        Page<MovieDto> moviesPage = movieService.listDto(pageNo, sortField, sortDir, filterField, filterValue);
        model.addAttribute("movies", moviesPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalElements", moviesPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("filterField", filterField);
        model.addAttribute("filterValue", filterValue);

        return "index";
    }


    @GetMapping("/new")
    public String createNewMovie(Model model) {
        model.addAttribute("movieDto", new MovieDto()); // Ensure a new MovieDto is provided to the form
        return "new"; // Changed from "/new" to "new"
    }

    @PostMapping("/")
    public String save(@ModelAttribute("movieDto") MovieDto movieDto) {
        movieService.save(movieDto);
        return "redirect:/movie/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/movie/";
    }

    @GetMapping("/update/{id}")
    public String updateMovie(@PathVariable Long id, Model model) {
        MovieDto movieDto = movieService.getDtoById(id);  // Assuming this method fetches the movie as a DTO
        if (movieDto == null) {
            return "redirect:/movie/";  // Redirect if no movie is found with the given ID
        }
        model.addAttribute("movieDto", movieDto);
        return "update";  // Make sure this corresponds to the name of the Thymeleaf template
    }


    @GetMapping("/filter/{keyword}")
    public String getWebMovies(Model model, @PathVariable String keyword) {
        List<MovieDto> movies = movieService.getAllWebMovies(keyword);
        model.addAttribute("movies", movies);
        return "index"; // Changed from "/index" to "index"
    }
}

