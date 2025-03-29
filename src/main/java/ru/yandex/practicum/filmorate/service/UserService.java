package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.StorageException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserStorage userStorage;
    private Long userId = 1L;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public User createUser(User newUser) {
        validateNewUser(newUser);
        newUser.setId(userId++);
        boolean wasCreated = userStorage.createUser(newUser);
        if (!wasCreated) {
            throw new StorageException(newUser + " wasn't created");
        }
        log.info("New user with id={} was created", newUser.getId());
        return newUser;
    }

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(long id) {
        if (checkUserExist(id)) {
            Optional<User> user = userStorage.findUserById(id);
            return user.orElse(null);
        }
        return null;
    }

    public User updateUser(User userToUpdate) {
        validateUserToUpdate(userToUpdate);
        boolean wasUpdated = userStorage.updateUser(userToUpdate);
        if (!wasUpdated) {
            throw new StorageException(userToUpdate + " wasn't updated");
        }
        log.info("User with id={} was updated", userToUpdate.getId());
        Optional<User> userOptional = userStorage.findUserById(userToUpdate.getId());
        return userOptional.orElse(null);
    }

    public void addFriend(long userId, long friendId) {
        checkUserExist(userId);
        checkUserExist(friendId);
        if (userId == friendId) {
            throw new ValidationException("User can't be a friend to himself");
        }
        boolean wasAdded = userStorage.addFriend(userId, friendId);
        if (!wasAdded) {
            throw new StorageException("Friend with id=" + friendId + " wasn't added to User with id=" + userId);
        }
    }

    public void deleteFriend(long userId, long friendId) {
        checkUserExist(userId);
        checkUserExist(friendId);
        if (userId == friendId) {
            throw new ValidationException("User can't be a friend to himself");
        }

        if (!userStorage.findFriendById(userId, friendId)) {
            throw new NotFoundException("Can't find friend with id=" + friendId + " from User with id=" + userId);
        }

        boolean wasAdded = userStorage.deleteFriend(userId, friendId);
        if (!wasAdded) {
            throw new StorageException("Friend with id=" + friendId + " wasn't deleted from User with id=" + userId);
        }

    }

    private void validateNewUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private void validateUserToUpdate(User user) {
        validateNewUser(user);
        checkUserExist(user.getId());
    }

    private boolean checkUserExist(long id) {
        Optional<User> mayBeUser = userStorage.findUserById(id);
        if (mayBeUser.isEmpty()) {
            String message = "User with id=" + id + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
        return true;
    }

    public Set<Long> getUserFriends(long id) {
        return userStorage.getUserFriends(id);
    }

    public Set<Long> getUserCommonFriends(long userId, long otherUserId) {
        checkUserExist(userId);
        checkUserExist(otherUserId);

        if (userId == otherUserId) {
            throw new ValidationException("User can't have common friends with himself");
        }

        return userStorage.getUserFriends(userId)
                .stream()
                .filter(userStorage.getUserFriends(otherUserId)::contains)
                .collect(Collectors.toSet());
    }
}
