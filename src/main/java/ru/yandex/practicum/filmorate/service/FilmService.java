package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.config.AppConfig;
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

    private final AppConfig appConfig;
    private final FilmStorage filmStorage;
    private final UserService userService;
    private Long filmId = 1L;

    public FilmService(AppConfig appConfig, FilmStorage filmStorage, UserService userService) {
        this.appConfig = appConfig;
        this.filmStorage = filmStorage;
        this.userService = userService;
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

    public Collection<Film> getTopFilms(Integer count) {
        if (count == null) {
            count = appConfig.getDefaultTopFilmCount();
        }
        return filmStorage.getTopFilms(count);
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

    public void addUserLike(long filmId, long userId) {
        checkFilmExist(filmId);
        userService.checkUserExist(userId);

        boolean wasAdded = filmStorage.addUserLike(filmId, userId);
        if (!wasAdded) {
            throw new StorageException("Like by User with id=" + userId + " wasn't added to Film with id=" + filmId);
        }
    }

    public void deleteUserLike(long filmId, long userId) {
        checkFilmExist(filmId);
        checkFilmUserLikeExist(filmId, userId);

        boolean wasAdded = filmStorage.deleteUserLike(filmId, userId);
        if (!wasAdded) {
            throw new StorageException("Like by User with id=" + userId + " wasn't delete from Film with id=" + filmId);
        }
    }

    private void validateFilmToUpdate(Film film) {
        validateNewFilm(film);
        checkFilmExist(film.getId());
    }

    private void checkFilmExist(long filmId) {
        Optional<Film> optionalFilm = filmStorage.getFilmById(filmId);
        if (optionalFilm.isEmpty()) {
            String message = "Film with id=" + filmId + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    private void checkFilmUserLikeExist(long filmId, long userLikeId) {
        Optional<Film> optionalFilm = filmStorage.getFilmById(filmId);

        if (optionalFilm.isPresent()) {
            if (!optionalFilm.get().getUsersLikes().contains(userLikeId)) {
                String message = "Like with User id=" + userLikeId + " not found";
                log.warn(message);
                throw new NotFoundException(message);
            }
        }
    }
}

