/*
 Navicat Premium Dump SQL

 Source Server         : 本地MySQL8
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : picture

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 04/02/2025 17:23:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture`  (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片 url',
                            `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '图片名称',
                            `introduction` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
                            `category` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类',
                            `tags` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标签（JSON 数组）',
                            `picSize` bigint NULL DEFAULT NULL COMMENT '图片体积',
                            `picWidth` int NULL DEFAULT NULL COMMENT '图片宽度',
                            `picHeight` int NULL DEFAULT NULL COMMENT '图片高度',
                            `picScale` double NULL DEFAULT NULL COMMENT '图片宽高比例',
                            `picFormat` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片格式',
                            `userId` bigint NOT NULL COMMENT '创建用户 id',
                            `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `editTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
                            `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                            `reviewStatus` int NOT NULL DEFAULT 0 COMMENT '审核状态：0-待审核; 1-通过; 2-拒绝',
                            `reviewMessage` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核信息',
                            `reviewerId` bigint NULL DEFAULT NULL COMMENT '审核人 ID',
                            `reviewTime` datetime NULL DEFAULT NULL COMMENT '审核时间',
                            `spaceId` bigint NULL DEFAULT NULL COMMENT '空间 id（为空表示公共空间）',
                            PRIMARY KEY (`id`) USING BTREE,
                            INDEX `idx_name`(`name` ASC) USING BTREE,
                            INDEX `idx_introduction`(`introduction` ASC) USING BTREE,
                            INDEX `idx_category`(`category` ASC) USING BTREE,
                            INDEX `idx_tags`(`tags` ASC) USING BTREE,
                            INDEX `idx_userId`(`userId` ASC) USING BTREE,
                            INDEX `idx_reviewStatus`(`reviewStatus` ASC) USING BTREE,
                            INDEX `idx_spaceId`(`spaceId` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1884891767640125442 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '图片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for space
-- ----------------------------
DROP TABLE IF EXISTS `space`;
CREATE TABLE `space`  (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                          `spaceName` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '空间名称',
                          `spaceLevel` int NULL DEFAULT 0 COMMENT '空间级别：0-普通版 1-专业版 2-旗舰版',
                          `maxSize` bigint NULL DEFAULT 0 COMMENT '空间图片的最大总大小',
                          `maxCount` bigint NULL DEFAULT 0 COMMENT '空间图片的最大数量',
                          `totalSize` bigint NULL DEFAULT 0 COMMENT '当前空间下图片的总大小',
                          `totalCount` bigint NULL DEFAULT 0 COMMENT '当前空间下的图片数量',
                          `userId` bigint NOT NULL COMMENT '创建用户 id',
                          `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `editTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
                          `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                          PRIMARY KEY (`id`) USING BTREE,
                          INDEX `idx_userId`(`userId` ASC) USING BTREE,
                          INDEX `idx_spaceName`(`spaceName` ASC) USING BTREE,
                          INDEX `idx_spaceLevel`(`spaceLevel` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '空间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
                         `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
                         `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
                         `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
                         `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
                         `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
                         `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin',
                         `editTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '编辑时间',
                         `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                         `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
                         PRIMARY KEY (`id`) USING BTREE,
                         UNIQUE INDEX `uk_userAccount`(`userAccount` ASC) USING BTREE,
                         INDEX `idx_userName`(`userName` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1886682231666036739 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
