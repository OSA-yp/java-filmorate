package ru.yandex.practicum.filmorate.mapper.film;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.film.mparate.MpaRateRequestInFilmDTO;
import ru.yandex.practicum.filmorate.dto.film.mparate.MpaRateResponseDTO;
import ru.yandex.practicum.filmorate.dto.film.mparate.MpaRateResponseInFilmDTO;
import ru.yandex.practicum.filmorate.model.MpaRate;
import ru.yandex.practicum.filmorate.service.MpaRateService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MpaRateMapper {

    public MpaRate toPmaRate(MpaRateRequestInFilmDTO mpaRateRequestInFilmDTO) {
        return MpaRate.builder()
                .id(mpaRateRequestInFilmDTO.getId())
                .build();
    }

    public MpaRateResponseDTO toMpaRateResponseDTO(MpaRate mpaRate) {
        return buildToMpaRateResponseDTO(mpaRate);
    }

    public Collection<MpaRateResponseDTO> toMpaRateResponseDTO(Collection<MpaRate> mpaRates) {
        return mpaRates.stream()
                .map(this::buildToMpaRateResponseDTO)
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public MpaRateResponseInFilmDTO toMpaRateResponseInFilmDTO(MpaRate mpaRate){
        return MpaRateResponseInFilmDTO.builder()
                .id(mpaRate.getId())
                .name(mpaRate.getName())
                .build();
    }

    private MpaRateResponseDTO buildToMpaRateResponseDTO(MpaRate mpaRate) {
        return MpaRateResponseDTO.builder()
                .id(mpaRate.getId())
                .name(mpaRate.getName())
                .build();
    }


}
