-- MySQL dump 10.13  Distrib 8.0.29, for Linux (x86_64)
--
-- Host: localhost    Database: LibrarySystemDB
-- ------------------------------------------------------
-- Server version	8.0.29-0ubuntu0.22.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `BookID` int NOT NULL AUTO_INCREMENT,
  `BookName` varchar(45) NOT NULL,
  `BookAuthor` varchar(45) NOT NULL,
  `BookCategory` varchar(45) NOT NULL,
  PRIMARY KEY (`BookID`),
  UNIQUE KEY `BookID_UNIQUE` (`BookID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Madol Duwa','Martin Wickramasinghe','Novel'),(5,'Sherlock Holmes','Chandana Mendis','Novel'),(6,'Madol Duwa','Martin Wickrmasinghe','Novel'),(7,'WDK Book1','Kavindra Weerasinghe','Novel'),(8,'WDK Book2','Kavindra Weerasinghe','Novel'),(9,'Moda Tilin','Thilni Priyangika','Life Story'),(10,'Happy Life','Suvi','Life Education'),(11,'Mentell Illness','Suvi','Science'),(12,'How to be Innocient','Thilni','Biology'),(13,'How to find girl friend','Romesh','Biology'),(14,'Happy Married Life','Thilni Adikari','Life lessons'),(15,'Life near to Sed','Helitha','Documentry');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book_issue`
--

DROP TABLE IF EXISTS `book_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_issue` (
  `id` int NOT NULL AUTO_INCREMENT,
  `UserID` int NOT NULL,
  `BookID` int NOT NULL,
  `IssueDate` date NOT NULL,
  `Received` varchar(3) NOT NULL,
  `ReceivedDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_book_issue_1_idx` (`UserID`),
  KEY `fk_issue_book_idx` (`BookID`),
  CONSTRAINT `fk_issue_book` FOREIGN KEY (`BookID`) REFERENCES `book` (`BookID`),
  CONSTRAINT `fk_issue_user` FOREIGN KEY (`UserID`) REFERENCES `user` (`UserID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_issue`
--

LOCK TABLES `book_issue` WRITE;
/*!40000 ALTER TABLE `book_issue` DISABLE KEYS */;
INSERT INTO `book_issue` VALUES (1,1,5,'2022-06-08','No','2022-07-13'),(2,1,6,'2022-07-09','No',NULL),(6,3,7,'2022-07-11','Yes','2022-07-14'),(7,3,8,'2022-07-13','No','2022-07-13'),(8,4,7,'2022-07-16','No',NULL),(9,5,9,'2022-07-16','No',NULL),(10,6,14,'2022-07-16','No',NULL);
/*!40000 ALTER TABLE `book_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `settings` (
  `id` int NOT NULL AUTO_INCREMENT,
  `chargeUnit` decimal(4,2) NOT NULL,
  `issueTime` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idSettings_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` VALUES (4,0.50,14),(5,1.00,14),(6,1.50,14),(7,2.50,14);
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `staff` (
  `StaffID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`StaffID`),
  UNIQUE KEY `staffID_UNIQUE` (`StaffID`),
  UNIQUE KEY `UserName_UNIQUE` (`UserName`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` VALUES (1,'Kavindra','12345'),(6,'Admin','456789');
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `UserID` int NOT NULL AUTO_INCREMENT,
  `UserName` varchar(45) NOT NULL,
  `DOB` date NOT NULL,
  `NIC` varchar(12) NOT NULL,
  `Gender` varchar(8) NOT NULL,
  `ContactNo` varchar(10) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Address` varchar(100) NOT NULL,
  PRIMARY KEY (`UserID`),
  UNIQUE KEY `UserID_UNIQUE` (`UserID`),
  UNIQUE KEY `ContactNo_UNIQUE` (`ContactNo`),
  UNIQUE KEY `NIC_UNIQUE` (`NIC`),
  UNIQUE KEY `Email_UNIQUE` (`Email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Kavindra Weerasinghe','1999-08-27','992401800V','Male','0774743603','wdilshankavindra@gmail.com','galigmuwa town'),(3,'Kavinda Helitha','1999-05-22','991408200V','Male','0776423852','helitha40@gmail.com','colombo'),(4,'Thilini Priyangika','1998-02-25','982562853V','Female','0776582145','thilinipriyangika98@gmail.com','Monaragala'),(5,'Tharindu Romesh','1999-04-02','991234567V','Male','0715685236','tharindu.romesh07@gmail.com','mathara'),(6,'Ishara Suvini','1998-09-05','982234567V','Female','0775485236','suvinilakshani@gmail.com','Madawachchiya');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-07-17 16:45:13
