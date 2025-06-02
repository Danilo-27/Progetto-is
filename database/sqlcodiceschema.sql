-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema ticketwo
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ticketwo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ticketwo` DEFAULT CHARACTER SET utf8 ;
USE `ticketwo` ;

-- -----------------------------------------------------
-- Table `ticketwo`.`Utenti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketwo`.`Utenti` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(20) NOT NULL,
  `cognome` VARCHAR(30) NOT NULL,
  `password` VARCHAR(40) NOT NULL,
  `ImmagineProfilo` VARCHAR(200) NULL DEFAULT 'prova',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketwo`.`Eventi`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketwo`.`Eventi` (
  `id` INT NOT NULL,
  `Titolo` VARCHAR(50) NOT NULL,
  `Descrizione` VARCHAR(150) NOT NULL,
  `Data` DATE NOT NULL,
  `Oraio` TIME NOT NULL,
  `Capienza` INT NOT NULL,
  `Partecipanti` INT NULL,
  `Costo` INT NOT NULL,
  `Amministratore_id` INT NOT NULL,
  PRIMARY KEY (`id`, `Amministratore_id`),
  UNIQUE INDEX `Titolo_UNIQUE` (`Titolo` ASC) VISIBLE,
  INDEX `fk_Eventi_Utenti_idx` (`Amministratore_id` ASC) VISIBLE,
  CONSTRAINT `fk_Eventi_Utenti`
    FOREIGN KEY (`Amministratore_id`)
    REFERENCES `ticketwo`.`Utenti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ticketwo`.`Biglietti`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ticketwo`.`Biglietti` (
  `CodiceUnivoco` VARCHAR(6) NOT NULL,
  `stato` INT NULL DEFAULT 0,
  `Cliente_id` INT NOT NULL,
  `Eventi_id` INT NOT NULL,
  PRIMARY KEY (`CodiceUnivoco`, `Cliente_id`, `Eventi_id`),
  INDEX `fk_Biglietti_Utenti1_idx` (`Cliente_id` ASC) VISIBLE,
  INDEX `fk_Biglietti_Eventi1_idx` (`Eventi_id` ASC) VISIBLE,
  CONSTRAINT `fk_Biglietti_Utenti1`
    FOREIGN KEY (`Cliente_id`)
    REFERENCES `ticketwo`.`Utenti` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Biglietti_Eventi1`
    FOREIGN KEY (`Eventi_id`)
    REFERENCES `ticketwo`.`Eventi` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
