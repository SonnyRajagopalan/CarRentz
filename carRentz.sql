-- Created from show create table *

CREATE TABLE `rentals` (
  `rentalID` bigint NOT NULL AUTO_INCREMENT,
  `carID` bigint NOT NULL,
  `carType` varchar(255) NOT NULL,
  `duration` int NOT NULL,
  `rentalDate` datetime NOT NULL,
  `returnDate` datetime NOT NULL,
  `customerID` varchar(255) NOT NULL,
  `expectedCost` float NOT NULL, -- inconsistent naming
  `actualCharges` float NOT NULL, -- inconsistent naming
  PRIMARY KEY (`rentalID`),
  UNIQUE KEY `rentalID` (`rentalID`),
  UNIQUE KEY `carID` (`carID`),
  CONSTRAINT `rentals_ibfk_1` FOREIGN KEY (`carID`) REFERENCES `inventory` (`carID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



CREATE TABLE `inventory` (
  `carID` bigint NOT NULL AUTO_INCREMENT,
  `carType` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  `milesDriven` float NOT NULL,
  `year` int NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `pricePerDay` float NOT NULL,
  `available` tinyint(1) NOT NULL,
  `car_type` varchar(255) DEFAULT NULL, --- this is not needed--was created by mistake
  `miles_driven` float NOT NULL, -- weird naming convention. my bad.
  `price_per_day` float NOT NULL, -- weird naming convention. my bad.
  PRIMARY KEY (`carID`),
  UNIQUE KEY `carID` (`carID`),
  KEY `carTypeIdx` (`carType`),
  KEY `modelIdx` (`model`),
  KEY `makeIdx` (`make`),
  KEY `yearIdx` (`year`),
  KEY `availableIdx` (`available`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ;


