-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: challengeaccepted
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenges`
--

LOCK TABLES `challenges` WRITE;
/*!40000 ALTER TABLE `challenges` DISABLE KEYS */;
INSERT INTO `challenges` VALUES (1,'2016-05-11 16:05:43','Lägg dig på marken och skrik: \"Macke, ring åklagaren!!!\".','','','\0','\0','Skrik på åklagaren!',0,'https://www.youtube.com/embed/M32Kz3oDqkw',2,1),(2,'2016-05-11 16:16:07','Skejta ner för valfri gata i bara kalsongerna.','','','\0','\0','Skejta i kalsonger.',0,'https://www.youtube.com/embed/elN_CPsJ27M',2,1),(3,'2016-05-11 16:22:03','Doppa ditt huvud i en myrstack och håll kvar det i minst 15 sekunder.','\0','\0','\0','\0','Doppa huvudet i en myrstack',1,NULL,NULL,1),(4,'2016-05-11 16:27:44','Gör ett inbrott i din egen bil','\0','\0',NULL,'\0','Inbrott',1,NULL,NULL,1),(5,'2016-05-11 16:28:14','Minst minus 18 grader kallt skall det vara!','\0','\0',NULL,'\0','Koka kaffe i en frys',NULL,NULL,NULL,1),(6,'2016-05-11 16:28:35','Mycket svår utmaning men kan vara värt ändå','\0','\0',NULL,'\0','Hälsa på Padde i Halmstad',NULL,NULL,NULL,1),(7,'2016-05-11 16:29:19','Krama ett träd mitt på trottoaren','\0','\0',NULL,'\0','Krama ett träd',NULL,NULL,NULL,1),(8,'2016-05-11 16:29:50','Låt inte dig själv eller någon annan sitta i den!','\0','\0',NULL,'\0','Spräng en soffa',NULL,NULL,NULL,1),(9,'2016-05-11 16:30:16','Med eller utan kläder spelar ingen roll, det är lika roligt ändå :PppPPPppP','\0','\0',NULL,'\0','Bada i en fontän',NULL,NULL,NULL,1);
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

-- Dump completed on 2016-05-11 16:35:25
