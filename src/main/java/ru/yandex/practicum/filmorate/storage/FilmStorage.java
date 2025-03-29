package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {

    boolean createFilm(Film newFilm);

    Collection<Film> getFilms();

    boolean updateFilm(Film filmToUpdate);

    Optional<Film> getFilmById(long id);

    int getFilmsCount();

    boolean addUserLike(long filmId, long userId);

    boolean deleteUserLike(long filmId, long userId);

    Collection<Film> getTopFilms(int count);
}
