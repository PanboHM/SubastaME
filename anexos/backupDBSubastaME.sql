CREATE DATABASE  IF NOT EXISTS `subastas` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish_ci */;
USE `subastas`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: subastas
-- ------------------------------------------------------
-- Server version	5.7.20-log

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
-- Table structure for table `articulos`
--

DROP TABLE IF EXISTS `articulos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `articulos` (
  `idArticulo` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `descripcionCorta` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `descripcion` text COLLATE utf8_spanish_ci,
  `idCategoria` tinyint(3) unsigned DEFAULT NULL,
  `idCliente` int(10) unsigned DEFAULT NULL,
  `fechaInicio` datetime NOT NULL,
  `fechaFin` datetime NOT NULL,
  `importeSalida` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idArticulo`),
  KEY `FK_articulos_clientes_idx` (`idCliente`),
  KEY `FK_articulos_categorias_idx` (`idCategoria`),
  CONSTRAINT `FK_articulos_categorias` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `FK_articulos_clientes` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulos`
--

LOCK TABLES `articulos` WRITE;
/*!40000 ALTER TABLE `articulos` DISABLE KEYS */;
INSERT INTO `articulos` VALUES (10,'Barco de vela','Barco de vela del siglo XX, en muy buen estado.',1,24,'2017-12-21 10:43:00','2018-01-18 22:22:00',80000.00),(11,'Barco a vapor','Barco a vapor del siglo XI por lo menos, una auténtica pieza de museo, no se envía, tienes que venir a por el.',1,24,'2017-12-21 11:00:00','2018-01-18 22:21:00',650000.00),(12,'Trirreme','Barco de remos trirreme de los griegos, incluye un montón de remos.\r\nNO INCLUYE ESCLAVOS.',1,24,'2017-12-21 11:00:00','2018-01-16 22:22:00',750000.00),(13,'Lancha hinchable','Lancha hinchable del Decathlon, se vende por no tener uso, como nueva.',1,24,'2017-12-21 11:15:00','2018-01-16 12:12:00',150.00),(14,'Mesa de salón','Mesa de madera robusta',3,23,'2017-12-21 11:00:00','2018-01-17 15:00:00',50.00),(15,'Sillas de restaurante','3 sillas de restaurante a precio de oferta, usadas por los clientes mas VIPS',3,23,'2017-12-21 11:11:00','2018-03-01 21:21:00',55.00),(16,'Estanteria modular','Estanteria modular de diseño moderno para colocar cosas.',3,23,'2017-12-21 11:00:00','2018-03-16 17:00:00',100.00),(17,'Placa solar','Placa solar para el sol, dar energía a tu casa, 100% REAL NO FAKE.',4,23,'2017-12-21 11:00:00','2018-04-12 12:12:00',500.00),(18,'Cacahuetes','Puñados de cacahuetes de excelente calidad, aún sin masticar.',4,23,'2017-12-21 11:00:00','2018-02-22 08:08:00',2.00),(19,'Duplex bonito','Duplex en urbanización de lujo, muy lujoso.',2,23,'2017-12-21 11:00:00','2018-04-07 22:22:00',120000.00),(20,'Casa','Casa de madera, típica que vuela con el primer huracán que pasa.',2,23,'2017-12-21 11:00:00','2018-04-17 15:15:00',45000.00);
/*!40000 ALTER TABLE `articulos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caracteristicas`
--

DROP TABLE IF EXISTS `caracteristicas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caracteristicas` (
  `idCaracteristica` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `idCategoria` tinyint(3) unsigned NOT NULL,
  `denominacion` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idCaracteristica`),
  KEY `FK_caracteristicas_categoria_idx` (`idCategoria`),
  CONSTRAINT `FK_caracteristicas_categorias` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caracteristicas`
--

LOCK TABLES `caracteristicas` WRITE;
/*!40000 ALTER TABLE `caracteristicas` DISABLE KEYS */;
INSERT INTO `caracteristicas` VALUES (1,1,'Material'),(2,1,'Eslora'),(4,2,'Tipo vivienda'),(5,2,'Material de construcción'),(6,2,'Antiguedad'),(7,2,'Numero de habitaciones'),(9,3,'Color'),(10,3,'Material/es'),(11,3,'Medidas(L*A*P)'),(12,4,'Material'),(15,6,'Motores'),(16,6,'Color');
/*!40000 ALTER TABLE `caracteristicas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `caracyart`
--

DROP TABLE IF EXISTS `caracyart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `caracyart` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `idArticulo` smallint(5) unsigned NOT NULL,
  `idCaracteristica` tinyint(3) unsigned NOT NULL,
  `valor` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_caracyart_articulos_idx` (`idArticulo`),
  KEY `FK_caracyart_caracteristicas_idx` (`idCaracteristica`),
  CONSTRAINT `FK_caracyart_articulos` FOREIGN KEY (`idArticulo`) REFERENCES `articulos` (`idArticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_caracyart_caracteristicas` FOREIGN KEY (`idCaracteristica`) REFERENCES `caracteristicas` (`idCaracteristica`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `caracyart`
--

LOCK TABLES `caracyart` WRITE;
/*!40000 ALTER TABLE `caracyart` DISABLE KEYS */;
INSERT INTO `caracyart` VALUES (13,10,1,'Madera contrachapada'),(14,10,2,'20 metros'),(15,11,1,'Hierro puro'),(16,11,2,'200 metros'),(17,12,1,'Madera noble'),(18,12,2,'150 metros'),(19,13,1,'Plastico ABS'),(20,13,2,'2 metros'),(21,14,9,'Marron'),(22,14,10,'Madera de pino'),(23,14,11,'12*15*7'),(24,15,9,'Multicolor'),(25,15,10,'Madera y tela'),(26,15,11,'5*5*8'),(27,16,9,'Multicolor'),(28,16,10,'Madera y plastico'),(29,16,11,'100*15*22'),(30,17,12,'Del sol'),(31,18,12,'Cacahuete'),(32,19,4,'Duplex'),(33,19,5,'Hormiguón'),(34,19,6,'2015'),(35,19,7,'5'),(36,20,4,'Casa'),(37,20,5,'Madera Volátil'),(38,20,6,'1999'),(39,20,7,'6');
/*!40000 ALTER TABLE `caracyart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `categorias` (
  `idCategoria` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `denominacion` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `imagen` varchar(40) COLLATE utf8_spanish_ci DEFAULT 'sin.jpg',
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'Barcos','barcos.png'),(2,'Viviendas','viviendas.png'),(3,'Muebles','muebles.png'),(4,'Otros','sin.jpg'),(6,'Aviones','aviones.png');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `idCliente` int(10) unsigned NOT NULL,
  `nombre` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `apellido1` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `apellido2` varchar(20) COLLATE utf8_spanish_ci DEFAULT NULL,
  `nif` varchar(9) COLLATE utf8_spanish_ci NOT NULL,
  `direccion` varchar(45) COLLATE utf8_spanish_ci NOT NULL,
  `telefono` varchar(9) COLLATE utf8_spanish_ci DEFAULT NULL,
  `avatar` varchar(100) COLLATE utf8_spanish_ci NOT NULL DEFAULT 'sin.jpg',
  PRIMARY KEY (`idCliente`),
  CONSTRAINT `FK_clientes_usuarios` FOREIGN KEY (`idCliente`) REFERENCES `usuarios` (`idUsuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (23,'Jesús','Hernández','Mateos','12345678A','Una calle en Guareña o no','612123123','23_ralph.jpg'),(24,'Juan','Fernández','Hernández','12345678A','Calle Falsa 123',NULL,'24_ralph.jpg'),(25,'Admin','Admin','Admin','12345678A','Calle de los admin','612123123','sin.jpg');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fotografias`
--

DROP TABLE IF EXISTS `fotografias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fotografias` (
  `idFotografia` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idArticulo` smallint(5) unsigned NOT NULL,
  `fotografia` varchar(40) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`idFotografia`),
  KEY `FK_fotografias_articulos_idx` (`idArticulo`),
  CONSTRAINT `FK_fotografias_articulos` FOREIGN KEY (`idArticulo`) REFERENCES `articulos` (`idArticulo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fotografias`
--

LOCK TABLES `fotografias` WRITE;
/*!40000 ALTER TABLE `fotografias` DISABLE KEYS */;
INSERT INTO `fotografias` VALUES (5,10,'partes-de-un-barco-de-vela.jpg'),(6,11,'barco-de-vapor.JPG'),(7,12,'trirreme.jpg'),(8,13,'lancha-hinchable.jpg'),(9,14,'mesa.jpg'),(10,15,'sillas.jpg'),(11,16,'estanteria 2.jpg'),(12,16,'estanteria.jpg'),(13,16,'estanteria3.jpg'),(14,17,'placa_solar.jpg'),(15,18,'cacahuetes-00.jpg'),(16,19,'dpulex.jpg'),(17,20,'casa.jpg');
/*!40000 ALTER TABLE `fotografias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pujas`
--

DROP TABLE IF EXISTS `pujas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pujas` (
  `idCliente` int(10) unsigned NOT NULL,
  `idArticulo` smallint(5) unsigned NOT NULL,
  `fecha` datetime NOT NULL,
  `importe` decimal(8,2) NOT NULL,
  PRIMARY KEY (`idCliente`,`idArticulo`,`fecha`,`importe`),
  KEY `pujas-articulos_idx` (`idArticulo`),
  CONSTRAINT `FK_pujas_articulos` FOREIGN KEY (`idArticulo`) REFERENCES `articulos` (`idArticulo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_pujas_clientes` FOREIGN KEY (`idCliente`) REFERENCES `clientes` (`idCliente`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pujas`
--

LOCK TABLES `pujas` WRITE;
/*!40000 ALTER TABLE `pujas` DISABLE KEYS */;
INSERT INTO `pujas` VALUES (23,10,'2017-12-26 20:42:44',900000.00),(23,10,'2017-12-26 20:43:11',900001.00),(23,10,'2017-12-27 21:42:56',900006.00),(23,10,'2017-12-27 21:43:01',900016.00),(23,10,'2017-12-27 22:00:50',900021.00),(23,10,'2017-12-27 22:10:44',910000.00),(23,11,'2017-12-28 20:41:27',650005.00),(23,11,'2017-12-28 21:12:48',650006.00),(23,12,'2017-12-29 20:22:54',750005.00),(23,12,'2017-12-29 20:26:40',750010.00);
/*!40000 ALTER TABLE `pujas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `idUsuario` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `ultimoAcceso` datetime NOT NULL,
  `tipoAcceso` set('a','u') COLLATE utf8_spanish_ci NOT NULL DEFAULT 'u',
  `bloqueado` set('s','n') COLLATE utf8_spanish_ci NOT NULL DEFAULT 'n',
  `valorPositivas` smallint(5) unsigned DEFAULT NULL,
  `valorNegativas` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`idUsuario`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (23,'jesush_panbo@hotmail.com','5074d5ec062ba97541d56c0cddd2e929112720e7','2018-01-07 22:47:56','u','n',NULL,NULL),(24,'juan@hotmail.com','5074d5ec062ba97541d56c0cddd2e929112720e7','2018-01-03 11:13:11','u','n',NULL,NULL),(25,'admin@admin.es','d033e22ae348aeb5660fc2140aec35850c4da997','2018-01-07 13:58:54','a','n',NULL,NULL);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'subastas'
--

--
-- Dumping routines for database 'subastas'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-08 11:08:07
