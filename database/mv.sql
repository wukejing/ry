/*
MySQL Data Transfer
Source Host: localhost
Source Database: mv
Target Host: localhost
Target Database: mv
Date: 2016/1/2 8:26:27
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for erp_privilege
-- ----------------------------
CREATE TABLE `erp_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pid` bigint(20) DEFAULT NULL COMMENT '上级id',
  `privilege_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '权限名称',
  `privilege_type` int(11) DEFAULT NULL COMMENT '权限类型 0-菜单 1-url 2-页面元素',
  `privilege_url` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT 'url地址',
  `privilege_code` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '权限码',
  `flag_leaf` int(11) DEFAULT NULL COMMENT '是否叶子节点 0-否 1-是',
  `icon` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `order_num` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '排序字段',
  `erp_sys_id` bigint(20) DEFAULT NULL COMMENT '所属系统ID',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注描述',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` int(11) DEFAULT NULL COMMENT '有效性，-1：已删除，1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='erp权限';

-- ----------------------------
-- Table structure for erp_role
-- ----------------------------
CREATE TABLE `erp_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_name` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` int(11) DEFAULT NULL COMMENT '有效性，-1：已删除，1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='erp角色';

-- ----------------------------
-- Table structure for erp_role_privilege
-- ----------------------------
CREATE TABLE `erp_role_privilege` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` bigint(20) DEFAULT NULL,
  `privilege_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10591 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色-权限关联';

-- ----------------------------
-- Table structure for erp_system
-- ----------------------------
CREATE TABLE `erp_system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sys_name` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '系统名称',
  `icon` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
  `sys_domain` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '系统域名、前缀',
  `sys_owner` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '系统负责人',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '系统备注',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` int(11) DEFAULT NULL COMMENT '有效性，-1：已删除，1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='子系统信息';

-- ----------------------------
-- Table structure for erp_user
-- ----------------------------
CREATE TABLE `erp_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_id` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '登录id',
  `password` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '登录密码',
  `user_name` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
  `user_no` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '工号',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` int(11) DEFAULT NULL COMMENT '有效性，-1：已删除，1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='erp用户';

-- ----------------------------
-- Table structure for erp_user_role
-- ----------------------------
CREATE TABLE `erp_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户-角色关联';

-- ----------------------------
-- Table structure for mv_company
-- ----------------------------
CREATE TABLE `mv_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `express_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '公司名称',
  `contact` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '联系人',
  `tel` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '联系电话',
  `created` datetime DEFAULT NULL COMMENT '创建时间',
  `modified` datetime DEFAULT NULL COMMENT '修改时间',
  `yn` int(11) DEFAULT NULL COMMENT '有效性，-1：已删除，1：正常',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='公司信息';

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `erp_privilege` VALUES ('1', '0', '资源管理', '0', '/erpPrivilege', '6f500ae6-6962-4f5e-8af7-a82b9eda60b7', '1', 'icon-tasks', '0', '1', '', '2014-12-19 16:04:32', '2015-01-13 08:22:12', '1');
INSERT INTO `erp_privilege` VALUES ('2', '1', '添加资源', '1', '/erpPrivilege/add', 'a8cf4523-05ef-434a-9d3e-28dc3e6a1b15', '0', null, '0', '1', null, '2014-12-19 16:04:36', '2014-12-19 16:04:48', '1');
INSERT INTO `erp_privilege` VALUES ('3', '1', '添加保存资源', '1', '/erpPrivilege/save', '57ba4afb-25f7-4a7b-bae0-593bca2ec540', '0', null, '0', '1', null, '2014-12-19 16:04:39', '2014-12-19 16:04:52', '1');
INSERT INTO `erp_privilege` VALUES ('52', '1', '编辑资源', '1', '/erpPrivilege/edit/*', 'dad731da-3fbc-40ab-a7bb-2630d92d141d', '0', '', '0', '1', '', '2014-12-19 16:07:45', '2014-12-19 16:07:45', '1');
INSERT INTO `erp_privilege` VALUES ('53', '1', '编辑保存资源', '1', '/erpPrivilege/update', '5e439165-962c-4700-a037-ffbc48faa2cc', '0', '', '0', '1', '', '2014-12-19 16:08:10', '2014-12-19 16:08:10', '1');
INSERT INTO `erp_privilege` VALUES ('54', '0', '角色管理', '0', '/erpRole', '67cbfe41-5343-4e96-9fec-d8284276a2ba', '1', 'icon-sitemap', '0', '1', '', '2014-12-19 16:08:55', '2015-01-15 06:28:04', '1');
INSERT INTO `erp_privilege` VALUES ('55', '54', '添加角色', '1', '/erpRole/add', '4b918db8-0e63-4b40-a44e-0d35f52e85e0', '0', '', '0', '1', '', '2014-12-19 16:09:19', '2014-12-19 17:06:09', '1');
INSERT INTO `erp_privilege` VALUES ('56', '54', '添加保存角色', '1', '/erpRole/save', '6547b4f1-7339-4e3a-959e-d95a5b137ec6', '0', '', '0', '1', '', '2014-12-19 16:09:39', '2014-12-19 16:09:39', '1');
INSERT INTO `erp_privilege` VALUES ('57', '54', '编辑角色', '1', '/erpRole/edit/*', '2435177e-ff2b-4f17-bbe6-0ff19a08031b', '0', '', '0', '1', '', '2014-12-19 16:10:10', '2014-12-19 16:14:51', '1');
INSERT INTO `erp_privilege` VALUES ('58', '54', '编辑保存角色', '1', '/erpRole/update', '96b588f3-9dd6-4a27-96f1-4432fabac0c9', '0', '', '0', '1', '', '2014-12-19 16:10:27', '2014-12-19 16:10:27', '1');
INSERT INTO `erp_privilege` VALUES ('59', '54', '角色授权', '1', '/erpRole/grant/*', '8a8c1391-bbc0-40e9-8777-c1afdfa3a764', '0', '', '0', '1', '', '2014-12-19 16:11:06', '2014-12-19 16:15:02', '1');
INSERT INTO `erp_privilege` VALUES ('60', '54', '角色授权保存', '1', '/erpRole/doGrant', 'cc4745eb-4dd8-46d9-ad33-c85cc647b645', '0', '', '0', '1', '', '2014-12-19 16:11:26', '2014-12-19 16:15:12', '1');
INSERT INTO `erp_privilege` VALUES ('61', '0', 'ERP用户管理', '0', '/erpUser', '23acf01d-d950-4f90-a4d4-23a20a18d767', '1', 'icon-user', '0', '1', '', '2014-12-19 16:12:41', '2015-01-15 06:28:16', '1');
INSERT INTO `erp_privilege` VALUES ('62', '61', '添加用户', '1', '/erpUser/add', '1ebfcc93-0500-417f-9839-0d457f5fa0ba', '0', '', '0', '1', '', '2014-12-19 16:16:14', '2014-12-19 16:16:14', '1');
INSERT INTO `erp_privilege` VALUES ('63', '61', '添加保存用户', '1', '/erpUser/save', '53f0efd5-7391-441b-8b73-74ae7cca8052', '0', '', '0', '1', '', '2014-12-19 16:16:29', '2014-12-19 16:16:29', '1');
INSERT INTO `erp_privilege` VALUES ('64', '61', '修改用户', '1', '/erpUser/edit/*', 'b723a905-a598-4dae-af87-8bfee5f4ef80', '0', '', '0', '1', '', '2014-12-19 16:16:49', '2014-12-19 16:16:49', '1');
INSERT INTO `erp_privilege` VALUES ('65', '61', '修改保存用户', '1', '/erpUser/update', 'b1cea8e7-e99c-428b-b6c8-f5023336c33c', '0', '', '0', '1', '', '2014-12-19 16:17:06', '2014-12-19 16:17:06', '1');
INSERT INTO `erp_privilege` VALUES ('66', '0', '公司管理', '0', '/company', '6c80f920-a643-4d5c-a311-1063fc8bae5b', '1', 'icon-plane', '0', '1', '', '2014-12-19 16:18:48', '2015-04-28 09:40:27', '1');
INSERT INTO `erp_privilege` VALUES ('110', '61', 'erp用户密码重置页面', '1', '/erpUser/editPWD/*', '9b7cb3cf-b3f5-4723-84d1-31be6e2758dd', '0', '', '0', '1', '', '2014-12-29 07:11:20', '2014-12-29 07:11:20', '1');
INSERT INTO `erp_privilege` VALUES ('111', '61', 'erp用户密码修改', '1', '/erpUser/updatePWD', 'ca757c7b-a6ea-467c-bd15-5fb02d576ec3', '0', '', '0', '1', '', '2014-12-29 07:11:50', '2014-12-29 07:11:50', '1');
INSERT INTO `erp_privilege` VALUES ('112', '93', 'NB权限', '1', '/**', 'ca757c7b-a6ea-467c-bd25-5fb02d576ec3', '0', '', '0', '1', '', '2014-12-29 07:11:50', '2014-12-29 07:11:50', '1');
INSERT INTO `erp_privilege` VALUES ('113', '0', '子系统管理', '0', '/erpSystem/list', 'a4890589-03a8-4765-adad-d2f1f4de4d9a', '1', 'icon-flag', '0', '1', '', '2015-01-15 07:02:25', '2015-03-25 13:14:50', '1');
INSERT INTO `erp_privilege` VALUES ('114', '113', '系统管理操作', '1', '/erpSystem/**', '220bf72e-cfd4-4d2a-8bf2-4787a6435f96', '1', '', '0', '1', '', '2015-01-15 07:04:36', '2015-01-15 07:04:36', '1');
INSERT INTO `erp_privilege` VALUES ('121', '66', '添加公司', '1', '/company/add', '13f55b1a-46c2-4abb-a15d-407798e63abc', '0', '', '0', '1', '', '2015-09-23 17:12:11', '2015-09-23 17:12:11', '1');
INSERT INTO `erp_privilege` VALUES ('122', '66', '添加保存公司', '1', '/company/save', '553b38cb-1905-412f-88da-1936f396d63d', '0', '', '0', '1', '', '2015-09-23 17:12:36', '2015-09-23 17:12:36', '1');
INSERT INTO `erp_privilege` VALUES ('123', '66', '修改公司', '1', '/company/edit/*', '6da0e9d2-62d1-4a52-bad4-3d20a291a73b', '0', '', '0', '1', '', '2015-09-23 17:16:18', '2015-09-23 17:16:18', '1');
INSERT INTO `erp_privilege` VALUES ('124', '66', '修改保存公司', '1', '/company/update', '81bda5d6-4293-4ccc-a495-4db11bbd0f70', '0', '', '0', '1', '', '2015-09-23 17:16:48', '2015-09-23 17:16:48', '1');
INSERT INTO `erp_role` VALUES ('1', '管理员', '系统管理员，权限最大', '2014-12-12 15:18:28', '2014-12-19 14:28:31', '1');
INSERT INTO `erp_role_privilege` VALUES ('10564', '1', '1');
INSERT INTO `erp_role_privilege` VALUES ('10565', '1', '2');
INSERT INTO `erp_role_privilege` VALUES ('10566', '1', '3');
INSERT INTO `erp_role_privilege` VALUES ('10567', '1', '52');
INSERT INTO `erp_role_privilege` VALUES ('10568', '1', '53');
INSERT INTO `erp_role_privilege` VALUES ('10569', '1', '54');
INSERT INTO `erp_role_privilege` VALUES ('10570', '1', '55');
INSERT INTO `erp_role_privilege` VALUES ('10571', '1', '56');
INSERT INTO `erp_role_privilege` VALUES ('10572', '1', '57');
INSERT INTO `erp_role_privilege` VALUES ('10573', '1', '58');
INSERT INTO `erp_role_privilege` VALUES ('10574', '1', '59');
INSERT INTO `erp_role_privilege` VALUES ('10575', '1', '60');
INSERT INTO `erp_role_privilege` VALUES ('10576', '1', '61');
INSERT INTO `erp_role_privilege` VALUES ('10577', '1', '62');
INSERT INTO `erp_role_privilege` VALUES ('10578', '1', '63');
INSERT INTO `erp_role_privilege` VALUES ('10579', '1', '64');
INSERT INTO `erp_role_privilege` VALUES ('10580', '1', '65');
INSERT INTO `erp_role_privilege` VALUES ('10581', '1', '110');
INSERT INTO `erp_role_privilege` VALUES ('10582', '1', '111');
INSERT INTO `erp_role_privilege` VALUES ('10583', '1', '66');
INSERT INTO `erp_role_privilege` VALUES ('10584', '1', '121');
INSERT INTO `erp_role_privilege` VALUES ('10585', '1', '122');
INSERT INTO `erp_role_privilege` VALUES ('10586', '1', '123');
INSERT INTO `erp_role_privilege` VALUES ('10587', '1', '124');
INSERT INTO `erp_role_privilege` VALUES ('10588', '1', '113');
INSERT INTO `erp_role_privilege` VALUES ('10589', '1', '114');
INSERT INTO `erp_system` VALUES ('1', '基础管理', 'icon-flag', 'http://localhost', 'admin', '', '2015-01-13 08:21:27', '2016-01-01 18:18:43', '1');
INSERT INTO `erp_user` VALUES ('1', 'admin', '670b14728ad9902aecba32e22fa4f6bd', '管理员哦', '111', '2014-12-12 22:13:56', '2016-01-01 13:45:47', '1');
INSERT INTO `erp_user_role` VALUES ('95', '1', '1');
INSERT INTO `mv_company` VALUES ('1', '第一个', '老张', '18180522333', '2015-12-31 15:41:33', '2015-12-31 15:41:33', '1');
INSERT INTO `mv_company` VALUES ('2', '第二个', '老王', '18180233333', '2015-12-31 15:41:33', '2015-12-31 15:41:33', '1');
INSERT INTO `mv_company` VALUES ('3', 'sdfsd', 'sdfdsf', 'sdf', '2016-01-01 22:02:27', '2016-01-01 22:06:58', '1');
INSERT INTO `mv_company` VALUES ('4', 'fsdf', 'sdfsdf', '3333333', '2016-01-01 22:04:11', '2016-01-01 22:09:03', '1');
