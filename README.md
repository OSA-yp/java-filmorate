# java-filmorate

## ER диаграмма для Filmorate

![ER диаграмма для Filmorate](./src/main/resources/Filmogram-ER-diagram.png)


Рейтинг Ассоциации кинокомпаний (MPA) добавлен к фильму как строка, в ней будут храниться значения Enum, это осознанная денормализация, для ускорения работы БД (рекомендация из вебинара, который нам рекомендовали). 

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
