package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {

    @BeforeAll
    public static void initializeLocaleAndTimeZone() {
        Locale.setDefault(Locale.US);
    }

    @Test
    void createCorrectUser() {
        UserController uc = new UserController(new UserService(new InMemoryUserStorage()));
        User user = new User(-1, "email@email.org", "login", "name", LocalDate.of(1979, 8, 15), new HashSet<>());
        uc.createUser(user);

        assertEquals(1, user.getId());
    }

    @Test
    void createNoNameUser() {
        UserController uc = new UserController(new UserService(new InMemoryUserStorage()));
        User user = new User(-1, "email@email.org", "login", null, LocalDate.of(1979, 8, 15), new HashSet<>());
        uc.createUser(user);

        assertEquals("login", user.getName());
    }


    @Test
    void updateUserWithWrongId() {
        UserController uc = new UserController(new UserService(new InMemoryUserStorage()));
        User user = new User(-1, "email@email.org", "login", "name", LocalDate.of(1979, 8, 15), new HashSet<>());

        assertThrows(NotFoundException.class, () -> uc.updateUser(user));
    }

    @Test
    void createUserWithBirthdayInFuture() {
        UserController uc = new UserController(new UserService(new InMemoryUserStorage()));
        User user = new User(-1, "email@email.org", "login", "name", LocalDate.of(2979, 8, 15), new HashSet<>());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        Set<ConstraintViolation<User>> checks = validator.validate(user);

        //assertEquals("должно содержать прошедшую дату", checks.iterator().next().getMessage());
        assertEquals("must be a past date", checks.iterator().next().getMessage()); // for GitHub tests
    }


}