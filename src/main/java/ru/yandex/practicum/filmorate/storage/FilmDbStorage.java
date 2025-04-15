package ru.yandex.practicum.filmorate.storage;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
@Qualifier("filmDbStorage")
public class FilmDbStorage implements FilmStorage{
    @Override
    public boolean createFilm(Film newFilm) {
        // TODO createFilm
        return false;
    }

    @Override
    public Collection<Film> getFilms() {
        // TODO getFilms
        return List.of();
    }

    @Override
    public boolean updateFilm(Film filmToUpdate) {
        // TODO updateFilm
        return false;
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        // TODO getFilmById
        return Optional.empty();
    }

    @Override
    public int getFilmsCount() {
        // TODO getFilmsCount
        return 0;
    }

    @Override
    public boolean addUserLike(long filmId, long userId) {
        // TODO addUserLike
        return false;
    }

    @Override
    public boolean deleteUserLike(long filmId, long userId) {
        // TODO deleteUserLike
        return false;
    }

    @Override
    public Collection<Film> getTopFilms(int count) {
        // TODO getTopFilms
        return List.of();
    }
}
