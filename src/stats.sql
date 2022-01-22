/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50141
Source Host           : localhost:3306
Source Database       : stats

Target Server Type    : MYSQL
Target Server Version : 50141
File Encoding         : 65001

Date: 2011-03-08 18:07:10
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tds_item`
-- ----------------------------
DROP TABLE IF EXISTS `tds_item`;
CREATE TABLE `tds_item` (
  `num_iid` bigint(20) NOT NULL,
  `shopid` bigint(50) NOT NULL,
  `title` varchar(60) NOT NULL,
  `pic_url` varchar(255) NOT NULL,
  PRIMARY KEY (`num_iid`),
  UNIQUE KEY `numiid` (`num_iid`) USING BTREE,
  KEY `sid` (`shopid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Records of tds_item

-- ----------------------------
-- Table structure for `tds_shop`
-- ----------------------------
DROP TABLE IF EXISTS `tds_shop`;
CREATE TABLE `tds_shop` (
  `shopid` bigint(20) NOT NULL,
  `nick` varchar(30) NOT NULL,
  `autosynflag` int(11) NOT NULL DEFAULT '1',
  `voiceflag` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`shopid`),
  KEY `idx_nick` (`nick`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

-- ----------------------------


-- ----------------------------
-- Table structure for `tds_visit`
-- ----------------------------
DROP TABLE IF EXISTS `tds_visit`;
CREATE TABLE `tds_visit` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `num_iid` bigint(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `pic_url` varchar(100) NOT NULL,
  `shopid` bigint(20) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `country` varchar(30) NOT NULL,
  `refer` int(11) NOT NULL,
  `entertime` datetime NOT NULL,
  `outtime` datetime NOT NULL,
  `timegroup` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Records of tds_visit
-- ----------------------------
