package ru.yandex.practicum.filmorate.controller;

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
public class FilmController {

    HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, IdAlreadyExistsException {
        if (isNotValid(film)) {
            //log.warn("Неправильная дата релиза или длительность фильма");
            throw new ValidationException("Неправильная дата фильма или его длительность");
        }

        if (film.getId() == null) {
            while (films.containsKey(id)) {
                id++;
            }
            film.setId(id);
            id++;
        }
        else if (films.containsKey(film.getId())) {
            throw new IdAlreadyExistsException("Фильм с аким id уже существует");
        }
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws  ValidationException, IdDoesNotExistsException {
        if (isNotValid(film)) throw new ValidationException("Неправильная дата фильма или его длительность");
        if (!films.containsKey(film.getId())) throw new IdDoesNotExistsException("Фидьма с таким id е существует");
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
