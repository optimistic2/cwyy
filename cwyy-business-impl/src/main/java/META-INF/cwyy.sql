/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.12-log : Database - cwyy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cwyy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cwyy`;

/*Table structure for table `cy_bd_dic` */

DROP TABLE IF EXISTS `cy_bd_dic`;

CREATE TABLE `cy_bd_dic` (
  `id` varchar(36) NOT NULL COMMENT '主键',
  `code` varchar(36) DEFAULT NULL COMMENT '编码唯一',
  `name` varchar(36) DEFAULT NULL COMMENT '字典名称',
  `description` varchar(100) DEFAULT NULL COMMENT '字典说明',
  `dic_id` varchar(36) DEFAULT NULL COMMENT '所属字典类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `cy_bd_dic` */

LOCK TABLES `cy_bd_dic` WRITE;

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
