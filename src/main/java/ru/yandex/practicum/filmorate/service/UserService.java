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

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User newUser) {
        setUserNameIfEmpty(newUser);
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
        return checkAndGetUserById(id);
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
        checkAndGetUserById(userId);
        checkAndGetUserById(friendId);
        if (userId == friendId) {
            throw new ValidationException("User can't be a friend to himself");
        }
        // добавляем пользователю друга
        boolean friendWasAddedToUser = userStorage.addFriend(userId, friendId);
        if (!friendWasAddedToUser) {
            throw new StorageException("Friend with id=" + friendId + " wasn't added to User with id=" + userId);
        }
        // добавляем в обратную сторону, чтобы оба были друзьями друг у друга
        boolean userWasAddedToNewFriendUser = userStorage.addFriend(friendId, userId);
        if (!userWasAddedToNewFriendUser) {
            throw new StorageException("Friend with id=" + friendId + " wasn't added to User with id=" + userId);
        }


    }

    public void deleteFriend(long userId, long friendId) {
        checkAndGetUserById(userId);
        checkAndGetUserById(friendId);
        if (userId == friendId) {
            throw new ValidationException("User can't be a friend to himself");
        }

        // удаляем друга у пользователя
        boolean friendWasDeletedFromUser = userStorage.deleteFriend(userId, friendId);
        if (!friendWasDeletedFromUser) {
            throw new StorageException("Friend with id=" + friendId + " wasn't deleted from User with id=" + userId);
        }

        // удаляем пользователя у бывшего друга
        boolean userWasDeletedFromExFriendUser = userStorage.deleteFriend(friendId, userId);
        if (!userWasDeletedFromExFriendUser) {
            throw new StorageException("Friend with id=" + friendId + " wasn't deleted from User with id=" + userId);
        }

    }

    private void setUserNameIfEmpty(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private void validateUserToUpdate(User user) {
        setUserNameIfEmpty(user);
        checkAndGetUserById(user.getId());
    }

    protected User checkAndGetUserById(long id) {
        Optional<User> mayBeUser = userStorage.findUserById(id);
        if (mayBeUser.isEmpty()) {
            String message = "User with id=" + id + " not found";
            log.warn(message);
            throw new NotFoundException(message);
        }
        return mayBeUser.get();
    }

    public Set<User> getUserFriends(long userId) {
        checkAndGetUserById(userId);
        return userStorage.getUserFriends(userId);
    }

    public Set<User> getUserCommonFriends(long userId, long otherUserId) {

        if (userId == otherUserId) {
            throw new ValidationException("User can't have common friends with himself");
        }

        checkAndGetUserById(userId);
        checkAndGetUserById(otherUserId);

        Set<User> userFriends = userStorage.getUserFriends(userId);
        Set<User> otherUserIdFriends = userStorage.getUserFriends(otherUserId);

        return userFriends
                .stream()
                .filter(otherUserIdFriends::contains)
                .collect(Collectors.toSet());
    }
}
