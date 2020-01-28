CREATE DATABASE  IF NOT EXISTS `coffeedb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `coffeedb`;
-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: coffeedb
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `coffee`
--

DROP TABLE IF EXISTS `coffee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coffee` (
  `Description` char(25) DEFAULT NULL,
  `ProdNum` char(10) NOT NULL,
  `Price` double DEFAULT NULL,
  PRIMARY KEY (`ProdNum`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coffee`
--

LOCK TABLES `coffee` WRITE;
/*!40000 ALTER TABLE `coffee` DISABLE KEYS */;
INSERT INTO `coffee` VALUES ('Bolivian Dark','14-001',8.95),('Bolivian Medium','14-002',8.95),('Brazilian Dark','15-001',7.95),('Brazilian Medium','15-002',7.95),('Brazilian Decaf','15-003',8.55),('Central American Dark','16-001',9.95),('Central American Medium','16-002',9.95),('Sumatra Dark','17-001',7.95),('Sumatra Decaf','17-002',8.95),('Sumatra Medium','17-003',7.95),('Sumatra Organic Dark','17-004',11.95),('Kona Medium','18-001',18.45),('Kona Dark','18-002',18.45),('French Roast Dark','19-001',9.65),('Galapagos Medium','20-001',6.85),('Guatemalan Dark','21-001',9.95),('Guatemalan Decaf','21-002',10.45),('Guatemalan Medium','21-003',9.95);
/*!40000 ALTER TABLE `coffee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `CustomerNumber` char(10) NOT NULL,
  `Name` char(25) DEFAULT NULL,
  `Address` char(25) DEFAULT NULL,
  `City` char(12) DEFAULT NULL,
  `State` char(2) DEFAULT NULL,
  `Zip` char(5) DEFAULT NULL,
  PRIMARY KEY (`CustomerNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('101','Downtown Cafe','17 N. Main Street','Asheville','NC','55515'),('102','Main Street Grocery','110 E. Main Street','Canton','NC','55555'),('103','The Coffee Place','101 Center Plaza','Waynesville','NC','55516');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unpaidorder`
--

DROP TABLE IF EXISTS `unpaidorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `unpaidorder` (
  `CustomerNumber` char(10) NOT NULL,
  `ProdNum` char(10) NOT NULL,
  `OrderDate` char(10) DEFAULT NULL,
  `Quantity` double DEFAULT NULL,
  `Cost` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unpaidorder`
--

LOCK TABLES `unpaidorder` WRITE;
/*!40000 ALTER TABLE `unpaidorder` DISABLE KEYS */;
/*!40000 ALTER TABLE `unpaidorder` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-09  9:57:12
