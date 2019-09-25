-- MySQL dump 10.13  Distrib 5.7.27, for Linux (x86_64)
--
-- Host: localhost    Database: demodb
-- ------------------------------------------------------
-- Server version	5.7.27-0ubuntu0.18.04.1

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
-- Table structure for table `ac_validation_tokens`
--

DROP TABLE IF EXISTS `ac_validation_tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ac_validation_tokens` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `token_valid` bit(1) NOT NULL,
  `token_valid_until` datetime DEFAULT NULL,
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `reason` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqamdf34eo911js0e19a36p6v2` (`created_by_id`),
  KEY `FK5c0f4fivjiyy19j4f0fxayyc3` (`updated_by_id`),
  KEY `FKi746ajdn1vcbojvapsq6jsc9u` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ac_validation_tokens`
--

LOCK TABLES `ac_validation_tokens` WRITE;
/*!40000 ALTER TABLE `ac_validation_tokens` DISABLE KEYS */;
/*!40000 ALTER TABLE `ac_validation_tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `activity_logs`
--

DROP TABLE IF EXISTS `activity_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity_logs` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `expires` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `request_method` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_visitors` bigint(20) DEFAULT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_agent` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK73c1vlvjh3dxqoy3txow3m48p` (`created_by_id`),
  KEY `FKornhymyu3fpfciwwp3caqvw7o` (`updated_by_id`),
  KEY `FKi1mahmfxp15s84l940o3nxg19` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity_logs`
--

LOCK TABLES `activity_logs` WRITE;
/*!40000 ALTER TABLE `activity_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `firebase_user_token`
--

DROP TABLE IF EXISTS `firebase_user_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `firebase_user_token` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `user_token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa59j1qrnpqwhsaj8p6xonwsl` (`created_by_id`),
  KEY `FKhnd7h4lpfmd992kbd5nbgejq3` (`updated_by_id`),
  KEY `FKijg4ywkf6r83uvyp905o6omwc` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `firebase_user_token`
--

LOCK TABLES `firebase_user_token` WRITE;
/*!40000 ALTER TABLE `firebase_user_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `firebase_user_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_users`
--

DROP TABLE IF EXISTS `m_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_users` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `account_non_expired` bit(1) NOT NULL,
  `account_non_locked` bit(1) NOT NULL,
  `credentials_non_expired` bit(1) NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrauvhfwxl9xfjhvc2wc2foj9f` (`created_by_id`),
  KEY `FKluks1h3sxcrnepqqqa4s9orjf` (`updated_by_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_users`
--

LOCK TABLES `m_users` WRITE;
/*!40000 ALTER TABLE `m_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `m_users_roles`
--

DROP TABLE IF EXISTS `m_users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `m_users_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FK5hwrg9mw7hmdq8cn63mh7sx0j` (`roles_id`),
  KEY `FK143dfv8wd8b2w93gbvd84ps5k` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `m_users_roles`
--

LOCK TABLES `m_users_roles` WRITE;
/*!40000 ALTER TABLE `m_users_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `m_users_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profiles`
--

DROP TABLE IF EXISTS `profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profiles` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `area` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `country` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `district` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `division` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `flat` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `floor` tinyint(4) NOT NULL,
  `house` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `police_station` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `post_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `post_office` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `road` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr_union` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `upazila` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `village` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo_identity_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `profile_picture_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi5ltfjqx7f5lps1ccggx9vlqo` (`created_by_id`),
  KEY `FK8hlv96njo618dbfk4qfbwiqma` (`updated_by_id`),
  KEY `FKonpp2s5vianwi6kh3800cqriv` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profiles`
--

LOCK TABLES `profiles` WRITE;
/*!40000 ALTER TABLE `profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `profiles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `promotions`
--

DROP TABLE IF EXISTS `promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `promotions` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `active` bit(1) NOT NULL,
  `background_color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `click_count` bigint(20) NOT NULL,
  `description` varchar(2000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `image_path` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `priority` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `text_color` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `view_count` bigint(20) NOT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiy66meuil12jls8wckj5idgsm` (`created_by_id`),
  KEY `FKjmtgxob0j5vmn0eb9i0vcrbq7` (`updated_by_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `promotions`
--

LOCK TABLES `promotions` WRITE;
/*!40000 ALTER TABLE `promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_by_id` bigint(20) DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKolo7v6bxrho3bgjfh10i4y4lb` (`created_by_id`),
  KEY `FK95jx57baw7c39ybblooejidye` (`updated_by_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-25  7:31:33
