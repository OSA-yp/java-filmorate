package ru.yandex.practicum.filmorate.dto.film;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import ru.yandex.practicum.filmorate.config.AppConfig;
import ru.yandex.practicum.filmorate.dto.film.genre.GenreResponseDTO;
import ru.yandex.practicum.filmorate.dto.film.genre.GenreResponseInFilmDTO;
import ru.yandex.practicum.filmorate.dto.film.mparate.MpaRateResponseInFilmDTO;
import ru.yandex.practicum.filmorate.model.Genre;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilmResponseDTO {

    private long id;

    @NotBlank
    private String name;

    @Length(max = AppConfig.FILM_DESCRIPTION_LENGTH)
    private String description;

    @NotNull
    private LocalDate releaseDate;

    @Positive
    private int duration;

    private List<Long> usersLikes;

    private MpaRateResponseInFilmDTO mpa;

    private List<GenreResponseInFilmDTO> genres;

}
