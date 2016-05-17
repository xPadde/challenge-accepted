-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: challengeaccepted
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `challenges`
--

DROP TABLE IF EXISTS `challenges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isChallengeClaimed` bit(1) DEFAULT NULL,
  `isChallengeCompleted` bit(1) DEFAULT NULL,
  `isChallengeDisapproved` bit(1) DEFAULT NULL,
  `isYoutubeUrlProvided` bit(1) DEFAULT NULL,
  `isYoutubeVideoUploaded` bit(1) DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `upvotes` bigint(20) DEFAULT NULL,
  `youtubeURL` varchar(255) DEFAULT NULL,
  `challenge_claimer_id` bigint(20) DEFAULT NULL,
  `challenge_creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9ocnxn5eus1tie48jn5tptm3` (`challenge_claimer_id`),
  KEY `FK_cqce2b189qgrgueef1k1c52j7` (`challenge_creator_id`),
  CONSTRAINT `FK_9ocnxn5eus1tie48jn5tptm3` FOREIGN KEY (`challenge_claimer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_cqce2b189qgrgueef1k1c52j7` FOREIGN KEY (`challenge_creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenges`
--

LOCK TABLES `challenges` WRITE;
/*!40000 ALTER TABLE `challenges` DISABLE KEYS */;
INSERT INTO `challenges` VALUES (1,'2016-05-16 14:32:09','Utan salt','\0','\0','\0',NULL,'\0','Ät en myrstack',2,NULL,NULL,1),(2,'2016-05-16 14:32:21','Med peppar','\0','\0','\0',NULL,'\0','Ät apor',2,NULL,NULL,1),(3,'2016-05-16 14:32:38','Med lite fanta i','','\0','\0','\0','','Drick en cola zero',2,'https://www.youtube.com/embed/JxQzzxIageA',2,1),(4,'2016-05-16 14:34:16','Med handflatan utåt','','\0','\0',NULL,'\0','Svina till ett svin',0,NULL,1,2),(5,'2016-05-16 14:34:26','Alla ska tro på dig','\0','\0','\0',NULL,'\0','Apa dig på ett zoo',1,NULL,NULL,2);
/*!40000 ALTER TABLE `challenges` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-16 14:36:32
