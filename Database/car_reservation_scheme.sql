-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.9-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.5.0.5293
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for car_reservation
DROP DATABASE IF EXISTS `car_reservation`;
CREATE DATABASE IF NOT EXISTS `car_reservation` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_croatian_ci */;
USE `car_reservation`;

-- Dumping structure for table car_reservation.car
CREATE TABLE IF NOT EXISTS `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `model_id` int(11) NOT NULL,
  `location_id` int(11) NOT NULL,
  `plate_number` varchar(10) COLLATE utf8mb4_croatian_ci NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_CAR_MODEL` (`model_id`),
  KEY `FK_CAR_LOCATION` (`location_id`),
  CONSTRAINT `FK_CAR_LOCATION` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_CAR_MODEL` FOREIGN KEY (`model_id`) REFERENCES `model` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `logo` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.cost
CREATE TABLE IF NOT EXISTS `cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.location
CREATE TABLE IF NOT EXISTS `location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  `address` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `company_id` int(11) NOT NULL,
  `latitude` decimal(10,6) NOT NULL,
  `longitude` decimal(10,6) NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_LOCATION_COMPANY` (`company_id`),
  CONSTRAINT `FK_LOCATION_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.logger
CREATE TABLE IF NOT EXISTS `logger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(128) COLLATE utf8mb4_croatian_ci NOT NULL,
  `action_details` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `table_name` varchar(128) COLLATE utf8mb4_croatian_ci NOT NULL,
  `created` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `user_id` int(11) NOT NULL,
  `atomic` tinyint(4) NOT NULL,
  `company_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_LOGGER_USER` (`user_id`),
  KEY `FK_LOGGER_COMPANY` (`company_id`),
  CONSTRAINT `FK_LOGGER_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_LOGGER_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.mail_option
CREATE TABLE IF NOT EXISTS `mail_option` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `option` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.manufacturer
CREATE TABLE IF NOT EXISTS `manufacturer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `logo` longblob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.model
CREATE TABLE IF NOT EXISTS `model` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manufacturer` int(11) NOT NULL,
  `model` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `class` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `body_style` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `engine` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `transmission` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `year` char(4) COLLATE utf8mb4_croatian_ci NOT NULL,
  `fuel` varchar(50) COLLATE utf8mb4_croatian_ci NOT NULL,
  `image` blob NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_MODEL_MANUFACTURER` (`manufacturer`),
  CONSTRAINT `FK_MODEL_MANUFACTURER` FOREIGN KEY (`manufacturer`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.reservation
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `user_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `start_mileage` int(11) NOT NULL,
  `finish_mileage` int(11) DEFAULT NULL,
  `direction` varchar(150) COLLATE utf8mb4_croatian_ci NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_RESERVATION_USER` (`user_id`),
  KEY `FK_RESERVATION_CAR` (`car_id`),
  CONSTRAINT `FK_RESERVATION_CAR` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FK_RESERVATION_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.running_cost
CREATE TABLE IF NOT EXISTS `running_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `datetime` timestamp NOT NULL DEFAULT current_timestamp(),
  `price` decimal(10,2) NOT NULL,
  `description` text COLLATE utf8mb4_croatian_ci NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `FK_RUNNING_COST_COST` (`cost_id`),
  KEY `FK_RUNNING_COST_CAR` (`car_id`),
  CONSTRAINT `FK_RUNNING_COST_CAR` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`),
  CONSTRAINT `FK_RUNNING_COST_COST` FOREIGN KEY (`cost_id`) REFERENCES `cost` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
-- Dumping structure for table car_reservation.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_croatian_ci NOT NULL,
  `username` varchar(80) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `password` char(128) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 0,
  `deleted` tinyint(4) NOT NULL DEFAULT 0,
  `mail_option_id` int(11) NOT NULL,
  `location_id` int(11) DEFAULT NULL,
  `company_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `token` char(10) COLLATE utf8mb4_croatian_ci DEFAULT NULL,
  `token_valid_until` datetime DEFAULT NULL,
  `avatar` longblob DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `FK_USER_ROLE` (`role_id`),
  KEY `FK_USER_COMPANY` (`company_id`),
  KEY `FK_USER_MAIL_OPTION` (`mail_option_id`),
  KEY `FK_USER_LOCATION` (`location_id`),
  CONSTRAINT `FK_USER_COMPANY` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_USER_LOCATION` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `FK_USER_MAIL_OPTION` FOREIGN KEY (`mail_option_id`) REFERENCES `mail_option` (`id`),
  CONSTRAINT `FK_USER_ROLE` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_croatian_ci;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
