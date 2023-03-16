package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface FilmStorage {

    Film addFilm(Film film);

    boolean deleteFilm(long id);

    boolean updateFilm(Film film);

    public Collection<Film> getFilms();
}
