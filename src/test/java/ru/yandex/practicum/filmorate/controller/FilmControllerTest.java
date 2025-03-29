package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class FilmControllerTest {

    @Test
    void createCorrectFilm() {
        FilmController fc = new FilmController(new FilmService(new InMemoryFilmStorage()));
        Film film = new Film(-1, "name", "description", LocalDate.now(), 120);
        fc.createFilm(film);

        assertEquals(1, film.getId());
    }

    @Test
    void createToOldFilm() {
        FilmController fc = new FilmController(new FilmService(new InMemoryFilmStorage()));
        Film film = new Film(-1, "name", "description", LocalDate.of(1800, 1, 1), 120);

        assertThrows(ValidationException.class, () -> fc.createFilm(film));
    }

    @Test
    void createFilmWithNegativeDuration() {
        FilmController fc = new FilmController(new FilmService(new InMemoryFilmStorage()));
        Film film = new Film(-1, "name", "description", LocalDate.of(1900, 1, 1), -120);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Film>> checks = validator.validate(film);

//        assertEquals("должно быть больше 0", checks.iterator().next().getMessage());
        assertEquals("must be greater than 0", checks.iterator().next().getMessage()); // for GitHub tests
    }

    @Test
    void updateFilmWithWrongId() {
        FilmController fc = new FilmController(new FilmService(new InMemoryFilmStorage()));
        Film film = new Film(-1, "name", "description", LocalDate.now(), 120, new HashSet<>() {
        });
        assertThrows(NotFoundException.class, () -> fc.updateFilm(film));
    }

}