package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;
    private Long userId = 1L;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    private void validateNewUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    public User createUser(User newUser) {
        validateNewUser(newUser);
        newUser.setId(userId++);
        log.info("New user with id={} was created - {}", newUser.getId(), userStorage.createUser(newUser));
        return newUser;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User updateUser(User userToUpdate) {
        validateUserToUpdate(userToUpdate);
        log.info("User with id={} was updated - {}", userToUpdate.getId(), userStorage.updateUser(userToUpdate));
        Optional<User> userOptional = userStorage.findUserById(userToUpdate.getId());
        return userOptional.orElse(null);
    }

    private void validateUserToUpdate(User user) {
        validateNewUser(user);
        Optional<User> mayBeUser = userStorage.findUserById(user.getId());
        if (mayBeUser.isEmpty()) {
            String message = "User with id=" + user.getId() + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
    }
}
