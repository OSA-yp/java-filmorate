package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.user.NewUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequestDTO;
import ru.yandex.practicum.filmorate.dto.user.UserResponseDTO;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponseDTO toUserResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .login(user.getLogin())
                .birthday(user.getBirthday())
                .friends(user.getFriends())
                .build();
    }

    public static Collection<UserResponseDTO> toUserResponseDTO(Collection<User> userSet) {
        return userSet.stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .login(user.getLogin())
                        .birthday(user.getBirthday())
                        .friends(user.getFriends())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));

    }

    public static User toUser(NewUserRequestDTO newUserRequestDTO) {
        return User.builder()
                .email(newUserRequestDTO.getEmail())
                .login(newUserRequestDTO.getLogin())
                .name(newUserRequestDTO.getName())
                .birthday(newUserRequestDTO.getBirthday())
                .build();
    }

    public static User toUser(UpdateUserRequestDTO updateUserRequestDTO) {
        return User.builder()
                .id(updateUserRequestDTO.getId())
                .email(updateUserRequestDTO.getEmail())
                .login(updateUserRequestDTO.getLogin())
                .name(updateUserRequestDTO.getName())
                .birthday(updateUserRequestDTO.getBirthday())
                .build();
    }


}
