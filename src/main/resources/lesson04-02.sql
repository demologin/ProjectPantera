DROP TABLE IF EXISTS player;

CREATE TABLE player
(
    id          BIGSERIAL PRIMARY KEY,
    login       VARCHAR(128) NOT NULL UNIQUE,
    password    VARCHAR(100) NOT NULL,
    role        VARCHAR(50),
    game_points INT,
    date        DATE
);

INSERT INTO player
    (login, password, role, game_points, date)
VALUES ('Carl', 'admin', 'ADMIN', 15, '2026-01-23'),
       ('Alisa', 'qwerty', 'USER', 8, '2025-05-05'),
       ('Bob', 'asdfg', 'GUEST', NULL, '2026-01-10'),
       ('Mike', 'mikepass', 'GUEST', 3, '2026-01-03'),
       ('Elon', 'musk', 'USER', 20, '2026-01-03'),
       ('Ann', 'pass', 'ADMIN', 18, '2026-01-03'),
       ('Elona', 'musk', 'GUEST', 10, '2024-01-04'),
       ('Michael', '12345', 'GUEST', 8, '2027-01-01');

SELECT login "Login", password
  FROM player;

SELECT id, login, password p, role, game_points, date
  FROM player
 WHERE id > 3
   AND role = 'GUEST';

SELECT *
  FROM player
 WHERE login ILIKE '%a%';

SELECT *
  FROM player
 WHERE date < NOW()
 ORDER BY date;

SELECT MAX(game_points) all_points, role
  FROM player
 GROUP BY role;

UPDATE player
   SET game_points = 123
 WHERE id = 1;

DELETE
  FROM player
 WHERE id = 8;

SELECT *
  FROM player;



UPDATE player
   SET game_points = game_points + 10
 WHERE id IN (SELECT id
                FROM player
               WHERE password = 'musk');


SELECT id, login, password
  FROM player
 WHERE game_points = (SELECT MAX(game_points) FROM player);

SELECT (SELECT AVG(player.game_points) FROM player) avg_points,
       player.login,
       player.game_points,
       CASE
           WHEN game_points > (SELECT AVG(player.game_points) FROM player)
               THEN 'Winner'
           ELSE 'Loser'
           END AS                                   result
  FROM player;

SELECT role,
       AVG(game_points)   a,
       MAX(game_points)   max,
       MIN(game_points)   min,
       SUM(game_points)   s,
       COUNT(game_points) n
  FROM player
 WHERE game_points IS NOT NULL
 GROUP BY role
HAVING SUM(game_points) > 25
 ORDER BY s DESC;


SELECT login, password, extract(YEAR FROM date) year_of_registration
  FROM player
ORDER BY login;

SELECT ROW_NUMBER() OVER (PARTITION BY role) row_num,
       login,role,game_points,
       AVG(game_points)   OVER (PARTITION BY role) avg_role_points
  FROM player;