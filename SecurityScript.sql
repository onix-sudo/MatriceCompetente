DROP DATABASE  IF EXISTS `webcm_security`;

CREATE DATABASE  IF NOT EXISTS `webcm_security`;
USE `webcm_security`;

--
-- Table structure for table `users`
-- authorities

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id_user` INT(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL UNIQUE,
  `password` char(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `reset_token` char(36) UNIQUE,
  `expiryDate` char(30),
  PRIMARY KEY (`id_user`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

--
-- Dumping data for table `users`
--
-- NOTE: The passwords are encrypted using BCrypt

-- Default passwords here are: fun123
--

INSERT INTO `users` 
VALUES 
(1,'admin@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(2,'ioana.popescu@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(3,'dumitru.sterpu@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(4,'ucounihan0@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(5,'hpaxman1@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(6,'bunworth2@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(7,'dsole3@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(8,'dmoulsdale4@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(9,'jjirek5@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL),
(10,'mpincked6@htsg.eu','{bcrypt}$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K',1,NULL,NULL);

/* JOIN TABLE for MANY-TO-MANY relationship*/  
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
    `id_roles` INT(11) NOT NULL AUTO_INCREMENT,
    `user_roles` VARCHAR(50) NOT NULL UNIQUE,
	PRIMARY KEY(`id_roles`)
)ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `roles` 
VALUES 
(1,'ROLE_EMPLOYEE'),
(2,'ROLE_MANAGER'),
(3,'ROLE_ADMIN');

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` int(11) NOT NULL,
  `authority` int(11) NOT NULL,
  
  PRIMARY KEY (`username`,`authority`),
  KEY `authority` (`authority`),
  
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`id_user`),
  CONSTRAINT `authorities_ibfk_2` FOREIGN KEY (`authority`) REFERENCES `roles` (`id_roles`)

  
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;
SET FOREIGN_KEY_CHECKS = 1;

--
-- Dumping data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
(1,3),
(2,1),
(3,1),
(3,2),
(4,1),
(5,1),
(6,1),
(7,1),
(8,1),
(9,1),
(10,1);