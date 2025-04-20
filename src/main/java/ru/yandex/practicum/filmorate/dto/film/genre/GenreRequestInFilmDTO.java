package ru.yandex.practicum.filmorate.dto.film.genre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenreRequestInFilmDTO {
    private int id;
}
