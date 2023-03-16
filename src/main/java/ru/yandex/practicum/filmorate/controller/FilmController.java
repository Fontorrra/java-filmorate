package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectCountValueException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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
    public Film updateFilm(@Valid @RequestBody Film film) throws  ValidationException, IdDoesNotExistsException {
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

    @GetMapping("/popular")
    public Collection<Film> getPopularFilms(@RequestParam(defaultValue = "10") Integer count) {
        log.info("Received GET request for /films/popular endpoint");
        if (count == null || count <= 0) throw new IncorrectCountValueException("count не может быть неположительным или null");
        return null;
    }


}
