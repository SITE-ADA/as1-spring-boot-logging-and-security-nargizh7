package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.service.MovieService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    @Autowired
    private MovieService movieService;

    @GetMapping("/index")
    public String getIndexPage(Model model,
                               @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                               @RequestParam(name = "sortField", defaultValue = "name") String sortField,
                               @RequestParam(name = "sortDir", defaultValue = "asc") String sortDir,
                               @RequestParam(name = "filterField", defaultValue = "") String filterField,
                               @RequestParam(name = "filterValue", defaultValue = "") String filterValue) {
        return getMovies(model, pageNo, sortField, sortDir, filterField, filterValue);
    }

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
    @PreAuthorize("hasRole('ADMIN')")
    public String createNewMovie(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "new";
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@Valid @ModelAttribute("movieDto") MovieDto movieDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movieDto", result);
            redirectAttributes.addFlashAttribute("movieDto", movieDto);
            return "new";  // Redirect back to the form page with errors
        }
        try {
            movieService.save(movieDto);
            return "redirect:/movie/";
        } catch (Exception e) {
            logger.error("Error saving movie: {}", movieDto.getName(), e);
            return "errorPage";
        }
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id) {
        try {
            movieService.deleteById(id);
            return "redirect:/movie/";
        } catch (Exception e) {
            logger.error("Error deleting movie with ID: {}", id, e);
            return "errorPage";
        }
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        MovieDto movieDto = movieService.getDtoById(id);
        if (movieDto == null) {
            return "redirect:/movie/";  // Redirect if no movie is found
        }
        model.addAttribute("movieDto", movieDto);
        return "update";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateMovie(@PathVariable Long id, @Valid @ModelAttribute("movieDto") MovieDto movieDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("movieDto", movieDto);
            model.addAttribute("org.springframework.validation.BindingResult.movieDto", result);
            return "update";  // Return directly to the update view with the form data and errors
        }
        movieService.save(movieDto);
        return "redirect:/movie/";  // Redirect after successful update
    }

    @GetMapping("/filter/{keyword}")
    public String getWebMovies(Model model, @PathVariable String keyword) {
        List<MovieDto> movies = movieService.getAllWebMovies(keyword);
        model.addAttribute("movies", movies);
        return "index";
    }
}
