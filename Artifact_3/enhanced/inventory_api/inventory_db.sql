CREATE DATABASE  IF NOT EXISTS `inventory_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `inventory_db`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: inventory_db
-- ------------------------------------------------------
-- Server version	8.0.41


--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `username` varchar(18) NOT NULL,
  `salt` text,
  `hash` text,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `inventoryitem`
--

DROP TABLE IF EXISTS `inventoryitem`;
CREATE TABLE `inventoryitem` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemName` text NOT NULL,
  `itemDescription` text NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `sku` varchar(20) DEFAULT NULL,
  `dateAdded` bigint DEFAULT NULL,
  `dateUpdated` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_InventoryItem_sku` (`sku`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

