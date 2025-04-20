package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

@RequiredArgsConstructor
public class BaseRepository<T> {
    protected final JdbcTemplate jdbcTemplate;
    private final RowMapper<T> mapper;
    private final Class<T> table;

    // TODO понять нужен ли


}
