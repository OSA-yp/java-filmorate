package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@Qualifier("userDbStorage")
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final UserRepository userRepository;

    @Override
    public Long createUser(User newUser) {
        return userRepository.createUser(newUser);
    }

    @Override
    public Collection<User> getUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public boolean updateUser(User userToUpdate) {
        return userRepository.updateUser(userToUpdate);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public boolean addFriend(long userId, long friendId) {
        return userRepository.addFriend(userId, friendId);
    }

    @Override
    public boolean deleteFriend(long userId, long friendId) {
        return userRepository.deleteFriend(userId, friendId);
    }

    @Override
    public boolean findFriendById(long userId, long friendId) {
        // TODO findFriendById
        return false;
    }

    @Override
    public Set<User> getUserFriends(long id) {
        // TODO Нужно понять выгружать всех или только подвержденных друзей
        return new HashSet<>(userRepository.getUserFriends(id));
    }

    // TODO getFilmGenres

    // TODO setFilmGenres
}
