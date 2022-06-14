SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema expleodb
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `expleodb` ;
CREATE DATABASE IF NOT EXISTS `expleodb` DEFAULT CHARACTER SET utf8 COLLATE utf8_romanian_ci ;
USE `expleodb` ;

-- -----------------------------------------------------
-- Table `expleodb`.`proiect`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`proiect` (
  `ID_Proiect` INT(11) NOT NULL AUTO_INCREMENT,
  `Nume_proiect` VARCHAR(45) NOT NULL,
  `Cod` VARCHAR(15) NOT NULL UNIQUE,
  `Manager_email` VARCHAR(60),
  PRIMARY KEY (`ID_Proiect`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `proiect` VALUES
	(1,'Project1','H0001', null),
	(2,'Project2','H0002', null),
	(3,'Project3','H0003', 'dumitru.sterpu@htsg.eu'),
	(4,'Project4','H0004', null),
	(5,'Project5','H0005', null),
	(6,'Project6','H0006', null),
	(7,'Project7','H0007', null),
	(8,'Project8','H0008', null),
	(9,'Project9','H0009', null),
	(10,'Project10','H0010', null);



-- -----------------------------------------------------
-- Table `expleodb`.`skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`skill` (
  `ID_skill` INT(11) NOT NULL AUTO_INCREMENT,
  `Nume_skill` VARCHAR(45) NOT NULL,
  `Categorie` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_skill`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `skill` VALUES
	(1,'Java','Programming'),
	(2,'C','Programming'),
	(3,'C++','Programming'),
	(4,'Python','Programming'),
	(5,'Microcontrollers','Embedded'),
	(6,'Microprocessors','Embedded'),
	(7,'Cloud','DevOps'),
	(8,'Automation','DevOps'),
	(9,'Coaching and mentoring','Management'),
	(10,'Conflict resolution','Management');


-- -----------------------------------------------------
-- Table `expleodb`.`proiect_skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`proiect_skill` (
  `ID_skill` INT(11) NOT NULL,
  `ID_proiect` INT(11) NOT NULL,
  `Pondere` INT(2) UNSIGNED NOT NULL DEFAULT 1,
  `Target` INT(1) UNSIGNED NOT NULL DEFAULT 1,
  INDEX `FK_proiect_skill_skill` (`ID_skill` ASC),
  INDEX `FK_proiect_skill_proiect` (`ID_proiect` ASC),
  CONSTRAINT `FK_proiect_skill_proiect`
    FOREIGN KEY (`ID_proiect`)
    REFERENCES `expleodb`.`proiect` (`ID_Proiect`),
  CONSTRAINT `FK_proiect_skill_skill`
    FOREIGN KEY (`ID_skill`)
    REFERENCES `expleodb`.`skill` (`ID_skill`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `proiect_skill` VALUES
(1,1,2,3),
(2,1,2,3),
(3,1,2,3),
(4,1,2,3),
(5,1,2,3),
(5,2,2,3),
(1,3,2,3),
(4,3,1,3),
(7,3,2,3),
(9,3,2,3);


-- -----------------------------------------------------
-- Table `expleodb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`user` (
  `ID_user` INT(11) NOT NULL AUTO_INCREMENT,
  `Nume_user` VARCHAR(20) NOT NULL,
  `Prenume_user` VARCHAR(40) NOT NULL,
  `Numar_matricol` INT(3) NOT NULL UNIQUE,
  `Email` VARCHAR(60) NOT NULL UNIQUE,
  `Data_angajare` DATE NOT NULL,
  `Functie` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`ID_user`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `user` VALUES
(1,'Admin','Admin','0000','admin@htsg.eu','2019-11-18','Admin'),
(2,'Smith','John','5428','ioana.popescu@htsg.eu','2019-07-26','Angajat'),
(3,'Sterpu','Cristian','1823','dumitru.sterpu@htsg.eu','2010-02-13','LT'),
(4,'Counihan','Ulrica','4551','ucounihan0@htsg.eu','2010-02-08','Angajat'),
(5,'Paxman','Hillary','1058','hpaxman1@htsg.eu','2010-04-20','Angajat'),
(6,'Unworth','Bibby','1012','bunworth2@htsg.eu','2020-04-20','Angajat'),
(7,'Sole','Dolf','4911','dsole3@htsg.eu','2010-04-20','Angajat'),
(8,'Moulsdale','Dacey','6756','dmoulsdale4@htsg.eu','2000-04-20','Angajat'),
(9,'Jirek','Jacqui','0583','jjirek5@htsg.eu','2022-04-20','Angajat'),
(10,'Pincked','Mirna','5228','mpincked6@htsg.eu','2021-04-20','Angajat');





-- -----------------------------------------------------
-- Table `expleodb`.`user_proiect`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`user_proiect` (
  `ID_user` INT(11) NOT NULL,
  `ID_proiect` INT(11) NOT NULL,
  INDEX `FK_user_proiect_proiect` (`ID_proiect` ASC),
  INDEX `FK_user_proiect_user` (`ID_user` ASC),
  CONSTRAINT `FK_user_proiect_proiect`
    FOREIGN KEY (`ID_proiect`)
    REFERENCES `expleodb`.`proiect` (`ID_Proiect`),
  CONSTRAINT `FK_user_proiect_user`
    FOREIGN KEY (`ID_user`)
    REFERENCES `expleodb`.`user` (`ID_user`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `user_proiect` VALUES
(3,3),
(4,3),
(5,3),
(6,3),
(7,3),
(5,2),
(8,5),
(6,6),
(3,8),
(2,2);


-- -----------------------------------------------------
-- Table `expleodb`.`user_skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `expleodb`.`user_skill` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `id_user` INT(11) NOT NULL,
  `id_skill` INT(11) NOT NULL,
  `evaluare` INT(1) UNSIGNED NOT NULL,
  `data_evaluare` DATE NOT NULL,

  INDEX `FK_user_skill_user` (`id_user` ASC),
  INDEX `FK_user_skill_skill` (`id_skill` ASC),
  CONSTRAINT `FK_user_skill_skill`
    FOREIGN KEY (`id_skill`)
    REFERENCES `expleodb`.`skill` (`ID_skill`),
  CONSTRAINT `FK_user_skill_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `expleodb`.`user` (`ID_user`),
    CONSTRAINT `clustered_index`
    UNIQUE(`id_user`, `id_skill`),

      PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

INSERT INTO `user_skill` VALUES
                             (5,3,1, 2,'2021-09-12'),
                             (6,3,4, 2,'2021-09-12'),
                             (7,3,7, 2,'2021-09-12'),
                             (8,3,9, 2,'2021-09-12'),
                             (13,4,1, 2,'2021-07-12'),
                             (14,4,4, 2,'2021-07-12'),
                             (15,4,7, 2,'2021-07-12'),
                             (16,4,9, 2,'2021-07-12'),
                             (17,5,1, 2,'2021-07-12'),
                             (18,5,4, 2,'2021-07-12'),
                             (19,5,7, 2,'2021-07-12'),
                             (20,5,9, 2,'2021-07-12');


-- -----------------------------------------------------
-- Table `expleodb`.`history`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `expleodb`.`history` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `id_user` INT(11) NOT NULL,
    `id_skill` INT(11) NOT NULL,
    `evaluare` INT(1) UNSIGNED NOT NULL,
    `data_evaluare` DATE NOT NULL,

    INDEX `FK_id_user` (`id_user` ASC),
    INDEX `FK_id_skill` (`id_skill` ASC),

    CONSTRAINT `FK_id_user`
    FOREIGN KEY (`id_user`)
    REFERENCES `expleodb`.`user` (`ID_user`),
    CONSTRAINT `FK_id_skill`
    FOREIGN KEY (`id_skill`)
    REFERENCES `expleodb`.`skill` (`ID_skill`),

    PRIMARY KEY (`id`),
    KEY (`id_user`),
    KEY (`id_skill`))

    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8
    COLLATE = utf8_romanian_ci;

INSERT INTO `history` VALUES
(1,3,1, 1,'2021-07-12'),
(2,3,4, 1,'2021-07-12'),
(3,3,7, 1,'2021-07-12'),
(4,3,9, 1,'2021-07-12'),
(5,3,1, 2,'2021-09-12'),
(6,3,4, 2,'2021-09-12'),
(7,3,7, 2,'2021-09-12'),
(8,3,9, 2,'2021-09-12'),
(9,4,1, 1,'2021-06-12'),
(10,4,4, 1,'2021-06-12'),
(11,4,7, 1,'2021-06-12'),
(12,4,9, 1,'2021-06-12'),
(13,5,1, 1,'2021-06-12'),
(14,5,4, 1,'2021-06-12'),
(15,5,7, 1,'2021-06-12'),
(16,5,9, 1,'2021-06-12'),
(17,4,1, 2,'2021-07-12'),
(18,4,4, 2,'2021-07-12'),
(19,4,7, 2,'2021-07-12'),
(20,4,9, 2,'2021-07-12'),
(21,5,1, 2,'2021-07-12'),
(22,5,4, 2,'2021-07-12'),
(23,5,7, 2,'2021-07-12'),
(24,5,9, 2,'2021-07-12');

-- -----------------------------------------------------
-- Table `expleodb`.`record`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `expleodb`.`record` (
  `ID_record` INT(11) NOT NULL AUTO_INCREMENT,
  `id_autor`INT(11) NOT NULL,
  `Categorie` VARCHAR(60) NOT NULL,
  `Titlu` VARCHAR(400) NOT NULL,
  `Descriere` VARCHAR(10000) NOT NULL,
  `Autor`VARCHAR(60) NOT NULL,

  INDEX `FK_Id_autor` (`Id_autor` ASC),

  CONSTRAINT `FK_id_autor`
  FOREIGN KEY (`id_autor`)
  REFERENCES `expleodb`.`user` (`ID_user`),

  PRIMARY KEY (`ID_record`),
  KEY (`id_autor`))

ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

-- -----------------------------------------------------
-- Table `expleodb`.`solution`
-- -----------------------------------------------------


  CREATE TABLE IF NOT EXISTS `expleodb`.`solution` (
  `ID_solution` INT(11) NOT NULL AUTO_INCREMENT,
  `id_record` INT(11) NOT NULL,
  `id_user` INT(11) NOT NULL,
  `Solutie` LONGTEXT NOT NULL,
  `Data` VARCHAR(30) NOT NULL,
  `Data_update` VARCHAR(30),

  INDEX `FK_id_record` (`id_record` ASC),
  INDEX `FK_id_user_solution` (`id_user` ASC),

  CONSTRAINT `FK_id_record`
  FOREIGN KEY (`id_record`)
  REFERENCES `expleodb`.`record` (`ID_record`),

  CONSTRAINT `FK_id_user_solution`
  FOREIGN KEY (`id_user`)
  REFERENCES `expleodb`.`user` (`ID_user`),

  PRIMARY KEY (`ID_solution`),
  KEY (`id_record`),
  KEY(`id_user`))

ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;