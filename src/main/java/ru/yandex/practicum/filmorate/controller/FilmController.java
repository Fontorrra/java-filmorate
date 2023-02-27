package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@RestController
@RequestMapping("/films")
public class FilmController {

    HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, IdAlreadyExistsException {
        if (isNotValid(film)) {
            //log.warn("Неправильная дата релиза или длительность фильма");
            throw new ValidationException();
        }

        if (film.getId() == null) {
            while (films.containsKey(id)) {
                id++;
            }
            film.setId(id);
            id++;
        }
        else if (films.containsKey(film.getId())) {
            throw new IdAlreadyExistsException();
        }
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws  ValidationException, IdDoesNotExistsException {
        if (isNotValid(film)) throw new ValidationException();
        if (!films.containsKey(film.getId())) throw new IdDoesNotExistsException();
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    public boolean isNotValid(Film film) {
        return film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
               film.getDuration() <= 0;
    }
}
