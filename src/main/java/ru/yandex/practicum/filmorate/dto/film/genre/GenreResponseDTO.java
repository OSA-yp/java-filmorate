package ru.yandex.practicum.filmorate.dto.film.genre;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class GenreResponseDTO {

    private int id;

    @NotNull
    private String name;
}
