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
VALUES ('Carl', 'admin', 'ADMIN', 15, NOW()),
       ('Alisa', 'qwerty', 'USER', 8, NOW()),
       ('Bob', 'asdfg', 'GUEST', 3, NOW()),
       ('Mike', 'mikepass', 'GUEST', 2, NOW()),
       ('Elon', 'musk', 'USER', 10, NOW()),
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

