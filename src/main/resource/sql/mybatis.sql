CREATE DATABASE mybatis
  DEFAULT CHARACTER SET utf8;

use mybatis;
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  id          INTEGER AUTO_INCREMENT     NOT NULL,
  username    VARCHAR(64) UNIQUE         NOT NULL,
  sex         VARCHAR(64)                NOT NULL,
  birtyday    TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
  address    VARCHAR(255),
  PRIMARY KEY (id)
);

INSERT INTO USER(username,sex,address) VALUES ('张怀','男','上海');
INSERT INTO USER(username,sex,address) VALUES ('玉婷','女','上海');
INSERT INTO USER(username,sex,address) VALUES ('刘飞','男','北京');
INSERT INTO USER(username,sex,address) VALUES ('逸飞','男','南京');