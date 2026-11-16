CREATE DATABASE IF NOT EXISTS videogamescatalog;
USE videogamescatalog;

DROP TABLE IF EXISTS videogames_list;
DROP TABLE IF EXISTS lists;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS videogames;

CREATE TABLE videogames(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    genre VARCHAR(24) NOT NULL,
    year_int INT NOT NULL,
    grade REAL NOT NULL
) ENGINE=InnoDB;

CREATE TABLE players(
    id INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(50) NOT NULL,
    mail VARCHAR(50) NOT NULL UNIQUE
) ENGINE=InnoDB;

CREATE TABLE lists(
    id INT PRIMARY KEY AUTO_INCREMENT,
    player_id INT NOT NULL,
    FOREIGN KEY(player_id) REFERENCES players(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE videogames_list(
    list_id INT NOT NULL,
    videogame_id INT NOT NULL,
    PRIMARY KEY(list_id, videogame_id),
    FOREIGN KEY(list_id) REFERENCES lists(id) ON DELETE CASCADE,
    FOREIGN KEY(videogame_id) REFERENCES videogames(id) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO videogames(title, genre, year_int, grade)
VALUES
    ('The Legend of Zelda: Breath of the Wild', 'Adventure', 2017, 10),
    ('Red Dead Redemption 2', 'Action', 2018, 9.8),
    ('Hades', 'Roguelike', 2020, 9.3),
    ('Hollow Knight', 'Platform', 2017, 9.1),
    ('Celeste', 'Platform', 2018, 8.9);

INSERT INTO players(NAME, mail)
VALUES ('Edu', 'edu@example.com'), ('Alice', 'alice@example.com'), ('Bob', 'bob@example.com');

INSERT INTO lists(player_id)
VALUES (1), (2), (3);

INSERT INTO videogames_list(list_id, videogame_id)
VALUES (1, 1), (1, 2), (2, 3), (2, 4), (3, 5), (3, 1);
