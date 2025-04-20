package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@EqualsAndHashCode(of = "id")
public class User {

    private Long id;

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @NotNull
    @Past
    private LocalDate birthday;

    private Set<Long> friends;

}
