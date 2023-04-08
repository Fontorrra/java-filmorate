package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    @Autowired
    FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
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

    public Collection<Film> getMostPopularFilms(Integer count) {
        if (count == null || count <= 0) {
            log.warn("Count must be positive");
            throw new IncorrectCountValueException("Count must be positive");
        }
        List<Film> films = new ArrayList<>(filmStorage.getFilms());
        films.sort(Comparator.comparingInt(Film::getLikes).reversed());
        return films.subList(0, Math.min(count, films.size()));
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long filmId) {
        Film film = filmStorage.getFilm(filmId);
        if (film == null) {
            log.warn("Film with id {} does not exist", filmId);
            throw new FilmNotFoundException("Film with this id does not exist");
        }
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        userStorage.getUser(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        userStorage.getUser(userId);
        filmStorage.removeLike(filmId, userId);
    }

    public boolean isNotValid(Film film) {
        return film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28)) ||
                film.getDuration() <= 0;
    }
}