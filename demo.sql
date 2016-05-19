-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: demo
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
-- Table structure for table `File`
--

DROP TABLE IF EXISTS `File`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `File` (
  `file_id` varchar(255) NOT NULL,
  `cloud_path` varchar(255) DEFAULT NULL,
  `exp_name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `folder_id` varchar(255) DEFAULT NULL,
  `integrity_type` varchar(255) DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `share_type` varchar(255) DEFAULT NULL,
  `upload_time` varchar(255) DEFAULT NULL,
  `folderId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `File`
--

LOCK TABLES `File` WRITE;
/*!40000 ALTER TABLE `File` DISABLE KEYS */;
INSERT INTO `File` VALUES ('123','123','ttt','123',NULL,'0','123','0','123','014635820080380');
/*!40000 ALTER TABLE `File` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `access_control`
--

DROP TABLE IF EXISTS `access_control`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_control` (
  `access_control_id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`access_control_id`)
) ENGINE=InnoDB AUTO_INCREMENT=424 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_control`
--

LOCK TABLES `access_control` WRITE;
/*!40000 ALTER TABLE `access_control` DISABLE KEYS */;
INSERT INTO `access_control` VALUES (2,123,'24325435435344f'),(417,123,'24325435435344f'),(418,125,'24325435435344f'),(420,123,'014634824656000d'),(423,123,'014634826261380d');
/*!40000 ALTER TABLE `access_control` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `access_control_strategy`
--

DROP TABLE IF EXISTS `access_control_strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `access_control_strategy` (
  `access_control_id` int(11) NOT NULL,
  `strategy_id` int(11) NOT NULL,
  PRIMARY KEY (`access_control_id`,`strategy_id`),
  KEY `FK_9x06udouw28bss9lt4c6gn603` (`strategy_id`),
  KEY `FK_m94bt7qu0v5g6v9kpa2oocs0n` (`access_control_id`),
  CONSTRAINT `FK_9x06udouw28bss9lt4c6gn603` FOREIGN KEY (`strategy_id`) REFERENCES `strategy` (`strategy_id`),
  CONSTRAINT `FK_m94bt7qu0v5g6v9kpa2oocs0n` FOREIGN KEY (`access_control_id`) REFERENCES `access_control` (`access_control_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `access_control_strategy`
--

LOCK TABLES `access_control_strategy` WRITE;
/*!40000 ALTER TABLE `access_control_strategy` DISABLE KEYS */;
INSERT INTO `access_control_strategy` VALUES (2,1),(417,414),(418,415),(420,417),(423,420);
/*!40000 ALTER TABLE `access_control_strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `businessgroup`
--

DROP TABLE IF EXISTS `businessgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `businessgroup` (
  `id` varchar(255) NOT NULL,
  `admin_attrs` varchar(255) DEFAULT NULL,
  `admin_id` varchar(255) DEFAULT NULL,
  `ctime` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `storage_id` varchar(128) DEFAULT NULL,
  `u_attrs` varchar(255) DEFAULT NULL,
  `uids` varchar(255) DEFAULT NULL,
  `utime` varchar(255) DEFAULT NULL,
  `adminAttrs` varchar(255) DEFAULT NULL,
  `adminId` varchar(255) DEFAULT NULL,
  `storageId` varchar(128) DEFAULT NULL,
  `uAttrs` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `businessgroup`
--

LOCK TABLES `businessgroup` WRITE;
/*!40000 ALTER TABLE `businessgroup` DISABLE KEYS */;
INSERT INTO `businessgroup` VALUES ('4028810a54be58200154be5b3f360007','type=teacher;courses=java','1501211002','2016-05-17 18:55:31','java','4028810a54be58200154be5b3f320006','type=student;courses=java','1501211001',NULL,'type=teacher;courses=java','1501211002','4028810a54be58200154be5b3f320006','type=student;courses=java'),('4028810a54be58200154be5b88150009','type=teacher;group=hadoop','1501211002','2016-05-17 18:57:06','tom','4028810a54be58200154be5cb05e000a','type=student;teacher=tom','1501211001,1501211003',NULL,'type=teacher;group=hadoop','1501211002','4028810a54be58200154be5cb05e000a','type=student;teacher=tom');
/*!40000 ALTER TABLE `businessgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `folder` (
  `folder_id` varchar(255) NOT NULL,
  `create_date` varchar(255) DEFAULT NULL,
  `creater` varchar(255) DEFAULT NULL,
  `father_id` varchar(255) DEFAULT NULL,
  `integrity_type` varchar(255) DEFAULT NULL,
  `folder_name` varchar(255) DEFAULT NULL,
  `share_type` varchar(255) DEFAULT NULL,
  `storage_id` varchar(255) DEFAULT NULL,
  `storage_type` varchar(255) DEFAULT NULL,
  `whether_root` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`folder_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES ('014634824062610','2016-05-17 18:53:26',NULL,'0','0','jerry','0','0','0','0'),('014634824656000','2016-05-17 18:54:25',NULL,'0','0','tom','0','0','0','0'),('014634825088290','2016-05-17 18:55:08',NULL,'0','0','sally','0','0','0','0'),('014634825316260','2016-05-17 18:55:31',NULL,'1','0','java','0','0','0','0'),('014634825502820','2016-05-17 18:55:50',NULL,'1','0','hadoop','0','0','0','0'),('014634826261380','2016-05-17 18:57:06',NULL,'1','0','tom','0','0','0','0');
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `free_group`
--

DROP TABLE IF EXISTS `free_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `free_group` (
  `fg_id` varchar(255) NOT NULL,
  `fg_manager` varchar(255) DEFAULT NULL,
  `fg_name` varchar(255) DEFAULT NULL,
  `fg_userlist` varchar(255) DEFAULT NULL,
  `storgeid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `free_group`
--

LOCK TABLES `free_group` WRITE;
/*!40000 ALTER TABLE `free_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `free_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `freegroup_file`
--

DROP TABLE IF EXISTS `freegroup_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `freegroup_file` (
  `fgfile_id` varchar(255) NOT NULL,
  `file_id` varchar(255) DEFAULT NULL,
  `folder_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fgfile_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `freegroup_file`
--

LOCK TABLES `freegroup_file` WRITE;
/*!40000 ALTER TABLE `freegroup_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `freegroup_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `message` (
  `mess_id` varchar(255) NOT NULL,
  `fg_id` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`mess_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `space`
--

DROP TABLE IF EXISTS `space`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `space` (
  `id` varchar(255) NOT NULL,
  `root_directory` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `space`
--

LOCK TABLES `space` WRITE;
/*!40000 ALTER TABLE `space` DISABLE KEYS */;
INSERT INTO `space` VALUES ('4028810a54be58200154be5955aa0000','014634824062610','jerry',23),('4028810a54be58200154be5a3d450002','014634824656000','tom',23),('4028810a54be58200154be5ae6220004','014634825088290','sally',23),('4028810a54be58200154be5b3f320006','014634825316260','java',123),('4028810a54be58200154be5b88110008','014634825502820','hadoop',666),('4028810a54be58200154be5cb05e000a','014634826261380','tom',1000);
/*!40000 ALTER TABLE `space` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `strategy` (
  `strategy_id` int(11) NOT NULL AUTO_INCREMENT,
  `allow_create_floder` int(11) DEFAULT NULL,
  `allow_delete_file` int(11) DEFAULT NULL,
  `allow_delete_floder` int(11) DEFAULT NULL,
  `allow_download_file` int(11) DEFAULT NULL,
  `allow_share_floder` int(11) DEFAULT NULL,
  `allow_upload_file` int(11) DEFAULT NULL,
  `integrity` int(11) DEFAULT NULL,
  `operate_ways` int(11) DEFAULT NULL,
  `property_expression` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`strategy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=421 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `strategy`
--

LOCK TABLES `strategy` WRITE;
/*!40000 ALTER TABLE `strategy` DISABLE KEYS */;
INSERT INTO `strategy` VALUES (1,1,1,0,0,1,0,1,1,'#username=\'a\'&(password=\'12\' $ ty pe=\'2\')$!userID = \'1\'#'),(414,1,1,0,0,1,0,0,0,'#username=\'a\'#'),(415,0,1,0,0,1,0,0,0,'#username=\'a\'&(password=\'12\' $ ty pe=\'2\')$!userID = \'1\'#'),(417,0,0,1,0,0,0,0,1,'#courses=\'java\'&(age>\'30\' $ ty pe=\'0\')$!userID = \'1\'#'),(420,0,1,0,0,1,0,0,0,'#courses=\'java\'&(age>\'30\' $ ty pe=\'0\')$!userID = \'1\'#');
/*!40000 ALTER TABLE `strategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `student_id` varchar(255) NOT NULL,
  `academy` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `courses` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `study_group` varchar(255) DEFAULT NULL,
  `teacher_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES ('1501211001','5th academe',23,'java','Software college','jerry','storm','tom'),('1501211003','3th academe',24,'hadoop','Software college','sally','hadoop','tom');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teacher` (
  `teacher_id` varchar(255) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `courses` varchar(255) DEFAULT NULL,
  `department` varchar(255) DEFAULT NULL,
  `duty` varchar(255) DEFAULT NULL,
  `teacher_name` varchar(255) DEFAULT NULL,
  `study_group` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `teacher`
--

LOCK TABLES `teacher` WRITE;
/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` VALUES ('1501211002',45,'java','Software college','teacher','tom','hadoop','professor');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `token_id` varchar(255) NOT NULL,
  `dead_line` varchar(255) DEFAULT NULL,
  `deadLine` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `pid` varchar(255) NOT NULL,
  `groups` varchar(512) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `storage_id` varchar(128) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('4028810a54be58200154be5955b40001','4028810a54be58200154be5b3f360007,4028810a54be58200154be5cb061000b','666666',1,'4028810a54be58200154be5955aa0000','0','1501211001','jerry'),('4028810a54be58200154be5a3d490003','4028810a54be58200154be5b3f360007,4028810a54be58200154be5b88150009,4028810a54be58200154be5cb061000b','666666',1,'4028810a54be58200154be5a3d450002','1','1501211002','tom'),('4028810a54be58200154be5ae6290005','4028810a54be58200154be5b88150009,4028810a54be58200154be5cb061000b','666666',1,'4028810a54be58200154be5ae6220004','0','1501211003','sally');
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

-- Dump completed on 2016-05-19 14:12:17
