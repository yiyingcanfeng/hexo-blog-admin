
CREATE DATABASE IF NOT EXISTS 'hexo-blog' DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', '管理员', '6fc3f369cc8bc7689c24c637109c0801d15bbb176e1a86f43e3c0947fa9a5114', '');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (425, '测试文章01', '2019-07-01 00:00:00');
INSERT INTO `article` VALUES (428, '测试文章02', '2019-07-02 00:00:00');
INSERT INTO `article` VALUES (430, '测试文章03', '2019-07-03 00:00:00');
INSERT INTO `article` VALUES (433, '测试文章04', '2019-07-04 00:00:00');
INSERT INTO `article` VALUES (436, '测试文章05', '2019-07-05 00:00:00');
INSERT INTO `article` VALUES (439, '测试文章06', '2019-07-06 00:00:00');
INSERT INTO `article` VALUES (443, '测试文章07', '2019-07-07 00:00:00');
INSERT INTO `article` VALUES (446, '测试文章08', '2019-07-08 00:00:00');
INSERT INTO `article` VALUES (448, '测试文章09', '2019-07-09 00:00:00');
INSERT INTO `article` VALUES (452, '测试文章10', '2019-07-10 00:00:00');
INSERT INTO `article` VALUES (455, '测试文章11', '2019-07-11 00:00:00');
INSERT INTO `article` VALUES (457, '测试文章12', '2019-07-12 00:00:00');
INSERT INTO `article` VALUES (461, '测试文章13', '2019-07-13 00:00:00');
INSERT INTO `article` VALUES (464, '测试文章14', '2019-07-14 00:00:00');
INSERT INTO `article` VALUES (466, '测试文章15', '2019-07-15 00:00:00');
INSERT INTO `article` VALUES (469, '测试文章16', '2019-07-16 00:00:00');
INSERT INTO `article` VALUES (474, '测试文章17', '2019-07-17 00:00:00');
INSERT INTO `article` VALUES (476, '测试文章18', '2019-07-18 00:00:00');
INSERT INTO `article` VALUES (479, '测试文章19', '2019-07-19 00:00:00');
INSERT INTO `article` VALUES (481, '测试文章20', '2019-07-20 00:00:00');
INSERT INTO `article` VALUES (485, '测试文章21', '2019-07-21 00:00:00');
INSERT INTO `article` VALUES (491, '测试文章23', '2019-07-23 00:00:00');
INSERT INTO `article` VALUES (493, '测试文章22', NULL);
INSERT INTO `article` VALUES (494, '测试', NULL);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '评论的父id，注意A评论下的所有子评论，我这里都设计为他的儿子，而不是儿子，孙子，曾孙。即，回复的回复，回复的回复的回复的pid都是某个一级评论。',
  `reply_user_id` int(11) NULL DEFAULT NULL COMMENT '被回复人的id，用于@对方时候显示。对一级评论进行评论不需要@他，所以reply_user_id 为空，对回复回复需要@对方，否则不知道回复谁。',
  `audit_status` int(11) NULL DEFAULT 0 COMMENT '审核状态，0:审核中 1:审核通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '可以', '2019-10-07 15:35:15', 1, 491, 0, 0, 1);
INSERT INTO `comment` VALUES (2, '写的不错', '2019-10-07 15:35:49', 2, 491, 0, 0, 1);
INSERT INTO `comment` VALUES (3, '确实', '2019-10-07 15:36:53', 3, 491, 1, 1, 1);
INSERT INTO `comment` VALUES (4, '不错', '2019-10-07 15:37:32', 10, 491, 0, 0, 0);

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `hexo_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `public_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '/root/hexo-blog/source/_posts', '/root/hexo-blog', '/usr/share/nginx/html');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '张三', 'aaa@qq.com', 'aaa.com');
INSERT INTO `user` VALUES (2, '李五', 'bbb@qq.com', NULL);
INSERT INTO `user` VALUES (3, '王五', 'ccc@qq.com', 'ccc.com');
INSERT INTO `user` VALUES (10, '赵六', 'ddd@qq.com', NULL);
INSERT INTO `user` VALUES (11, '小张', 'z@qq.com', NULL);
INSERT INTO `user` VALUES (12, '小李', 'xl@qq.com', NULL);
INSERT INTO `user` VALUES (14, '小王', 'wang@qq.com', NULL);
INSERT INTO `user` VALUES (15, '小赵', 'zhao@qq.com', NULL);
INSERT INTO `user` VALUES (16, '李', 'li@qq.com', NULL);

SET FOREIGN_KEY_CHECKS = 1;
