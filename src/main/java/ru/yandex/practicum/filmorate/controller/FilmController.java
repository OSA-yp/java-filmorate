package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController {

    private final HashMap<Long, Film> films = new HashMap<>();
    private Long filmId = 1L;


    @PostMapping
    public Film createFilm(@RequestBody @Valid Film newFilm) {
        validateNewFilm(newFilm);
        newFilm.setId(filmId++);
        log.info("New film with id={} was created", newFilm.getId());
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        log.info("{} films were read", films.size());
        return films.values();
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film filmToUpdate) {
        validateFilmToUpdate(filmToUpdate);
        films.put(filmToUpdate.getId(), filmToUpdate);
        log.info("User with id={} was updated", filmToUpdate.getId());
        return films.get(filmToUpdate.getId());
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
        if (!films.containsKey(film.getId())) {
            String message = "Film with id=" + film.getId() + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
    }


}
