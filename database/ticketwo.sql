CREATE DATABASE  IF NOT EXISTS `ticketwo` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ticketwo`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: ticketwo
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `biglietti`
--

DROP TABLE IF EXISTS `biglietti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `biglietti` (
  `CodiceUnivoco` varchar(15) NOT NULL,
  `stato` int DEFAULT '0',
  `Cliente_id` int NOT NULL,
  `Evento_id` int NOT NULL,
  PRIMARY KEY (`CodiceUnivoco`),
  UNIQUE KEY `uk_clientid_eventoid` (`Cliente_id`,`Evento_id`),
  UNIQUE KEY `uk_clienteid_eventoid` (`Cliente_id`,`Evento_id`),
  KEY `fk_Biglietti_Utenti1_idx` (`Cliente_id`),
  KEY `fk_Biglietti_eventi_idx` (`Evento_id`),
  CONSTRAINT `fk_Biglietti_eventi` FOREIGN KEY (`Evento_id`) REFERENCES `eventi` (`id`),
  CONSTRAINT `fk_Biglietti_Utenti1` FOREIGN KEY (`Cliente_id`) REFERENCES `utenti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biglietti`
--

LOCK TABLES `biglietti` WRITE;
/*!40000 ALTER TABLE `biglietti` DISABLE KEYS */;
INSERT INTO `biglietti` VALUES ('COR-3E7F8C1A',1,5,2),('COR-4AC9D3B7',1,1,2),('COR-9F1A3D2B',1,3,2),('MON-3E7F8C1A',0,5,3),('MON-4AC9D3B7',1,3,3),('MON-9F1A3D2B',1,4,3);
/*!40000 ALTER TABLE `biglietti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `eventi`
--

DROP TABLE IF EXISTS `eventi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `eventi` (
  `id` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(50) NOT NULL,
  `Descrizione` varchar(150) NOT NULL,
  `Data` date NOT NULL,
  `Orario` time NOT NULL,
  `Capienza` int NOT NULL,
  `Partecipanti` int DEFAULT NULL,
  `Costo` int NOT NULL,
  `Amministratore_id` int NOT NULL,
  `Luogo` varchar(200) NOT NULL,
  PRIMARY KEY (`id`,`Amministratore_id`),
  UNIQUE KEY `Titolo_UNIQUE` (`Titolo`),
  KEY `fk_Eventi_Utenti_idx` (`Amministratore_id`),
  CONSTRAINT `fk_Eventi_Utenti` FOREIGN KEY (`Amministratore_id`) REFERENCES `utenti` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `eventi`
--

LOCK TABLES `eventi` WRITE;
/*!40000 ALTER TABLE `eventi` DISABLE KEYS */;
INSERT INTO `eventi` VALUES (1,'ritiro del Napoli a Castel di Sangro','Ritiro della squadra SSC Napoli a Castel di Sangro','2025-07-25','10:00:00',400,0,50,2,'Castel di Sangro'),(2,'Corso di cucina Italiana','Lezione pratica di cucina regionale','2025-04-10','18:00:00',30,3,20,2,'Napoli'),(3,'Money Gang Tour','Concerto di Sfera Ebbasta allo stadio Diego Armando Maradona','2025-06-07','21:00:00',300,2,50,2,'Napoli'),(4,'Agile O\'Day','Incontro formativo su metodi agili','2025-09-20','14:00:00',100,0,0,2,'Napoli');
/*!40000 ALTER TABLE `eventi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utenti`
--

DROP TABLE IF EXISTS `utenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utenti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(20) NOT NULL,
  `cognome` varchar(30) NOT NULL,
  `password` varchar(40) NOT NULL,
  `ImmagineProfilo` varchar(200) DEFAULT 'prova',
  `email` varchar(45) NOT NULL,
  `Ruolo` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utenti`
--

LOCK TABLES `utenti` WRITE;
/*!40000 ALTER TABLE `utenti` DISABLE KEYS */;
INSERT INTO `utenti` VALUES (1,'Giovanni','Di Lorenzo','ag4in','giovanni.png','giovanni.dilo@gmail.com',0),(2,'Aurelio','De Laurentiis','antonioconte','null','aurelio.dela@gmail.com',1),(3,'Antonio','Conte','maradona123','Antonio.png','antonio.ag4in@gmail.com',0),(4,'Stanislav','Lobotka','napoli1926','stani.png','lobo.maestro68@gmail.com',0),(5,'Scott','McTominay','mazzocchi','scott.png','scott.braveheart@gmail.com',0);
/*!40000 ALTER TABLE `utenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-18 20:01:46
