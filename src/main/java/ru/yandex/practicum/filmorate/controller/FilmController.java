package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film createFilm(@RequestBody @Valid Film newFilm) {
        return filmService.createFilm(newFilm);
        // TODO переход на DTO
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
        // TODO переход на DTO
    }

    @GetMapping("/popular")
    public Collection<Film> getTopFilms(@RequestParam(required = false) Integer count) {
        return filmService.getTopFilms(count);
        // TODO переход на DTO
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film filmToUpdate) {
        return filmService.updateFilm(filmToUpdate);
        // TODO переход на DTO
    }

    @PutMapping("/{filmId}/like/{userId}")
    public void addUserLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.addUserLike(filmId, userId);
    }

    @DeleteMapping("/{filmId}/like/{userId}")
    public void deleteUserLike(@PathVariable long filmId, @PathVariable long userId) {
        filmService.deleteUserLike(filmId, userId);
    }

    // TODO GET /genres — возвращает список объектов содержащих жанр

    //TODO GET /genres/{id} — возвращает объект содержащий жанр с идентификатором id

    // TODO GET /mpa — возвращает список объектов содержащих рейтинг

    // TODO GET /mpa/{id} — возвращает объект содержащий рейтинг с идентификатором id
}
