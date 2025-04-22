package ru.yandex.practicum.filmorate.dto.film.mparate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MpaRateRequestInFilmDTO {
    private int id;
}
