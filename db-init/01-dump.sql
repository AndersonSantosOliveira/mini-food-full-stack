-- MySQL dump 10.13  Distrib 8.4.8, for Linux (x86_64)
--
-- Host: localhost    Database: meubanco
-- ------------------------------------------------------
-- Server version	8.4.8

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `pedido_produto`
--

DROP TABLE IF EXISTS `pedido_produto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido_produto` (
  `pedido_id` bigint NOT NULL,
  `produto_id` bigint NOT NULL,
  KEY `FKb7jn3ooptx8rdw1pr6mjm9p8n` (`produto_id`),
  KEY `FK6dahbmbnsis894l30ts6l45we` (`pedido_id`),
  CONSTRAINT `FK6dahbmbnsis894l30ts6l45we` FOREIGN KEY (`pedido_id`) REFERENCES `tabelapedidos` (`id`),
  CONSTRAINT `FKb7jn3ooptx8rdw1pr6mjm9p8n` FOREIGN KEY (`produto_id`) REFERENCES `tabelaprodutos` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido_produto`
--

LOCK TABLES `pedido_produto` WRITE;
/*!40000 ALTER TABLE `pedido_produto` DISABLE KEYS */;
INSERT INTO `pedido_produto` VALUES (2,8),(2,14),(3,14),(3,8),(4,8),(4,18),(4,19),(5,8),(5,16);
/*!40000 ALTER TABLE `pedido_produto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabelacliente`
--

DROP TABLE IF EXISTS `tabelacliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabelacliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(150) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKc9kgggpjdfevae51yvtth08ca` (`email`),
  UNIQUE KEY `UKle7afc49p2cqrvxlptcwjo573` (`telefone`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabelacliente`
--

LOCK TABLES `tabelacliente` WRITE;
/*!40000 ALTER TABLE `tabelacliente` DISABLE KEYS */;
INSERT INTO `tabelacliente` VALUES (4,'manoelsantos0984@gmail.com','Manoel de jesus reis muniz','98984721700'),(5,'maria@exemplo.com','Maria Souza','98991234567'),(6,'marcos@exemplo.com','Marcos Souza','98991233218'),(7,'mariana@exemplo.com','Mariana Souza','98958733218'),(8,'anavitoria@exemplo.com','Ana Vitoria Souza','98952333248'),(9,'samuel@exemple.com','Samuel Silva Sousa','98 945652314');
/*!40000 ALTER TABLE `tabelacliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabelapedidos`
--

DROP TABLE IF EXISTS `tabelapedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabelapedidos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `valor_total` double NOT NULL,
  `cliente_id` bigint NOT NULL,
  `restaurante_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2rp2ctmidmcbhnrhepavvd5va` (`cliente_id`),
  KEY `FKl8td5okmnw35xel3yyrxlqd4` (`restaurante_id`),
  CONSTRAINT `FK2rp2ctmidmcbhnrhepavvd5va` FOREIGN KEY (`cliente_id`) REFERENCES `tabelacliente` (`id`),
  CONSTRAINT `FKl8td5okmnw35xel3yyrxlqd4` FOREIGN KEY (`restaurante_id`) REFERENCES `tablerestaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabelapedidos`
--

LOCK TABLES `tabelapedidos` WRITE;
/*!40000 ALTER TABLE `tabelapedidos` DISABLE KEYS */;
INSERT INTO `tabelapedidos` VALUES (2,177,7,1),(3,177,7,1),(4,221.55,8,1),(5,202,9,1);
/*!40000 ALTER TABLE `tabelapedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabelaprodutos`
--

DROP TABLE IF EXISTS `tabelaprodutos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabelaprodutos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `preco` double DEFAULT NULL,
  `url_imagem` varchar(255) DEFAULT NULL,
  `restaurante_id` bigint DEFAULT NULL,
  `available` bit(1) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKovoll2cagbmdwtam61023ufrw` (`restaurante_id`),
  CONSTRAINT `FKovoll2cagbmdwtam61023ufrw` FOREIGN KEY (`restaurante_id`) REFERENCES `tablerestaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabelaprodutos`
--

LOCK TABLES `tabelaprodutos` WRITE;
/*!40000 ALTER TABLE `tabelaprodutos` DISABLE KEYS */;
INSERT INTO `tabelaprodutos` VALUES (6,'Corte nobre com capa de gordura uniforme, selada na brasa forte e finalizada com flor de sal para realçar a suculência original.','Picanha Premium',85,'https://images.unsplash.com/photo-1544025162-d76694265947?q=80&w=800',1,_binary '\0',NULL),(7,'A verdadeira costela bovina, assada lentamente no bafo por 12 horas até desmanchar do osso. Sabor intenso e textura incomparável.','Costela Janela',85,'https://images.unsplash.com/photo-1529193591184-b1d58069ecdd?q=80&w=800',1,_binary '\0',NULL),(8,'Corte extremamente macio e suculento, fatiado contra a fibra e finalizado com uma camada generosa de manteiga de garrafa e alho crocante.','Maminha na Manteiga',82,'https://images.unsplash.com/photo-1558030006-450675393462?q=80&w=800',1,_binary '\0',NULL),(14,'Medalhões de filé mignon grelhados na manteiga de ervas, servidos ao ponto do mestre.','Filé Mignon Premium',95,'https://media.istockphoto.com/id/849935798/photo/slicing-the-filet-steak.jpg?s=170667a&w=0&k=20&c=A3OlyBkbcAXLA9BO-cb1YcXkpQKaQrWlRWwyczngBFI=',1,_binary '\0',NULL),(15,'Tiras de carne marinadas grelhadas na hora, servidas com acompanhamentos tradicionais e molho especial.','Churrasco Coreano Especial',89.9,'https://images.unsplash.com/photo-1598515214211-89d3c73ae83b?q=80&w=1000&auto=format&fit=crop',1,_binary '\0',NULL),(16,'Cortes selecionados grelhados em fogo alto com chamas vivas, garantindo o sabor defumado e a suculência perfeita.','Churrasco Gourmet Premium',120,'https://images.unsplash.com/photo-1555939594-58d7cb561ad1?q=80&w=1000&auto=format&fit=crop',1,_binary '\0',NULL),(17,'Vem com farofa','Bife Acebolado',65.65,'https://files.agro20.com.br/uploads/2019/12/Bife-acebolado-3.jpg',1,_binary '',NULL),(18,'Vem Com farofa e feijoada além de salada verde como acompanhamento','Picanha mal passada',59.65,'https://www.coisasdaleia.com.br/wp-content/uploads/2022/09/dia-do-churrasco.jpg',1,_binary '',NULL),(19,'Costela bovina assada lentamente por 8 horas, macia e suculenta, acompanha farofa, vinagrete e mandioca cozida.','Costela Bovina no Bafo',79.9,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcStTt9kGcoZfJD9wtJdfCnts9f22ayIRqM4Zc_Wbevfrw&s=10',1,_binary '',NULL);
/*!40000 ALTER TABLE `tabelaprodutos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tablerestaurant`
--

DROP TABLE IF EXISTS `tablerestaurant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tablerestaurant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `tiposcomidas` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `close_time` varchar(255) DEFAULT NULL,
  `delivery_radius_km` int DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `open_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tablerestaurant`
--

LOCK TABLES `tablerestaurant` WRITE;
/*!40000 ALTER TABLE `tablerestaurant` DISABLE KEYS */;
INSERT INTO `tablerestaurant` VALUES (1,'contato@fogoegrasa.com','Fogo & Brasa','Churrasco Uruguaio',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tablerestaurant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersecurity`
--

DROP TABLE IF EXISTS `usersecurity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersecurity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(150) NOT NULL,
  `senha` varchar(255) DEFAULT NULL,
  `cliente_id` bigint DEFAULT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK82p0t9yjkvyrf8a9uc7tar5jr` (`cliente_id`),
  CONSTRAINT `FKf9f02xhkicacv4r5skhx33osm` FOREIGN KEY (`cliente_id`) REFERENCES `tabelacliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersecurity`
--

LOCK TABLES `usersecurity` WRITE;
/*!40000 ALTER TABLE `usersecurity` DISABLE KEYS */;
INSERT INTO `usersecurity` VALUES (1,'andersonsousa65@gmail.com','$2a$10$y9Bm5JSRb0NX8Cq4A4Js9ua9rvdYcnPR9JQPPKnFoB74lxhnaEIBG',NULL,''),(2,'maria@exemplo.com','$2a$10$fajoHzLuHxsPsmym.pyAwe6cP8IGGp9hK4NeJgC1mK0MGfwEfE6yy',5,''),(3,'marcos@exemplo.com','$2a$10$Wavkh9FwB8E/zsSzFw0KBO2Oadykq1D4BVwpL/nB64c36te9Sn6wu',6,''),(4,'mariana@exemplo.com','$2a$10$eBATrAVX7KS/0D5svgLUZe5wy1DTC33QPscfxFva9U6488NXV9ZUm',7,''),(55,'admin@fogoebrasa.com.br','$2a$10$eiiqkQdlnmrMgqIZUCVrte59YgpNjz3HwD7h9M1YJ/OLKg0NN72pq',NULL,'ROLE_ADMIN'),(56,'anavitoria@exemplo.com','$2a$10$iDOTst3wyUJba20xJ1jEK.iVazGklaQBimptOpz4YR9NNX0mG1Wsa',8,'ROLE_CLIENTE'),(57,'samuel@exemple.com','$2a$10$4xPzorY6glv6.oOtpHaUBuRZuWRe./NZ.Edw0xbBolxZTdss0w/Hm',9,'ROLE_CLIENTE');
/*!40000 ALTER TABLE `usersecurity` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-16  1:11:01
