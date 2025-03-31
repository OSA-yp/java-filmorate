package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {

    boolean createUser(User newUser);

    Collection<User> getUsers();

    boolean updateUser(User userToUpdate);

    Optional<User> findUserById(long id);

    boolean addFriend(long userId, long friendId);

    boolean deleteFriend(long userId, long friendId);

    boolean findFriendById(long userId, long friendId);

    Set<User> getUserFriends(long id);
}
