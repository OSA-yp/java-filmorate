package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Long, User> users = new HashMap<>();
    private Long userId = 1L;

    @Override
    public boolean createUser(User newUser) {
        newUser.setId(userId++);
        newUser.setFriends(new HashSet<>());
        users.put(newUser.getId(), newUser);
        return true;
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }

    @Override
    public boolean updateUser(User userToUpdate) {
        if (userToUpdate.getFriends() == null) {
            userToUpdate.setFriends(new HashSet<>());
        }
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

    @Override
    public boolean addFriend(long userId, long friendId) {
        users.get(userId).getFriends().add(friendId);
        return true;
    }

    @Override
    public boolean deleteFriend(long userId, long friendId) {
        users.get(userId).getFriends().remove(friendId);
        return true;
    }

    @Override
    public boolean findFriendById(long userId, long friendId) {
        return users.get(userId).getFriends().contains(friendId);
    }

    @Override
    public Set<User> getUserFriends(long userId) {
        return users.get(userId).getFriends()
                .stream()
                .map(users::get)
                .collect(Collectors.toSet());
    }
}
