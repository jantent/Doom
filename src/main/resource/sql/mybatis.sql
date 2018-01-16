CREATE DATABASE mybatis
  DEFAULT CHARACTER SET utf8;

use mybatis;
DROP TABLE IF EXISTS USER;
CREATE TABLE USER (
  id          INTEGER AUTO_INCREMENT     NOT NULL,
  username    VARCHAR(64)                NOT NULL,
  sex         VARCHAR(64)                NOT NULL,
  birthday    TIMESTAMP  DEFAULT CURRENT_TIMESTAMP,
  address     VARCHAR(255),
  PRIMARY KEY (id)
);
DROP TABLE IF EXISTS ADMIN;
CREATE TABLE ADMIN (
  adminId INTEGER AUTO_INCREMENT     NOT NULL,
  adminAccount  VARCHAR(64)          NOT NULL,
  adminPassword VARCHAR(64)          NOT NULL,
  address VARCHAR(255),
  PRIMARY KEY (adminId)
);

INSERT INTO ADMIN(adminAccount,adminPassword,address) VALUES ('张怀','123456','上海');
INSERT INTO ADMIN(adminAccount,adminPassword,address) VALUES ('王宇','123456','上海');

INSERT INTO USER(username,sex,address) VALUES ('张怀','男','上海');
INSERT INTO USER(username,sex,address) VALUES ('玉婷','女','上海');
INSERT INTO USER(username,sex,address) VALUES ('刘飞','男','北京');
INSERT INTO USER(username,sex,address) VALUES ('逸飞','男','南京');
INSERT INTO USER(username,sex,address) VALUES ('黄小明','男','南京');
INSERT INTO USER(username,sex,address) VALUES ('张小明','男','南京');
INSERT INTO USER(username,sex,address) VALUES ('路小明','男','南京');
INSERT INTO USER(username,sex,address) VALUES ('晏小明','男','南京');