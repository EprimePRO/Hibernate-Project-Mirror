-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema simple_company
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `simple_company` ;

-- -----------------------------------------------------
-- Table `simple_company`.`customer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`customer` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `gender` CHAR(1) NOT NULL,
  `dob` DATE NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `simple_company`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`address` (
  `id` INT(11) NOT NULL,
  `address1` VARCHAR(45) NOT NULL,
  `address2` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  CONSTRAINT `ADDRESS`
    FOREIGN KEY (`id`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `simple_company`.`creditcard`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`creditcard` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `ccNumber` VARCHAR(45) NOT NULL,
  `expDate` VARCHAR(45) NOT NULL,
  `securityCode` VARCHAR(45) NOT NULL,
  CONSTRAINT `CREDITCARD`
    FOREIGN KEY (`id`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `simple_company`.`product`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`product` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `category` INT(11) NOT NULL,
  `upc` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `simple_company`.`purchase`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `simple_company`.`purchase` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `productID` INT(11) NOT NULL,
  `customerID` INT(11) NOT NULL,
  `purchaseDate` DATE NOT NULL,
  `purchaseAmt` FLOAT NOT NULL,
  PRIMARY KEY (`id`, `customerID`, `productID`),
  INDEX `customerID_idx` (`customerID` ASC) VISIBLE,
  INDEX `productID_idx` (`productID` ASC) VISIBLE,
  CONSTRAINT `customerID`
    FOREIGN KEY (`customerID`)
    REFERENCES `simple_company`.`customer` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `productID`
    FOREIGN KEY (`productID`)
    REFERENCES `simple_company`.`product` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
