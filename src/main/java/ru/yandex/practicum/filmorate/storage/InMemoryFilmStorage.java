package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Long, Film> films = new HashMap<>();

    @Override
    public boolean createFilm(Film newFilm) {
        newFilm.setUsersLikes(new HashSet<>());
        films.put(newFilm.getId(), newFilm);
        return true;
    }

    @Override
    public Collection<Film> getFilms() {
        return films.values();
    }

    @Override
    public Collection<Film> getTopFilms(int count) {
        return films.values()
                .stream()
                .sorted(Comparator.comparingInt((Film film) -> film.getUsersLikes().size()).reversed())
                .limit(count)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean updateFilm(Film filmToUpdate) {
        films.put(filmToUpdate.getId(), filmToUpdate);
        return true;
    }

    @Override
    public Optional<Film> getFilmById(long id) {
        if (films.containsKey(id)) {
            return Optional.of(films.get(id));
        }
        return Optional.empty();
    }

    @Override
    public int getFilmsCount() {
        return films.size();
    }

    @Override
    public boolean addUserLike(long filmId, long userId) {
        films.get(filmId).getUsersLikes().add(userId);
        return true;
    }

    @Override
    public boolean deleteUserLike(long filmId, long userId) {
        films.get(filmId).getUsersLikes().remove(userId);
        return true;
    }


}
