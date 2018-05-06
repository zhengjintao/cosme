CREATE DATABASE  IF NOT EXISTS `cosme` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cosme`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: cosme
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cdata_good_add`
--

DROP TABLE IF EXISTS `cdata_good_add`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_good_add` (
  `goodscode` varchar(50) NOT NULL,
  `visitcount` int(11) DEFAULT NULL,
  PRIMARY KEY (`goodscode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_good_add`
--

LOCK TABLES `cdata_good_add` WRITE;
/*!40000 ALTER TABLE `cdata_good_add` DISABLE KEYS */;
/*!40000 ALTER TABLE `cdata_good_add` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cdata_goodsinfo`
--

DROP TABLE IF EXISTS `cdata_goodsinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cdata_goodsinfo` (
  `goodscode` varchar(50) NOT NULL,
  `shopcode` varchar(10) NOT NULL,
  `price` varchar(20) NOT NULL,
  PRIMARY KEY (`goodscode`,`shopcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cdata_goodsinfo`
--

LOCK TABLES `cdata_goodsinfo` WRITE;
/*!40000 ALTER TABLE `cdata_goodsinfo` DISABLE KEYS */;
INSERT INTO `cdata_goodsinfo` VALUES ('0001','00001','199');
/*!40000 ALTER TABLE `cdata_goodsinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmstr_goods`
--

DROP TABLE IF EXISTS `cmstr_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmstr_goods` (
  `goodscode` varchar(50) NOT NULL,
  `goodsname` varchar(200) DEFAULT NULL,
  `imgurl` longtext,
  PRIMARY KEY (`goodscode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmstr_goods`
--

LOCK TABLES `cmstr_goods` WRITE;
/*!40000 ALTER TABLE `cmstr_goods` DISABLE KEYS */;
INSERT INTO `cmstr_goods` VALUES ('0001','おいしい弁当','asserts/images/lunch.jpeg'),('4936201100941','马油','asserts/images/lunch.jpeg');
/*!40000 ALTER TABLE `cmstr_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cmstr_shop`
--

DROP TABLE IF EXISTS `cmstr_shop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmstr_shop` (
  `shopcode` varchar(10) NOT NULL,
  `shopname` varchar(200) NOT NULL,
  PRIMARY KEY (`shopcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cmstr_shop`
--

LOCK TABLES `cmstr_shop` WRITE;
/*!40000 ALTER TABLE `cmstr_shop` DISABLE KEYS */;
INSERT INTO `cmstr_shop` VALUES ('00001','松本清上野店');
/*!40000 ALTER TABLE `cmstr_shop` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-06 12:52:33
