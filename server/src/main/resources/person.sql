CREATE DATABASE IF NOT EXISTS `searchable-video-library`;
USE `searchable-video-library`;

CREATE TABLE IF NOT EXISTS `Person` (
  `id`        integer unsigned  NOT NULL  AUTO_INCREMENT,
  `username`  varchar(63)       NOT NULL,
  `email`     varchar(63)       NOT NULL,
  `name`      varchar(63)       NOT NULL,
  `country`   varchar(63)       NOT NULL,
  PRIMARY KEY (`id`, `username`)
);
