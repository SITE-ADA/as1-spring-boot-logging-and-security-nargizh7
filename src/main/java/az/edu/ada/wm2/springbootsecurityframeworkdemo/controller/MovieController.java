package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("totalPages", moviesPage.getTotalPages()); // Ensure this is not null
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
    public ModelAndView updateMovie(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("update"); // Changed from "movies/update" to "update"
        MovieDto movieDto = movieService.getById(id); // Fetch the MovieDto
        mv.addObject("movieDto", movieDto);
        return mv;
    }

    @GetMapping("/filter/{keyword}")
    public String getWebMovies(Model model, @PathVariable String keyword) {
        List<MovieDto> movies = movieService.getAllWebMovies(keyword);
        model.addAttribute("movies", movies);
        return "index"; // Changed from "/index" to "index"
    }
}

