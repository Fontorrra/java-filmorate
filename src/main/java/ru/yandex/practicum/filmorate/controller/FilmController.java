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

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) throws ValidationException, IdAlreadyExistsException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))
            || film.getDuration().isNegative() || film.getDuration().isZero()) {
            //log.warn("Неправильная дата релиза или длительность фильма");
            throw new ValidationException();
        }

        if (films.containsKey(film.getId())) throw new IdAlreadyExistsException();
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) throws  ValidationException {
        if (film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28))
                &&(film.getDuration().isNegative() || film.getDuration().isZero())) throw new ValidationException();

        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    public boolean isValid(Film film) {
        return !film.getName().isBlank() &&
                film.getDescription().length() <= 200 &&
                film.getReleaseDate().isAfter(LocalDate.of(1895, 12, 28)) &&
                !film.getDuration().isNegative() && !film.getDuration().isZero();
    }
}
