package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.IdAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.IdDoesNotExistsException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage  {

    HashMap<Long, Film> films = new HashMap<>();
    private long id = 1;

    public long getIdForFilm() {
        while (films.containsKey(id)) {
            id++;
        }
        id++;
        return id - 1;
    }

    @Override
    public Film addFilm(Film film) {
        if (films.containsKey(film.getId())) {
            log.warn("Film with id {} already exists", film.getId());
            throw new IdAlreadyExistsException("Film with this id already exists");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public boolean deleteFilm(long id) {
        if (!films.containsKey(id)) {
            log.warn("Film with id {} does not exist", id);
            throw new FilmNotFoundException("Film with this id does not exist");
        }
        return false;
    }

    @Override
    public boolean updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            log.warn("Film with id {} does not exist", film.getId());
            throw new FilmNotFoundException("Film with this id does not exist");
        }
        films.put(film.getId(), film);
        return true;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Film getFilm(Long filmId) {
        return films.get(filmId);
    }

    @Override
    public void addLike(Long filmId, Long userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Film with id {} does not exist", filmId);
            throw new FilmNotFoundException("Film with this id does not exist");
        }
        films.get(filmId).addLike(userId);
    }

    @Override
    public void removeLike(Long filmId, Long userId) {
        if (!films.containsKey(filmId)) {
            log.warn("Film with id {} does not exist", filmId);
            throw new IdDoesNotExistsException("Film with this id does not exist");
        }
        films.get(filmId).removeLike(userId);
    }
}