package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public boolean createFilm(Film newFilm) {
        films.put(newFilm.getId(), newFilm);
        return true;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public boolean updateFilm(Film filmToUpdate) {
        films.put(filmToUpdate.getId(), filmToUpdate);
        return true;
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        return Optional.empty();
    }

    @Override
    public int getFilmsCount() {
        return films.size();
    }

    // TODO - перенести логику из контроллера
}
