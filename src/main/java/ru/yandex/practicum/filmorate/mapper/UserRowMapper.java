package ru.yandex.practicum.filmorate.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

@Component
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new User(
                rs.getLong("USER_ID"),
                rs.getString("EMAIL"),
                rs.getString("LOGIN"),
                rs.getString("NAME"),
                rs.getDate("BIRTHDAY").toLocalDate(),
                new HashSet<>()
        );
    }
}
