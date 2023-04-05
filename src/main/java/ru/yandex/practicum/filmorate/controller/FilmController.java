package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    FilmService filmService;

    @Autowired
    FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, IdAlreadyExistsException {
        log.info("Received POST request for /films endpoint with body {}", film);
        film = filmService.createFilm(film);
        log.info("Successfully created film {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws ValidationException, IdDoesNotExistsException {
        log.info("Received PUT request for /films endpoint with body {}", film);
        filmService.updateFilm(film);
        log.info("Film updated: {}", film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Received GET request for /films endpoint");
        return filmService.getFilms();
    }

    @GetMapping("/{filmId}")
    public Film getFilms(@PathVariable Long filmId) {
        log.info("Received GET request for /films/{} endpoint", filmId);
        return filmService.getFilm(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public String addLike(@PathVariable Long filmId,
                          @PathVariable Long userId) {
        log.info("Received PUT request for /films/{}/like/{} endpoint", filmId, userId);
        filmService.addLike(filmId, userId);
        log.info("Like successfully added");
        return "Like successfully added";
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public String deleteLike(@PathVariable Long filmId,
                             @PathVariable Long userId) {
        log.info("Received DELETE request for /films{}/like{} endpoint", filmId, userId);
        filmService.deleteLike(filmId, userId);
        log.info("Like successfully deleted");
        return "Like successfully deleted";
    }

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Received GET request for /films/popular?count={} endpoint", count);
        return filmService.getMostPopularFilms(count);
    }
}