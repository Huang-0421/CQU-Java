/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : stu_system

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 22/10/2024 18:45:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class_info
-- ----------------------------
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE `class_info`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `teacher_name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `count` int NULL DEFAULT NULL,
  `class` int NULL DEFAULT NULL,
  `term` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class_info
-- ----------------------------
INSERT INTO `class_info` VALUES (1, 'computer', '张三', 40, 1, '第四学期');
INSERT INTO `class_info` VALUES (2, 'computer', '李四', 37, 2, '第四学期');
INSERT INTO `class_info` VALUES (3, 'java', '王五', 36, 1, '第五学期');
INSERT INTO `class_info` VALUES (4, 'java', '赵六', 34, 2, '第五学期');
INSERT INTO `class_info` VALUES (5, 'database_sys', '钱七', 41, 1, '第四学期');
INSERT INTO `class_info` VALUES (6, 'database_sys', '王八', 38, 2, '第四学期');
INSERT INTO `class_info` VALUES (7, 'structure', '黄九', 37, 1, '第三学期');
INSERT INTO `class_info` VALUES (8, 'structure', '马十', 37, 2, '第三学期');

-- ----------------------------
-- Table structure for computer
-- ----------------------------
DROP TABLE IF EXISTS `computer`;
CREATE TABLE `computer`  (
  `name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade1` int NULL DEFAULT NULL,
  `grade2` int NULL DEFAULT NULL,
  `grade3` int NULL DEFAULT NULL,
  `grade4` int NULL DEFAULT NULL,
  `grade_avg` float NULL DEFAULT NULL,
  `class` int NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of computer
-- ----------------------------
INSERT INTO `computer` VALUES ('student1', 60, 60, 67, 87, 68.5, 2);
INSERT INTO `computer` VALUES ('student100', 75, 82, 87, 88, 83, 2);
INSERT INTO `computer` VALUES ('student11', 69, 100, 79, 64, 78, 2);
INSERT INTO `computer` VALUES ('student12', 85, 68, 82, 71, 76.5, 1);
INSERT INTO `computer` VALUES ('student13', 86, 79, 65, 86, 79, 2);
INSERT INTO `computer` VALUES ('student14', 81, 72, 75, 83, 77.75, 2);
INSERT INTO `computer` VALUES ('student15', 71, 81, 90, 79, 80.25, 2);
INSERT INTO `computer` VALUES ('student17', 79, 89, 96, 67, 82.75, 1);
INSERT INTO `computer` VALUES ('student18', 86, 91, 96, 65, 84.5, 2);
INSERT INTO `computer` VALUES ('student19', 69, 87, 91, 60, 76.75, 2);
INSERT INTO `computer` VALUES ('student2', 94, 68, 70, 89, 80.25, 2);
INSERT INTO `computer` VALUES ('student22', 95, 71, 85, 78, 82.25, 2);
INSERT INTO `computer` VALUES ('student24', 87, 100, 62, 65, 78.5, 1);
INSERT INTO `computer` VALUES ('student25', 91, 66, 72, 95, 81, 1);
INSERT INTO `computer` VALUES ('student27', 78, 67, 64, 90, 74.75, 1);
INSERT INTO `computer` VALUES ('student28', 94, 85, 85, 100, 91, 1);
INSERT INTO `computer` VALUES ('student29', 98, 77, 63, 62, 75, 2);
INSERT INTO `computer` VALUES ('student3', 94, 84, 91, 64, 83.25, 2);
INSERT INTO `computer` VALUES ('student30', 90, 81, 87, 70, 82, 1);
INSERT INTO `computer` VALUES ('student31', 84, 61, 73, 81, 74.75, 2);
INSERT INTO `computer` VALUES ('student32', 98, 67, 96, 94, 88.75, 2);
INSERT INTO `computer` VALUES ('student34', 86, 62, 67, 74, 72.25, 1);
INSERT INTO `computer` VALUES ('student35', 100, 92, 90, 71, 88.25, 1);
INSERT INTO `computer` VALUES ('student36', 87, 95, 71, 75, 82, 1);
INSERT INTO `computer` VALUES ('student37', 73, 85, 69, 96, 80.75, 2);
INSERT INTO `computer` VALUES ('student38', 86, 77, 80, 76, 79.75, 1);
INSERT INTO `computer` VALUES ('student39', 80, 68, 88, 70, 76.5, 1);
INSERT INTO `computer` VALUES ('student4', 96, 92, 87, 90, 91.25, 2);
INSERT INTO `computer` VALUES ('student40', 89, 72, 83, 99, 85.75, 2);
INSERT INTO `computer` VALUES ('student41', 61, 63, 78, 74, 69, 2);
INSERT INTO `computer` VALUES ('student42', 82, 66, 84, 90, 80.5, 1);
INSERT INTO `computer` VALUES ('student43', 90, 80, 73, 67, 77.5, 1);
INSERT INTO `computer` VALUES ('student44', 67, 65, 81, 75, 72, 2);
INSERT INTO `computer` VALUES ('student45', 65, 71, 84, 81, 75.25, 2);
INSERT INTO `computer` VALUES ('student46', 86, 86, 75, 71, 79.5, 1);
INSERT INTO `computer` VALUES ('student47', 82, 82, 96, 80, 85, 1);
INSERT INTO `computer` VALUES ('student5', 79, 90, 82, 83, 83.5, 1);
INSERT INTO `computer` VALUES ('student50', 87, 90, 94, 89, 90, 2);
INSERT INTO `computer` VALUES ('student51', 68, 92, 60, 98, 79.5, 1);
INSERT INTO `computer` VALUES ('student52', 65, 94, 68, 87, 78.5, 2);
INSERT INTO `computer` VALUES ('student53', 70, 90, 72, 60, 73, 2);
INSERT INTO `computer` VALUES ('student54', 90, 68, 99, 93, 87.5, 1);
INSERT INTO `computer` VALUES ('student55', 88, 61, 79, 75, 75.75, 1);
INSERT INTO `computer` VALUES ('student56', 78, 65, 63, 90, 74, 1);
INSERT INTO `computer` VALUES ('student58', 87, 62, 68, 78, 73.75, 1);
INSERT INTO `computer` VALUES ('student59', 74, 70, 96, 62, 75.5, 1);
INSERT INTO `computer` VALUES ('student61', 65, 98, 60, 85, 77, 1);
INSERT INTO `computer` VALUES ('student62', 80, 62, 61, 61, 66, 1);
INSERT INTO `computer` VALUES ('student63', 63, 71, 74, 88, 74, 2);
INSERT INTO `computer` VALUES ('student65', 80, 72, 67, 88, 76.75, 2);
INSERT INTO `computer` VALUES ('student66', 66, 94, 63, 99, 80.5, 1);
INSERT INTO `computer` VALUES ('student67', 72, 69, 86, 98, 81.25, 2);
INSERT INTO `computer` VALUES ('student7', 60, 65, 85, 78, 72, 1);
INSERT INTO `computer` VALUES ('student70', 96, 85, 95, 93, 92.25, 1);
INSERT INTO `computer` VALUES ('student71', 92, 88, 61, 87, 82, 2);
INSERT INTO `computer` VALUES ('student73', 68, 70, 70, 84, 73, 2);
INSERT INTO `computer` VALUES ('student74', 83, 68, 76, 92, 79.75, 1);
INSERT INTO `computer` VALUES ('student75', 77, 84, 69, 69, 74.75, 2);
INSERT INTO `computer` VALUES ('student76', 93, 76, 61, 72, 75.5, 2);
INSERT INTO `computer` VALUES ('student78', 66, 72, 94, 84, 79, 1);
INSERT INTO `computer` VALUES ('student79', 88, 99, 97, 79, 90.75, 1);
INSERT INTO `computer` VALUES ('student8', 83, 91, 95, 65, 83.5, 2);
INSERT INTO `computer` VALUES ('student81', 83, 74, 69, 63, 72.25, 1);
INSERT INTO `computer` VALUES ('student82', 100, 92, 75, 61, 82, 2);
INSERT INTO `computer` VALUES ('student83', 79, 86, 88, 61, 78.5, 1);
INSERT INTO `computer` VALUES ('student84', 62, 94, 77, 99, 83, 2);
INSERT INTO `computer` VALUES ('student87', 81, 100, 61, 94, 84, 2);
INSERT INTO `computer` VALUES ('student89', 78, 66, 82, 91, 79.25, 1);
INSERT INTO `computer` VALUES ('student91', 84, 96, 93, 66, 84.75, 2);
INSERT INTO `computer` VALUES ('student92', 86, 61, 79, 68, 73.5, 2);
INSERT INTO `computer` VALUES ('student93', 78, 100, 87, 66, 82.75, 1);
INSERT INTO `computer` VALUES ('student94', 69, 75, 70, 67, 70.25, 2);
INSERT INTO `computer` VALUES ('student95', 85, 82, 64, 76, 76.75, 1);
INSERT INTO `computer` VALUES ('student96', 82, 94, 86, 99, 90.25, 1);
INSERT INTO `computer` VALUES ('student97', 92, 88, 61, 98, 84.75, 1);
INSERT INTO `computer` VALUES ('student98', 69, 76, 64, 98, 76.75, 1);
INSERT INTO `computer` VALUES ('student99', 62, 71, 88, 74, 73.75, 1);
INSERT INTO `computer` VALUES ('黄瑞杰', 98, 68, 75, 96, 84.25, 1);

-- ----------------------------
-- Table structure for database_sys
-- ----------------------------
DROP TABLE IF EXISTS `database_sys`;
CREATE TABLE `database_sys`  (
  `name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade1` int NULL DEFAULT NULL,
  `grade2` int NULL DEFAULT NULL,
  `grade3` int NULL DEFAULT NULL,
  `grade4` int NULL DEFAULT NULL,
  `grade_avg` float NULL DEFAULT NULL,
  `class` int NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of database_sys
-- ----------------------------
INSERT INTO `database_sys` VALUES ('student1', 88, 63, 81, 92, 81, 2);
INSERT INTO `database_sys` VALUES ('student10', 62, 79, 87, 96, 81, 1);
INSERT INTO `database_sys` VALUES ('student100', 77, 99, 61, 97, 83.5, 1);
INSERT INTO `database_sys` VALUES ('student11', 73, 64, 73, 75, 71.25, 1);
INSERT INTO `database_sys` VALUES ('student12', 63, 95, 71, 76, 76.25, 1);
INSERT INTO `database_sys` VALUES ('student14', 84, 87, 98, 80, 87.25, 2);
INSERT INTO `database_sys` VALUES ('student15', 71, 79, 78, 60, 72, 1);
INSERT INTO `database_sys` VALUES ('student16', 74, 64, 62, 64, 66, 2);
INSERT INTO `database_sys` VALUES ('student17', 64, 90, 72, 93, 79.75, 1);
INSERT INTO `database_sys` VALUES ('student18', 69, 74, 72, 80, 73.75, 1);
INSERT INTO `database_sys` VALUES ('student19', 88, 85, 76, 65, 78.5, 2);
INSERT INTO `database_sys` VALUES ('student2', 79, 68, 77, 88, 78, 1);
INSERT INTO `database_sys` VALUES ('student20', 88, 86, 86, 91, 87.75, 1);
INSERT INTO `database_sys` VALUES ('student21', 64, 83, 88, 71, 76.5, 2);
INSERT INTO `database_sys` VALUES ('student23', 80, 64, 98, 90, 83, 1);
INSERT INTO `database_sys` VALUES ('student24', 62, 78, 66, 85, 72.75, 1);
INSERT INTO `database_sys` VALUES ('student26', 87, 90, 78, 73, 82, 1);
INSERT INTO `database_sys` VALUES ('student28', 76, 69, 90, 94, 82.25, 2);
INSERT INTO `database_sys` VALUES ('student29', 64, 73, 93, 68, 74.5, 1);
INSERT INTO `database_sys` VALUES ('student3', 81, 96, 95, 84, 89, 2);
INSERT INTO `database_sys` VALUES ('student31', 95, 89, 81, 81, 86.5, 1);
INSERT INTO `database_sys` VALUES ('student32', 97, 66, 99, 70, 83, 2);
INSERT INTO `database_sys` VALUES ('student33', 74, 94, 81, 66, 78.75, 2);
INSERT INTO `database_sys` VALUES ('student35', 98, 94, 60, 75, 81.75, 1);
INSERT INTO `database_sys` VALUES ('student36', 66, 89, 61, 81, 74.25, 1);
INSERT INTO `database_sys` VALUES ('student37', 82, 68, 62, 63, 68.75, 1);
INSERT INTO `database_sys` VALUES ('student38', 68, 75, 94, 90, 81.75, 2);
INSERT INTO `database_sys` VALUES ('student39', 84, 90, 65, 81, 80, 1);
INSERT INTO `database_sys` VALUES ('student4', 76, 74, 80, 87, 79.25, 2);
INSERT INTO `database_sys` VALUES ('student40', 93, 67, 81, 69, 77.5, 2);
INSERT INTO `database_sys` VALUES ('student41', 64, 69, 96, 94, 80.75, 2);
INSERT INTO `database_sys` VALUES ('student42', 76, 79, 82, 70, 76.75, 2);
INSERT INTO `database_sys` VALUES ('student43', 71, 81, 73, 77, 75.5, 1);
INSERT INTO `database_sys` VALUES ('student44', 63, 92, 72, 72, 74.75, 2);
INSERT INTO `database_sys` VALUES ('student45', 73, 68, 74, 97, 78, 1);
INSERT INTO `database_sys` VALUES ('student47', 90, 98, 90, 97, 93.75, 1);
INSERT INTO `database_sys` VALUES ('student48', 73, 98, 66, 85, 80.5, 1);
INSERT INTO `database_sys` VALUES ('student49', 86, 83, 83, 64, 79, 2);
INSERT INTO `database_sys` VALUES ('student5', 75, 92, 95, 82, 86, 1);
INSERT INTO `database_sys` VALUES ('student50', 93, 81, 98, 91, 90.75, 1);
INSERT INTO `database_sys` VALUES ('student51', 84, 67, 67, 70, 72, 2);
INSERT INTO `database_sys` VALUES ('student52', 60, 98, 93, 90, 85.25, 2);
INSERT INTO `database_sys` VALUES ('student53', 88, 65, 100, 69, 80.5, 2);
INSERT INTO `database_sys` VALUES ('student55', 83, 88, 94, 78, 85.75, 2);
INSERT INTO `database_sys` VALUES ('student56', 71, 91, 69, 71, 75.5, 1);
INSERT INTO `database_sys` VALUES ('student57', 61, 90, 62, 60, 68.25, 2);
INSERT INTO `database_sys` VALUES ('student58', 80, 62, 95, 82, 79.75, 1);
INSERT INTO `database_sys` VALUES ('student6', 62, 93, 75, 89, 79.75, 1);
INSERT INTO `database_sys` VALUES ('student60', 99, 74, 91, 60, 81, 1);
INSERT INTO `database_sys` VALUES ('student61', 73, 90, 88, 76, 81.75, 1);
INSERT INTO `database_sys` VALUES ('student63', 89, 60, 75, 62, 71.5, 2);
INSERT INTO `database_sys` VALUES ('student64', 67, 74, 76, 77, 73.5, 2);
INSERT INTO `database_sys` VALUES ('student65', 73, 65, 93, 68, 74.75, 1);
INSERT INTO `database_sys` VALUES ('student68', 61, 68, 99, 82, 77.5, 2);
INSERT INTO `database_sys` VALUES ('student69', 71, 86, 62, 82, 75.25, 2);
INSERT INTO `database_sys` VALUES ('student7', 61, 95, 80, 86, 80.5, 1);
INSERT INTO `database_sys` VALUES ('student70', 96, 73, 85, 83, 84.25, 1);
INSERT INTO `database_sys` VALUES ('student72', 66, 93, 91, 78, 82, 1);
INSERT INTO `database_sys` VALUES ('student73', 98, 70, 95, 97, 90, 2);
INSERT INTO `database_sys` VALUES ('student74', 71, 95, 66, 94, 81.5, 2);
INSERT INTO `database_sys` VALUES ('student75', 100, 81, 68, 93, 85.5, 1);
INSERT INTO `database_sys` VALUES ('student76', 80, 100, 89, 98, 91.75, 2);
INSERT INTO `database_sys` VALUES ('student77', 86, 70, 77, 61, 73.5, 1);
INSERT INTO `database_sys` VALUES ('student78', 69, 99, 91, 68, 81.75, 1);
INSERT INTO `database_sys` VALUES ('student79', 92, 91, 83, 83, 87.25, 2);
INSERT INTO `database_sys` VALUES ('student80', 74, 69, 60, 86, 72.25, 2);
INSERT INTO `database_sys` VALUES ('student81', 79, 86, 83, 61, 77.25, 1);
INSERT INTO `database_sys` VALUES ('student83', 92, 74, 65, 86, 79.25, 2);
INSERT INTO `database_sys` VALUES ('student85', 74, 84, 96, 67, 80.25, 2);
INSERT INTO `database_sys` VALUES ('student86', 67, 66, 89, 81, 75.75, 2);
INSERT INTO `database_sys` VALUES ('student87', 100, 77, 71, 77, 81.25, 1);
INSERT INTO `database_sys` VALUES ('student88', 82, 78, 76, 91, 81.75, 1);
INSERT INTO `database_sys` VALUES ('student9', 74, 60, 84, 89, 76.75, 2);
INSERT INTO `database_sys` VALUES ('student90', 71, 85, 68, 100, 81, 2);
INSERT INTO `database_sys` VALUES ('student93', 92, 100, 71, 86, 87.25, 2);
INSERT INTO `database_sys` VALUES ('student94', 91, 68, 73, 75, 76.75, 2);
INSERT INTO `database_sys` VALUES ('student96', 100, 74, 88, 79, 85.25, 1);
INSERT INTO `database_sys` VALUES ('student97', 64, 79, 74, 64, 70.25, 2);
INSERT INTO `database_sys` VALUES ('student98', 86, 90, 94, 82, 88, 1);

-- ----------------------------
-- Table structure for java
-- ----------------------------
DROP TABLE IF EXISTS `java`;
CREATE TABLE `java`  (
  `name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade1` int NULL DEFAULT NULL,
  `grade2` int NULL DEFAULT NULL,
  `grade3` int NULL DEFAULT NULL,
  `grade4` int NULL DEFAULT NULL,
  `grade_avg` float NULL DEFAULT NULL,
  `class` int NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of java
-- ----------------------------
INSERT INTO `java` VALUES ('student10', 61, 88, 83, 86, 79.5, 2);
INSERT INTO `java` VALUES ('student100', 88, 81, 81, 97, 86.75, 2);
INSERT INTO `java` VALUES ('student12', 84, 73, 71, 80, 77, 1);
INSERT INTO `java` VALUES ('student13', 79, 97, 86, 87, 87.25, 2);
INSERT INTO `java` VALUES ('student16', 72, 74, 93, 99, 84.5, 2);
INSERT INTO `java` VALUES ('student18', 76, 93, 94, 84, 86.75, 1);
INSERT INTO `java` VALUES ('student19', 88, 73, 62, 72, 73.75, 2);
INSERT INTO `java` VALUES ('student2', 83, 61, 75, 97, 79, 1);
INSERT INTO `java` VALUES ('student20', 60, 70, 71, 99, 75, 1);
INSERT INTO `java` VALUES ('student21', 90, 71, 80, 100, 85.25, 1);
INSERT INTO `java` VALUES ('student22', 67, 99, 60, 87, 78.25, 1);
INSERT INTO `java` VALUES ('student23', 77, 69, 87, 98, 82.75, 1);
INSERT INTO `java` VALUES ('student25', 63, 70, 71, 78, 70.5, 1);
INSERT INTO `java` VALUES ('student26', 64, 95, 95, 71, 81.25, 1);
INSERT INTO `java` VALUES ('student27', 99, 78, 63, 88, 82, 1);
INSERT INTO `java` VALUES ('student28', 78, 74, 96, 80, 82, 1);
INSERT INTO `java` VALUES ('student29', 97, 66, 89, 91, 85.75, 1);
INSERT INTO `java` VALUES ('student30', 92, 92, 98, 74, 89, 2);
INSERT INTO `java` VALUES ('student33', 86, 97, 66, 97, 86.5, 2);
INSERT INTO `java` VALUES ('student34', 74, 91, 76, 75, 79, 2);
INSERT INTO `java` VALUES ('student38', 74, 72, 90, 63, 74.75, 2);
INSERT INTO `java` VALUES ('student4', 94, 84, 83, 85, 86.5, 2);
INSERT INTO `java` VALUES ('student40', 86, 72, 76, 92, 81.5, 1);
INSERT INTO `java` VALUES ('student42', 88, 88, 92, 89, 89.25, 2);
INSERT INTO `java` VALUES ('student43', 64, 93, 75, 75, 76.75, 1);
INSERT INTO `java` VALUES ('student45', 90, 70, 86, 90, 84, 1);
INSERT INTO `java` VALUES ('student46', 77, 70, 94, 64, 76.25, 2);
INSERT INTO `java` VALUES ('student48', 79, 78, 68, 62, 71.75, 2);
INSERT INTO `java` VALUES ('student49', 74, 74, 87, 77, 78, 1);
INSERT INTO `java` VALUES ('student5', 79, 97, 78, 64, 79.5, 1);
INSERT INTO `java` VALUES ('student50', 63, 96, 62, 63, 71, 1);
INSERT INTO `java` VALUES ('student51', 65, 97, 98, 61, 80.25, 2);
INSERT INTO `java` VALUES ('student54', 80, 100, 76, 94, 87.5, 2);
INSERT INTO `java` VALUES ('student55', 66, 86, 72, 99, 80.75, 2);
INSERT INTO `java` VALUES ('student57', 97, 61, 96, 94, 87, 2);
INSERT INTO `java` VALUES ('student59', 94, 94, 60, 89, 84.25, 2);
INSERT INTO `java` VALUES ('student6', 61, 82, 77, 87, 76.75, 1);
INSERT INTO `java` VALUES ('student60', 97, 86, 75, 66, 81, 1);
INSERT INTO `java` VALUES ('student61', 90, 98, 97, 89, 93.5, 1);
INSERT INTO `java` VALUES ('student62', 100, 84, 99, 80, 90.75, 2);
INSERT INTO `java` VALUES ('student64', 70, 81, 72, 78, 75.25, 2);
INSERT INTO `java` VALUES ('student66', 70, 94, 93, 77, 83.5, 1);
INSERT INTO `java` VALUES ('student67', 96, 69, 78, 88, 82.75, 1);
INSERT INTO `java` VALUES ('student68', 65, 73, 86, 72, 74, 1);
INSERT INTO `java` VALUES ('student69', 73, 93, 97, 91, 88.5, 2);
INSERT INTO `java` VALUES ('student7', 71, 74, 83, 78, 76.5, 1);
INSERT INTO `java` VALUES ('student70', 74, 88, 85, 85, 83, 2);
INSERT INTO `java` VALUES ('student71', 85, 93, 92, 99, 92.25, 2);
INSERT INTO `java` VALUES ('student72', 100, 89, 60, 94, 85.75, 1);
INSERT INTO `java` VALUES ('student76', 83, 87, 83, 65, 79.5, 1);
INSERT INTO `java` VALUES ('student77', 84, 65, 68, 85, 75.5, 2);
INSERT INTO `java` VALUES ('student79', 73, 96, 69, 85, 80.75, 2);
INSERT INTO `java` VALUES ('student8', 89, 92, 99, 76, 89, 1);
INSERT INTO `java` VALUES ('student80', 68, 63, 72, 81, 71, 1);
INSERT INTO `java` VALUES ('student82', 84, 75, 77, 80, 79, 1);
INSERT INTO `java` VALUES ('student83', 95, 68, 76, 66, 76.25, 2);
INSERT INTO `java` VALUES ('student84', 91, 76, 79, 97, 85.75, 1);
INSERT INTO `java` VALUES ('student85', 83, 61, 75, 62, 70.25, 1);
INSERT INTO `java` VALUES ('student86', 63, 82, 82, 71, 74.5, 2);
INSERT INTO `java` VALUES ('student88', 65, 61, 68, 61, 63.75, 1);
INSERT INTO `java` VALUES ('student89', 91, 80, 60, 89, 80, 2);
INSERT INTO `java` VALUES ('student9', 82, 97, 62, 64, 76.25, 1);
INSERT INTO `java` VALUES ('student90', 71, 74, 75, 61, 70.25, 2);
INSERT INTO `java` VALUES ('student91', 95, 85, 93, 99, 93, 2);
INSERT INTO `java` VALUES ('student92', 87, 63, 64, 76, 72.5, 1);
INSERT INTO `java` VALUES ('student93', 92, 82, 100, 99, 93.25, 2);
INSERT INTO `java` VALUES ('student95', 86, 99, 97, 88, 92.5, 2);
INSERT INTO `java` VALUES ('student97', 67, 81, 98, 87, 83.25, 2);
INSERT INTO `java` VALUES ('student98', 81, 91, 84, 93, 87.25, 1);
INSERT INTO `java` VALUES ('student99', 83, 68, 60, 61, 68, 2);
INSERT INTO `java` VALUES ('黄瑞杰', 85, 60, 83, 70, 74.5, 2);

-- ----------------------------
-- Table structure for structure
-- ----------------------------
DROP TABLE IF EXISTS `structure`;
CREATE TABLE `structure`  (
  `name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `grade1` int NULL DEFAULT NULL,
  `grade2` int NULL DEFAULT NULL,
  `grade3` int NULL DEFAULT NULL,
  `grade4` int NULL DEFAULT NULL,
  `grade_avg` float NULL DEFAULT NULL,
  `class` int NULL DEFAULT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of structure
-- ----------------------------
INSERT INTO `structure` VALUES ('student1', 94, 90, 66, 72, 80.5, 1);
INSERT INTO `structure` VALUES ('student10', 93, 87, 61, 98, 84.75, 2);
INSERT INTO `structure` VALUES ('student11', 84, 77, 77, 94, 83, 2);
INSERT INTO `structure` VALUES ('student13', 86, 94, 85, 61, 81.5, 1);
INSERT INTO `structure` VALUES ('student14', 82, 86, 78, 100, 86.5, 2);
INSERT INTO `structure` VALUES ('student15', 60, 97, 62, 88, 76.75, 2);
INSERT INTO `structure` VALUES ('student16', 85, 79, 80, 67, 77.75, 2);
INSERT INTO `structure` VALUES ('student17', 62, 95, 60, 66, 70.75, 2);
INSERT INTO `structure` VALUES ('student20', 93, 70, 91, 99, 88.25, 2);
INSERT INTO `structure` VALUES ('student21', 84, 63, 100, 62, 77.25, 2);
INSERT INTO `structure` VALUES ('student22', 87, 70, 63, 67, 71.75, 1);
INSERT INTO `structure` VALUES ('student23', 73, 71, 76, 80, 75, 1);
INSERT INTO `structure` VALUES ('student24', 85, 70, 93, 82, 82.5, 1);
INSERT INTO `structure` VALUES ('student25', 62, 91, 96, 68, 79.25, 2);
INSERT INTO `structure` VALUES ('student26', 99, 76, 60, 84, 79.75, 2);
INSERT INTO `structure` VALUES ('student27', 85, 60, 93, 74, 78, 2);
INSERT INTO `structure` VALUES ('student3', 96, 61, 79, 80, 79, 2);
INSERT INTO `structure` VALUES ('student30', 97, 79, 65, 89, 82.5, 1);
INSERT INTO `structure` VALUES ('student31', 83, 96, 93, 99, 92.75, 2);
INSERT INTO `structure` VALUES ('student32', 85, 86, 79, 83, 83.25, 2);
INSERT INTO `structure` VALUES ('student33', 92, 75, 66, 90, 80.75, 2);
INSERT INTO `structure` VALUES ('student34', 95, 81, 98, 60, 83.5, 1);
INSERT INTO `structure` VALUES ('student35', 81, 84, 69, 100, 83.5, 1);
INSERT INTO `structure` VALUES ('student36', 82, 88, 91, 95, 89, 1);
INSERT INTO `structure` VALUES ('student37', 60, 92, 73, 80, 76.25, 2);
INSERT INTO `structure` VALUES ('student39', 77, 75, 80, 92, 81, 1);
INSERT INTO `structure` VALUES ('student41', 92, 63, 91, 93, 84.75, 1);
INSERT INTO `structure` VALUES ('student44', 77, 83, 64, 92, 79, 2);
INSERT INTO `structure` VALUES ('student46', 65, 72, 71, 65, 68.25, 1);
INSERT INTO `structure` VALUES ('student47', 97, 95, 84, 69, 86.25, 1);
INSERT INTO `structure` VALUES ('student48', 91, 80, 93, 62, 81.5, 1);
INSERT INTO `structure` VALUES ('student49', 93, 99, 97, 91, 95, 2);
INSERT INTO `structure` VALUES ('student52', 94, 82, 84, 69, 82.25, 1);
INSERT INTO `structure` VALUES ('student53', 75, 87, 81, 98, 85.25, 1);
INSERT INTO `structure` VALUES ('student54', 88, 85, 86, 85, 86, 1);
INSERT INTO `structure` VALUES ('student56', 61, 76, 91, 77, 76.25, 1);
INSERT INTO `structure` VALUES ('student57', 82, 79, 84, 89, 83.5, 2);
INSERT INTO `structure` VALUES ('student58', 73, 63, 98, 99, 83.25, 1);
INSERT INTO `structure` VALUES ('student59', 93, 88, 87, 98, 91.5, 2);
INSERT INTO `structure` VALUES ('student6', 99, 78, 76, 87, 85, 2);
INSERT INTO `structure` VALUES ('student60', 61, 96, 100, 68, 81.25, 1);
INSERT INTO `structure` VALUES ('student62', 89, 69, 84, 86, 82, 2);
INSERT INTO `structure` VALUES ('student63', 77, 61, 91, 65, 73.5, 2);
INSERT INTO `structure` VALUES ('student64', 87, 62, 67, 90, 76.5, 1);
INSERT INTO `structure` VALUES ('student65', 81, 74, 92, 87, 83.5, 1);
INSERT INTO `structure` VALUES ('student66', 79, 89, 77, 69, 78.5, 2);
INSERT INTO `structure` VALUES ('student67', 100, 75, 64, 99, 84.5, 1);
INSERT INTO `structure` VALUES ('student68', 83, 65, 78, 80, 76.5, 2);
INSERT INTO `structure` VALUES ('student69', 73, 77, 64, 71, 71.25, 1);
INSERT INTO `structure` VALUES ('student71', 88, 84, 64, 84, 80, 2);
INSERT INTO `structure` VALUES ('student72', 99, 91, 88, 96, 93.5, 1);
INSERT INTO `structure` VALUES ('student73', 98, 84, 61, 94, 84.25, 1);
INSERT INTO `structure` VALUES ('student74', 86, 100, 98, 62, 86.5, 2);
INSERT INTO `structure` VALUES ('student75', 85, 95, 63, 89, 83, 2);
INSERT INTO `structure` VALUES ('student77', 97, 68, 98, 77, 85, 1);
INSERT INTO `structure` VALUES ('student78', 71, 99, 75, 80, 81.25, 2);
INSERT INTO `structure` VALUES ('student8', 94, 79, 87, 100, 90, 1);
INSERT INTO `structure` VALUES ('student80', 65, 80, 87, 74, 76.5, 1);
INSERT INTO `structure` VALUES ('student81', 82, 88, 72, 63, 76.25, 2);
INSERT INTO `structure` VALUES ('student82', 84, 85, 68, 61, 74.5, 2);
INSERT INTO `structure` VALUES ('student84', 100, 95, 66, 95, 89, 2);
INSERT INTO `structure` VALUES ('student85', 97, 77, 99, 99, 93, 1);
INSERT INTO `structure` VALUES ('student86', 67, 68, 98, 67, 75, 2);
INSERT INTO `structure` VALUES ('student87', 67, 64, 100, 72, 75.75, 1);
INSERT INTO `structure` VALUES ('student88', 66, 79, 88, 61, 73.5, 1);
INSERT INTO `structure` VALUES ('student89', 88, 85, 93, 94, 90, 1);
INSERT INTO `structure` VALUES ('student9', 83, 62, 89, 95, 82.25, 1);
INSERT INTO `structure` VALUES ('student90', 75, 95, 75, 67, 78, 1);
INSERT INTO `structure` VALUES ('student91', 70, 62, 95, 67, 73.5, 2);
INSERT INTO `structure` VALUES ('student92', 61, 73, 87, 60, 70.25, 2);
INSERT INTO `structure` VALUES ('student94', 76, 90, 73, 99, 84.5, 1);
INSERT INTO `structure` VALUES ('student95', 86, 60, 94, 78, 79.5, 1);
INSERT INTO `structure` VALUES ('student96', 98, 97, 67, 74, 84, 2);
INSERT INTO `structure` VALUES ('student99', 60, 84, 73, 67, 71, 2);
INSERT INTO `structure` VALUES ('黄瑞杰', 89, 82, 94, 70, 83.75, 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` int NOT NULL AUTO_INCREMENT,
  `name` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stu_gender` int NULL DEFAULT NULL,
  `password` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course1` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course2` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `course3` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, '黄瑞杰', 1, '123', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (2, 'student1', 1, 'password1', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (3, 'student2', 0, 'password2', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (4, 'student3', 1, 'password3', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (5, 'student4', 0, 'password4', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (6, 'student5', 1, 'password5', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (7, 'student6', 0, 'password6', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (8, 'student7', 1, 'password7', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (9, 'student8', 0, 'password8', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (10, 'student9', 1, 'password9', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (11, 'student10', 0, 'password10', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (12, 'student11', 1, 'password11', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (13, 'student12', 0, 'password12', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (14, 'student13', 1, 'password13', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (15, 'student14', 0, 'password14', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (16, 'student15', 1, 'password15', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (17, 'student16', 0, 'password16', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (18, 'student17', 1, 'password17', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (19, 'student18', 0, 'password18', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (20, 'student19', 1, 'password19', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (21, 'student20', 0, 'password20', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (22, 'student21', 1, 'password21', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (23, 'student22', 0, 'password22', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (24, 'student23', 1, 'password23', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (25, 'student24', 0, 'password24', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (26, 'student25', 1, 'password25', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (27, 'student26', 0, 'password26', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (28, 'student27', 1, 'password27', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (29, 'student28', 0, 'password28', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (30, 'student29', 1, 'password29', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (31, 'student30', 0, 'password30', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (32, 'student31', 1, 'password31', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (33, 'student32', 0, 'password32', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (34, 'student33', 1, 'password33', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (35, 'student34', 0, 'password34', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (36, 'student35', 1, 'password35', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (37, 'student36', 0, 'password36', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (38, 'student37', 1, 'password37', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (39, 'student38', 0, 'password38', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (40, 'student39', 1, 'password39', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (41, 'student40', 0, 'password40', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (42, 'student41', 1, 'password41', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (43, 'student42', 0, 'password42', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (44, 'student43', 1, 'password43', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (45, 'student44', 0, 'password44', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (46, 'student45', 1, 'password45', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (47, 'student46', 0, 'password46', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (48, 'student47', 1, 'password47', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (49, 'student48', 0, 'password48', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (50, 'student49', 1, 'password49', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (51, 'student50', 0, 'password50', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (52, 'student51', 1, 'password51', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (53, 'student52', 0, 'password52', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (54, 'student53', 1, 'password53', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (55, 'student54', 0, 'password54', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (56, 'student55', 1, 'password55', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (57, 'student56', 0, 'password56', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (58, 'student57', 1, 'password57', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (59, 'student58', 0, 'password58', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (60, 'student59', 1, 'password59', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (61, 'student60', 0, 'password60', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (62, 'student61', 1, 'password61', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (63, 'student62', 0, 'password62', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (64, 'student63', 1, 'password63', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (65, 'student64', 0, 'password64', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (66, 'student65', 1, 'password65', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (67, 'student66', 0, 'password66', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (68, 'student67', 1, 'password67', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (69, 'student68', 0, 'password68', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (70, 'student69', 1, 'password69', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (71, 'student70', 0, 'password70', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (72, 'student71', 1, 'password71', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (73, 'student72', 0, 'password72', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (74, 'student73', 1, 'password73', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (75, 'student74', 0, 'password74', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (76, 'student75', 1, 'password75', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (77, 'student76', 0, 'password76', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (78, 'student77', 1, 'password77', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (79, 'student78', 0, 'password78', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (80, 'student79', 1, 'password79', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (81, 'student80', 0, 'password80', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (82, 'student81', 1, 'password81', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (83, 'student82', 0, 'password82', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (84, 'student83', 1, 'password83', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (85, 'student84', 0, 'password84', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (86, 'student85', 1, 'password85', 'database_sys', 'java', 'structure');
INSERT INTO `student` VALUES (87, 'student86', 0, 'password86', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (88, 'student87', 1, 'password87', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (89, 'student88', 0, 'password88', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (90, 'student89', 1, 'password89', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (91, 'student90', 0, 'password90', 'java', 'database_sys', 'structure');
INSERT INTO `student` VALUES (92, 'student91', 1, 'password91', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (93, 'student92', 0, 'password92', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (94, 'student93', 1, 'password93', 'computer', 'database_sys', 'java');
INSERT INTO `student` VALUES (95, 'student94', 0, 'password94', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (96, 'student95', 1, 'password95', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (97, 'student96', 0, 'password96', 'computer', 'database_sys', 'structure');
INSERT INTO `student` VALUES (98, 'student97', 1, 'password97', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (99, 'student98', 0, 'password98', 'computer', 'java', 'database_sys');
INSERT INTO `student` VALUES (100, 'student99', 1, 'password99', 'computer', 'java', 'structure');
INSERT INTO `student` VALUES (101, 'student100', 0, 'password100', 'computer', 'java', 'database_sys');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `teacher_id` int NOT NULL AUTO_INCREMENT,
  `name` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '张三', '123');
INSERT INTO `teacher` VALUES (2, '李四', '123');
INSERT INTO `teacher` VALUES (3, '王五', '123');
INSERT INTO `teacher` VALUES (4, '赵六', '123');
INSERT INTO `teacher` VALUES (5, '钱七', '123');
INSERT INTO `teacher` VALUES (6, '王八', '123');
INSERT INTO `teacher` VALUES (7, '黄九', '123');
INSERT INTO `teacher` VALUES (8, '马十', '123');

SET FOREIGN_KEY_CHECKS = 1;
