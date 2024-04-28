package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import org.springframework.data.domain.Page;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.entity.Movie;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/movie")
public class MovieController {
    static final Logger LOGGER = LoggerFactory.getLogger(MovieController.class);

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping({"", "/", "/list"})
    public String getMovies(Model model,
                            @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                            @RequestParam(name = "sortField", defaultValue = "name") String sortField,
                            @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                            @RequestParam(name = "filterField", defaultValue = "") String filterField,
                            @RequestParam(name = "filterValue", defaultValue = "") String filterValue) {
        Page<Movie> moviesPage = movieService.list(pageNo, sortField, sortDir, filterField, filterValue);
        model.addAttribute("movies", moviesPage.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", moviesPage.getTotalPages());
        model.addAttribute("totalElements", moviesPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("filterField", filterField);
        model.addAttribute("filterValue", filterValue);

        return "movies/index";
    }

    @GetMapping("/new")
    public String createNewMovie(Model model) {
        model.addAttribute("movie", new Movie());
        return "movies/new";
    }

    @PostMapping("/")
    public String save(@ModelAttribute("movie") Movie movie) {
        movieService.save(movie);
        return "redirect:/movie/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/movie/";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateMovie(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("movies/update");
        Movie movie = movieService.getById(id);
        mv.addObject("movie", movie);
        return mv;
    }

    @GetMapping("/filter/{keyword}")
    public String getWebMovies(Model model, @PathVariable String keyword) {
        model.addAttribute("movies", movieService.getAllWebMovies(keyword));
        return "movies/index";
    }
}
