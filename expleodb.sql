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
	(1,'webCM','I1030', null),
	(2,'Plastice','P1056', null),
	(3,'Motoare','P1020', null);



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
	(1,'Java','Programare'),
	(2,'C','Programare'),
	(3,'C++','Programare'),
	(4,'Python','Programare'),
	(5,'Catia V5','Proiectare'),
	(6,'Plastice','Proiectare'),
	(7,'Motoare','Proiectare'),
	(8,'Drawings','Proiectare'),
	(9,'Motivare','Management'),
	(10,'Luarea deciziilor','Management');


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
	(1,'Milea','Ovidiu','3465','ovidiu-marian.milea@expleogroup.com','2017-11-20','Angajat'),
	(2,'Rusu','Tiberiu','5421','tiberiu.rusu@expleogroup.com','2019-07-20','Angajat'),
	(3,'Baldovin','Virgil-Florentin','5428','virgil-florentin.baldovin@expleogroup.com','2019-07-26','Angajat'),
	(4,'Sterpu','Cristian','1823','dumitru-cristian.sterpu@expleogroup.com','2010-02-13','LT'),
	(5,'Admin','Admin','0000','admin@expleogroup.com','2019-11-18','Admin');

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

      PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_romanian_ci;

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

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;