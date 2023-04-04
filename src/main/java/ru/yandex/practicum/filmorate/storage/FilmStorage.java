package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    boolean deleteFilm(long id);

    boolean updateFilm(Film film);

    Collection<Film> getFilms();

    Film getFilm(Long filmId);

    void addLike(Long filmId, Long userId);

    void removeLike(Long filmId, Long userId);
}
