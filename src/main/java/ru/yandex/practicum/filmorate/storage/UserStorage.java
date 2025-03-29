package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    boolean createUser(User newUser);

    Collection<User> getUsers();

    boolean updateUser(User userToUpdate);

    Optional<User>  findUserById(long id);
}
