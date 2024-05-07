package az.edu.ada.wm2.springbootsecurityframeworkdemo.controller;

import az.edu.ada.wm2.springbootsecurityframeworkdemo.model.dto.MovieDto;
import az.edu.ada.wm2.springbootsecurityframeworkdemo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
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
        model.addAttribute("movieDto", new MovieDto());
        return "new";
    }

    @PostMapping("/")
    public String save(@Valid @ModelAttribute("movieDto") MovieDto movieDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movieDto", result);
            redirectAttributes.addFlashAttribute("movieDto", movieDto);
            return "new";  // Redirect back to the form page with errors
        }
        movieService.save(movieDto);
        return "redirect:/movie/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        movieService.deleteById(id);
        return "redirect:/movie/";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        MovieDto movieDto = movieService.getDtoById(id);
        if (movieDto == null) {
            return "redirect:/movie/";  // Redirect if no movie is found
        }
        model.addAttribute("movieDto", movieDto);
        return "update";
    }

    @PostMapping("/update/{id}")
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
