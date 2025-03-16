package ru.yandex.practicum.filmorate.controller;

import com.google.gson.Gson;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController {

    private final HashMap<Long, User> users = new HashMap<>();
    private Long userId = 1L;


    @PostMapping
    public User createUser(@RequestBody @Valid User newUser) {
        validateNewUser(newUser);
//        newUser.setId(userId++);
        newUser.setId(userId++);
        users.put(newUser.getId(), newUser);
        log.info("New user with id={} was created", newUser.getId());
        return newUser;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User userToUpdate) {
        validateUserToUpdate(userToUpdate);
        users.put(userToUpdate.getId(), userToUpdate);
        log.info("User with id={} was updated", userToUpdate.getId());
        return users.get(userToUpdate.getId());
    }


    private void validateNewUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    private void validateUserToUpdate(User user) {
        validateNewUser(user);

        if (!users.containsKey(user.getId())) {
            String message = "User with id=" + user.getId() + " not found";
            Gson gson = new Gson();
            log.warn(message);
            throw new NotFoundException(gson.toJson(message));
        }
    }


}
