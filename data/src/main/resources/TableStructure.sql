/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50616
Source Host           : localhost:3306
Source Database       : helphim

Target Server Type    : MYSQL
Target Server Version : 50616
File Encoding         : 65001

Date: 2016-11-15 17:10:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `description` varchar(100) NOT NULL,
  `permission` varchar(50) NOT NULL,
  `name` varchar(20) NOT NULL,
  `weight` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `creator_user_id` varchar(32) NOT NULL,
  `is_show` bit(1) NOT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_permission` (`permission`),
  KEY `index_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('06237d1d31b645ad948b9a0345315e07', '2016-11-02 15:47:46', '1', '用户详情', 'system:user:detail', '用户详情', '1', '3', '1', '\0', '', '', '690dd7a92cf44126bb3e0d5673d52f82');
INSERT INTO `sys_permission` VALUES ('06237d1d31b645ad948b9a0345315e08', '2016-11-02 15:47:46', '1', '角色详情', 'system:role:detail', '角色详情', '1', '3', '1', '\0', '', '', 'ef31d2d192ed4659b50d084fb649e2e2');
INSERT INTO `sys_permission` VALUES ('06237d1d31b645ad948b9a0345315e09', '2016-11-02 15:47:46', '1', '权限详情', 'system:permission:detail', '权限详情', '1', '3', '1', '\0', '', '', 'cc933ded4957417e9636a3d98b3a7a86');
INSERT INTO `sys_permission` VALUES ('08a8a730de184efeb3ee0bd82e5289a0', '2016-11-02 15:46:58', '1', '编辑系统用户', 'system:user:edit', '用户编辑', '1', '3', '1', '\0', '', '', '690dd7a92cf44126bb3e0d5673d52f82');
INSERT INTO `sys_permission` VALUES ('08a8a730de184efeb3ee0bd82e5289a1', '2016-11-02 15:46:58', '1', '编辑角色', 'system:role:edit', '角色编辑', '1', '3', '1', '\0', '', '', 'ef31d2d192ed4659b50d084fb649e2e2');
INSERT INTO `sys_permission` VALUES ('08a8a730de184efeb3ee0bd82e5289a2', '2016-11-02 15:46:58', '1', '编辑权限', 'system:permission:edit', '权限编辑', '1', '3', '1', '\0', '', '', 'cc933ded4957417e9636a3d98b3a7a86');
INSERT INTO `sys_permission` VALUES ('690dd7a92cf44126bb3e0d5673d52f82', '2016-11-02 16:20:53', '1', '用户管理', 'system:user', '用户管理', '1', '2', '1', '', '', '/system/user/page', '6e36ea2494e049a0bfbda74f86f18c0a');
INSERT INTO `sys_permission` VALUES ('6e36ea2494e049a0bfbda74f86f18c0a', '2016-10-11 14:51:43', '1', '系统相关配置管理', 'system', '系统管理', '1', '1', '1', '', 'fa-gear', '', '0');
INSERT INTO `sys_permission` VALUES ('cc933ded4957417e9636a3d98b3a7a86', '2016-11-02 16:21:37', '1', '权限管理', 'system:permission', '权限管理', '3', '2', '1', '', '', '/system/permission/page', '6e36ea2494e049a0bfbda74f86f18c0a');
INSERT INTO `sys_permission` VALUES ('df10f89acd494418954b893cf5c2a0c9', '2016-10-11 16:17:11', '1', '角色管理', 'system:role:page', '角色列表', '2', '2', '1', '', '', '', 'ef31d2d192ed4659b50d084fb649e2e2');
INSERT INTO `sys_permission` VALUES ('eca3dc246a184785997340af52977a06', '2016-10-12 14:02:34', '1', '系统用户管理', 'system:user:page', '用户列表', '3', '2', '1', '', '', '', '690dd7a92cf44126bb3e0d5673d52f82');
INSERT INTO `sys_permission` VALUES ('ef31d2d192ed4659b50d084fb649e2e2', '2016-11-02 16:21:14', '1', '角色管理', 'system:role', '角色管理', '2', '2', '1', '', '', '/system/role/page', '6e36ea2494e049a0bfbda74f86f18c0a');
INSERT INTO `sys_permission` VALUES ('f2d33140b0304f7f8cd056b3c0b8ba18', '2016-10-11 16:12:23', '1', '管理每一个功能的权限', 'system:permission:page', '菜单权限列表', '1', '2', '1', '', '', '', 'cc933ded4957417e9636a3d98b3a7a86');
INSERT INTO `sys_permission` VALUES ('fd4b2b8f0860412ca64037e69cebf1dc', '2016-11-02 15:48:15', '1', '删除用户', 'system:user:delete', '用户删除', '1', '3', '1', '\0', '', '', '690dd7a92cf44126bb3e0d5673d52f82');
INSERT INTO `sys_permission` VALUES ('fd4b2b8f0860412ca64037e69cebf1dd', '2016-11-02 15:48:15', '1', '删除角色', 'system:role:delete', '角色删除', '1', '3', '1', '\0', '', '', 'ef31d2d192ed4659b50d084fb649e2e2');
INSERT INTO `sys_permission` VALUES ('fd4b2b8f0860412ca64037e69cebf1de', '2016-11-02 15:48:15', '1', '删除权限', 'system:permission:delete', '权限删除', '1', '3', '1', '\0', '', '', 'cc933ded4957417e9636a3d98b3a7a86');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `description` varchar(300) NOT NULL,
  `name` varchar(100) NOT NULL,
  `role` varchar(100) NOT NULL,
  `creator_user_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '2016-09-09 16:35:41', '1', '系统超级管理员', '系统超级管理员', 'admin', '1');
INSERT INTO `sys_role` VALUES ('5853886fe60f462fb5fb74dbb17ead5b', '2016-10-12 11:56:21', '1', '只能管理系统角色', '角色管理员', 'role_manager', '1');
INSERT INTO `sys_role` VALUES ('a779072db9d04bf086df08ad26a94f0a', '2016-10-29 15:46:14', '1', '只有用户管理菜单', '用户管理', 'user_manager', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  `permission_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_role_permission` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('04815ce2142f46b1a1ac9289361233ea', '2016-11-14 17:40:52', '1', '1', 'f2d33140b0304f7f8cd056b3c0b8ba18');
INSERT INTO `sys_role_permission` VALUES ('04e9ae79a03c440a96083f10568a4dcb', '2016-11-14 17:40:56', '1', '1', '08a8a730de184efeb3ee0bd82e5289a0');
INSERT INTO `sys_role_permission` VALUES ('09a7ee6197294f63bd5917b55d7877ce', '2016-11-14 17:40:55', '1', '1', 'ef31d2d192ed4659b50d084fb649e2e2');
INSERT INTO `sys_role_permission` VALUES ('1b0f18c89ec5419887fc0b30b8600e51', '2016-11-14 17:40:52', '1', '1', 'cc933ded4957417e9636a3d98b3a7a86');
INSERT INTO `sys_role_permission` VALUES ('44687c954eb44885a3f848ce7edea750', '2016-11-14 17:40:55', '1', '1', '690dd7a92cf44126bb3e0d5673d52f82');
INSERT INTO `sys_role_permission` VALUES ('464959abac98464bb1ae4ab5dc12dd89', '2016-11-14 17:40:55', '1', '1', 'fd4b2b8f0860412ca64037e69cebf1dd');
INSERT INTO `sys_role_permission` VALUES ('48c859f7ada64cbeab4ec7bb700e673c', '2016-11-14 17:40:56', '1', '1', '06237d1d31b645ad948b9a0345315e07');
INSERT INTO `sys_role_permission` VALUES ('736d94e06e934bfb855eb3c6bc390a8c', '2016-11-14 17:40:56', '1', '1', 'fd4b2b8f0860412ca64037e69cebf1dc');
INSERT INTO `sys_role_permission` VALUES ('790035af69e143d4bbe6f57c629dece2', '2016-11-14 17:40:55', '1', '1', '06237d1d31b645ad948b9a0345315e08');
INSERT INTO `sys_role_permission` VALUES ('8082be6a50a94672bef45cffa23b89a0', '2016-11-14 17:40:55', '1', '1', '08a8a730de184efeb3ee0bd82e5289a1');
INSERT INTO `sys_role_permission` VALUES ('8f4b63fa1263497aa2cd9abc6a4daf06', '2016-11-14 17:40:57', '1', '1', '6e36ea2494e049a0bfbda74f86f18c0a');
INSERT INTO `sys_role_permission` VALUES ('bee2d59bdbbd427bb1dcfe238c51314d', '2016-11-14 17:40:52', '1', '1', 'fd4b2b8f0860412ca64037e69cebf1de');
INSERT INTO `sys_role_permission` VALUES ('c10a8cd6a08047fbb2dd168a7162756d', '2016-11-14 17:40:56', '1', '1', 'eca3dc246a184785997340af52977a06');
INSERT INTO `sys_role_permission` VALUES ('dc4fffe47227413eac48d7a82b40408c', '2016-11-14 17:40:52', '1', '1', '08a8a730de184efeb3ee0bd82e5289a2');
INSERT INTO `sys_role_permission` VALUES ('e775506b0ba2422aa372fd89cff5fe63', '2016-11-14 17:40:52', '1', '1', '06237d1d31b645ad948b9a0345315e09');
INSERT INTO `sys_role_permission` VALUES ('eeb8da7c97fe4966a92025209cab2d79', '2016-11-14 17:40:55', '1', '1', 'df10f89acd494418954b893cf5c2a0c9');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `nickname` varchar(100) NOT NULL,
  `mobile_phone` varchar(11) NOT NULL,
  `password` varchar(100) NOT NULL,
  `salt` varchar(5) NOT NULL,
  `username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '2016-09-08 15:57:12', '1', '123', 'AdminNickname', '123', '849f92e1dbe18285fd48adadfb20e6ce', 'AhLDi', 'admin');
INSERT INTO `sys_user` VALUES ('134991168d8d42e2b573858a1097cf65', '2016-10-12 15:55:40', '1', '12312', 'wyd', '11111111111', '31deb0285f1912a808cb006832a798d3', 'iXC3f', 'wyd');
INSERT INTO `sys_user` VALUES ('97426f411dc7403c9ef78a2a8bc65c0e', '2016-10-29 15:45:45', '1', '373437500@qq.com', 'test', '17785145470', '56dfcf54fdd5c2d5d2b6e4c3e47ceb6a', 'iwcMW', 'test');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL,
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `role_id` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `index_user_role` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('0fd727ca33874cdeae77d9e881dc45b5', '2016-10-20 15:33:35', '1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('5093317921a440228d3c9afe84179d59', '2016-10-28 15:20:23', '1', '1', '5853886fe60f462fb5fb74dbb17ead5b');
INSERT INTO `sys_user_role` VALUES ('7839963b77ed40e9a45496bd768935d0', '2016-10-29 16:14:18', '1', '97426f411dc7403c9ef78a2a8bc65c0e', 'a779072db9d04bf086df08ad26a94f0a');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` char(32) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  `creator_user_id` char(32) NOT NULL,
  `status` int(11) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=1;
