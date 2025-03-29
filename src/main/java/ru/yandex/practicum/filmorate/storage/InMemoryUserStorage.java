package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public boolean createUser(User newUser) {
        users.put(newUser.getId(), newUser);
        return true;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public boolean updateUser(User userToUpdate) {
        users.put(userToUpdate.getId(), userToUpdate);
        return true;
    }

    @Override
    public Optional<User> findUserById(long id) {
        Optional<User> userOptional = Optional.empty();

        if (users.containsKey(id)) {
            userOptional = Optional.of(users.get(id));
        }
        return userOptional;
    }
}
