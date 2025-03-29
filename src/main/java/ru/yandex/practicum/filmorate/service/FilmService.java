package ru.yandex.practicum.filmorate.service;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {

    private final FilmStorage filmStorage;
    private Long filmId = 1L;

    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film newFilm) {
        validateNewFilm(newFilm);
        newFilm.setId(filmId++);
        boolean wasCreated = filmStorage.createFilm(newFilm);
        if (!wasCreated) {
            throw new StorageException(newFilm + " wasn't created");
        }
        log.info("New film with id={} was created", newFilm.getId());
        return newFilm;
    }

    public Collection<Film> getFilms() {
        log.info("{} films were read", filmStorage.getFilmsCount());
        return filmStorage.getFilms();
    }

    public Film updateFilm(Film filmToUpdate) {
        validateFilmToUpdate(filmToUpdate);
        boolean wasUpdated = filmStorage.updateFilm(filmToUpdate);
        if (!wasUpdated) {
            throw new StorageException(filmToUpdate + " wasn't updated");
        }
        log.info("User with id={} was updated", filmToUpdate.getId());
        return filmStorage.getFilmById(filmToUpdate.getId()).orElse(null);
    }

    private void validateNewFilm(Film film) {
        final LocalDate FIRST_FILM_RELEASE_DATE = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(FIRST_FILM_RELEASE_DATE)) {
            String message = "Film release date must be after 28/12/1895";
            log.warn(message);
            throw new ValidationException(message);
        }

    }

    private void validateFilmToUpdate(Film film) {
        validateNewFilm(film);
        Optional<Film> optionalFilm = filmStorage.getFilmById(film.getId());
        if (optionalFilm.isEmpty()) {
            String message = "Film with id=" + film.getId() + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
    }
}
