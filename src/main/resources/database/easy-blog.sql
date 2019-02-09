/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.6.15 : Database - easy-blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`easy-blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `easy-blog`;

/*Table structure for table `admin_log` */

DROP TABLE IF EXISTS `admin_log`;

CREATE TABLE `admin_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT '1',
  `type` int(11) DEFAULT '0' COMMENT '操作类型',
  `target` bigint(20) DEFAULT NULL COMMENT '操作对象id',
  `target_name` varchar(32) DEFAULT NULL COMMENT '操作对象名称',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `blog` */

DROP TABLE IF EXISTS `blog`;

CREATE TABLE `blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `cover` varchar(256) DEFAULT NULL COMMENT '封面图片地址',
  `path` varchar(256) DEFAULT NULL COMMENT '文章路径',
  `brief` varchar(512) DEFAULT NULL COMMENT '简介',
  `classification` bigint(20) DEFAULT NULL COMMENT '所属分类',
  `labels` varchar(256) DEFAULT NULL COMMENT '标签，分号相隔',
  `state` int(11) DEFAULT '0' COMMENT '发布状态',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `watch_count` int(11) DEFAULT '0' COMMENT '浏览次数',
  `recommend_count` int(11) DEFAULT '0' COMMENT '推荐次数',
  `user_id` bigint(20) DEFAULT '1' COMMENT '创建人',
  `type` int(11) DEFAULT '1' COMMENT '文章类型',
  `classification_name` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Table structure for table `classification` */

DROP TABLE IF EXISTS `classification`;

CREATE TABLE `classification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `father` bigint(20) DEFAULT '0' COMMENT '父类id',
  `brief` varchar(256) DEFAULT NULL COMMENT '简介',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) DEFAULT '1',
  `father_name` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `comment_log` */

DROP TABLE IF EXISTS `comment_log`;

CREATE TABLE `comment_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(64) DEFAULT NULL,
  `content` varchar(256) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `folder` */

DROP TABLE IF EXISTS `folder`;

CREATE TABLE `folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `father` bigint(20) DEFAULT '0',
  `path` varchar(256) DEFAULT NULL,
  `brief` varchar(512) DEFAULT NULL,
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` bigint(20) DEFAULT '1',
  `type` int(11) DEFAULT '1' COMMENT '1为图片，2为资源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

/*Table structure for table `image` */

DROP TABLE IF EXISTS `image`;

CREATE TABLE `image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `folder` bigint(20) DEFAULT NULL,
  `name` varchar(64) DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL,
  `url` varchar(256) DEFAULT NULL,
  `size` bigint(11) DEFAULT NULL,
  `suffix` varchar(18) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT '1',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Table structure for table `link` */

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '创建链接的用户',
  `name` varchar(64) DEFAULT NULL COMMENT '链接名称',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '1 表示个人链接，2表示友链',
  `icon` varchar(256) DEFAULT NULL COMMENT '链接的图标',
  `address` varchar(256) DEFAULT NULL COMMENT '链接的地址',
  `time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建链接的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Table structure for table `resource` */

DROP TABLE IF EXISTS `resource`;

CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `path` varchar(256) DEFAULT NULL,
  `name` varchar(128) DEFAULT NULL,
  `brief` varchar(512) DEFAULT NULL,
  `page` bigint(20) DEFAULT NULL COMMENT '介绍页面路径，可以是blog',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT '1',
  `logo` varchar(256) DEFAULT NULL,
  `folder` bigint(20) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `system` */

DROP TABLE IF EXISTS `system`;

CREATE TABLE `system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `host` varchar(64) DEFAULT NULL,
  `image_root` varchar(256) DEFAULT NULL,
  `resource_root` varchar(256) DEFAULT NULL,
  `port` int(11) DEFAULT '80',
  `enable_rss` int(11) DEFAULT '0' COMMENT '允许rss',
  `enable_comment` int(11) DEFAULT '1' COMMENT '允许评论',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `nickname` varchar(64) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `website` varchar(64) DEFAULT NULL COMMENT '个人主页',
  `signature` varchar(256) DEFAULT NULL COMMENT '个人签名',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `place` varchar(32) DEFAULT NULL COMMENT '定位',
  `organization` varchar(64) DEFAULT NULL COMMENT '组织',
  `sex` varchar(1) DEFAULT NULL COMMENT '性别，M or W',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `visitor` */

DROP TABLE IF EXISTS `visitor`;

CREATE TABLE `visitor` (
  `email` varchar(64) NOT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` int(11) DEFAULT '1' COMMENT '状态，默认为1合法状态',
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `visitor_log` */

DROP TABLE IF EXISTS `visitor_log`;

CREATE TABLE `visitor_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip',
  `browser` varchar(32) DEFAULT NULL COMMENT '浏览器',
  `device` varchar(32) DEFAULT NULL COMMENT '设备',
  `type` int(11) DEFAULT '0' COMMENT '操作类型',
  `time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
