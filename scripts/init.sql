SET @@sql_mode = "traditional";
DROP DATABASE IF EXISTS `play-api-demo`;
CREATE DATABASE `play-api-demo`;
USE `play-api-demo`;


DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mls_id` int(11) unsigned DEFAULT NULL,
  `market_status` enum('off_market', 'for_sale', 'pending', 'auction', 'foreclosure', 'new_construction', 'coming_soon', 'sold', 'for_rent', 'foreclosed', 'lot/land', 'make_me_move') NOT NULL DEFAULT 'off_market',
  `property_type` enum('condo', 'cooperative', 'townhouse', 'apartment', 'multi_family', 'single_family') DEFAULT NULL,
  `listing_price` int(11) unsigned DEFAULT NULL,
  `num_bedroom` tinyint unsigned DEFAULT NULL,
  `num_full_bath` tinyint unsigned DEFAULT NULL,
  `num_half_bath` tinyint unsigned DEFAULT 0,
  `total_room` tinyint unsigned DEFAULT NULL,
  `build_year` smallint unsigned DEFAULT NULL,
  `version` varchar(45) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `published_on` BIGINT NULL DEFAULT NULL,
  `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `property_media`;
CREATE TABLE `property_media` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `property_id` int(11) unsigned NOT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `version` varchar(45) NOT NULL DEFAULT '0',
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `created_on` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_deleted` (`deleted`),
  CONSTRAINT `property_property_media_fk` FOREIGN KEY (`property_id`) REFERENCES `property` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
