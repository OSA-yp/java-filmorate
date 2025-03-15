package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootApplication
public class FilmorateApplication {
	public static void main(String[] args) {
		SpringApplication.run(FilmorateApplication.class, args);

		// TODO Добавьте логирование для операций, которые изменяют сущности — добавляют и обновляют их.
		//  Также логируйте причины ошибок — например, если валидация не пройдена. Это считается хорошей практикой.
	}

}
