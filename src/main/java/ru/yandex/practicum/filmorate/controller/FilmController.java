package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
 //// test
    HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, IdAlreadyExistsException {
        log.info("Received POST request for /films endpoint with body {}", film);

        if (isNotValid(film)) {
            log.warn("Film validation failed for {}", film);
            throw new ValidationException("Film validation failed");
        }

        if (film.getId() == null) {
            while (films.containsKey(id)) {
                id++;
            }
            film.setId(id);
            id++;
        }
        else if (films.containsKey(film.getId())) {
            log.warn("Film with id {} already exists", film.getId());
            throw new IdAlreadyExistsException("Film with this id already exists");
        }
        films.put(film.getId(), film);
        log.info("Successfully created film {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws  ValidationException, IdDoesNotExistsException {
        log.info("Received PUT request for /films endpoint with body {}", film);
        if (isNotValid(film)) {
            log.warn("Film validation failed for {}", film);
            throw new ValidationException("Film validation failed");
        }
        if (!films.containsKey(film.getId())){
            log.warn("Film with id {} does not exist", film.getId());
            throw new IdDoesNotExistsException("Film with this id does not exist");
        }
        films.put(film.getId(), film);
        log.info("Film updated: {}", film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("Received GET request for /films endpoint");
        return films.values();
    }

    public boolean isNotValid(Film film) {
        return film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
               film.getDuration() <= 0;
    }
}
