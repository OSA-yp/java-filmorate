# java-filmorate

## Примеры запросов

Вот так можно получить общих друзей:
```
SELECT uf1.friend_id AS common_friend_id
FROM users_friends uf1
JOIN users_friends uf2
ON uf1.friend_id = uf2.friend_id
WHERE uf1.user_id = 1
AND uf2.user_id = 2
```
А вот так TOP-10 фильмов:
```
SELECT f.name,
    COUNT(fl.user_id) AS likes_count
FROM films f
JOIN films_likes fl ON f.film_id=fl.film_id
GROUP BY f.name
ORDER BY likes_count DESC
LIMIT 10
```


Изменения текущего спринта:
1. Модель доработана, добавлена Table mpa_rate для соответствия тестам
2. Переход на хранение в H2
3. Добавлены DTO и мапперы для User и Film
4. Диаграмма была изменена, но так как png не пропускает gitHub она удалена