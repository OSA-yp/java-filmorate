package ru.yandex.practicum.filmorate.dto.film.mparate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MpaRateResponseInFilmDTO {
    private int id;
    private String name;
}
