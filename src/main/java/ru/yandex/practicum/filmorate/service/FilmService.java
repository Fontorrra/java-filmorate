package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final InMemoryFilmStorage filmStorage;

    @Autowired
    FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        if (isNotValid(film)) {
            log.warn("Film validation failed for {}", film);
            throw new ValidationException("Film validation failed");
        }
        if (film.getId() == null) {
            film.setId(filmStorage.getIdForFilm());
        }
        film = filmStorage.addFilm(film);
        return film;
    }

    public void updateFilm(Film film) {
        if (isNotValid(film)) {
            log.warn("Film validation failed for {}", film);
            throw new ValidationException("Film validation failed");
        }

        filmStorage.updateFilm(film);
    }

    public List<Film> getMostPopularFilms() {
        return null;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public boolean addLike() {

        return true;
    }

    public boolean deleteLike() {

        return true;
    }


    public boolean isNotValid(Film film) {
        return film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getDuration() <= 0;
    }
}
