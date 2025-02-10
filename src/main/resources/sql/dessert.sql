CREATE DATABASE desserthub
    DEFAULT CHARACTER SET = 'utf8mb4';

CREATE TABLE gallery(  
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    user_id VARCHAR(255),
    user_nn VARCHAR(255),
    gallery_title VARCHAR(255),
    gallery_hashtag VARCHAR(255),
    gallery_img LONGTEXT,
    gallery_liked INT DEFAULT 0,
    gallery_writeday DATETIME
) COMMENT '';
CREATE TABLE board(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    user_id VARCHAR(255),
    user_nn VARCHAR(255),
    board_category VARCHAR(255),
    board_title VARCHAR(255),
    board_content TEXT,
    board_img LONGTEXT,
    board_writeday DATETIME,
    board_view INT DEFAULT 0,
    board_liked INT DEFAULT 0,
    board_comment INT DEFAULT 0
) COMMENT '';

CREATE TABLE user(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    user_id VARCHAR(255),
    user_pw VARCHAR(255),
    user_em VARCHAR(255),
    user_nn VARCHAR(255),
    user_pi LONGTEXT
) COMMENT '';

CREATE TABLE dessert(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    dessert_name VARCHAR(255),
    dessert_image LONGTEXT,
    dessert_chara VARCHAR(255)
) COMMENT '';

CREATE TABLE dlike(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    user_id int,
    target VARCHAR(255),
    target_id INT,
    target_title VARCHAR(255),
    target_content longtext
) COMMENT '';

CREATE TABLE reply(
    id int NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT 'Primary Key',
    user_id int,
    user_nn VARCHAR(255),
    board_id int,
    reply_body longtext,
    reply_time DATETIME
) COMMENT '';

ALTER TABLE board AUTO_INCREMENT=1;
ALTER TABLE gallery AUTO_INCREMENT=1;
ALTER TABLE user AUTO_INCREMENT=1;
ALTER TABLE reply AUTO_INCREMENT=1;
ALTER TABLE dlike AUTO_INCREMENT=1;

SELECT * FROM gallery;
SELECT * FROM board;
SELECT * FROM dessert;

commit;