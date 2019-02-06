CREATE DATABASE IF NOT EXISTS `searchable_video_library`;
USE `searchable_video_library`;

CREATE TABLE IF NOT EXISTS `Person` (
  `id`        integer unsigned  NOT NULL  AUTO_INCREMENT,
  `email`     varchar(50)       NOT NULL,
  `name`      varchar(50)       NOT NULL,
  `country`   varchar(50)       NOT NULL,
  PRIMARY KEY (`id`)
);
