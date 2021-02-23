CREATE TABLE `sys_error_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error_interface` varchar(255) DEFAULT NULL COMMENT '接口地址',
  `error_time` datetime DEFAULT NULL COMMENT '发生时间',
  `error_code` varchar(255) DEFAULT NULL COMMENT '错误code',
  `error_type` varchar(255) DEFAULT NULL COMMENT '错误类型',
  `error_exception` varchar(255) DEFAULT NULL COMMENT '异常',
  `error_msg` text COMMENT '异常描述',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `is_del` int(1) DEFAULT '0' COMMENT '删除标志：1 ：删除   0：未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4488 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统请求发生异常记录表';

CREATE TABLE `sys_role_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色编号',
  `role_name` varchar(32) DEFAULT NULL COMMENT '角色名字',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `role_status` varchar(2) DEFAULT NULL COMMENT '状态标志',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志：1 ：删除   0：未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_code_indexes` (`role_code`) USING BTREE COMMENT '角色编号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色信息表';

INSERT INTO `my_pro`.`sys_role_info`(`id`, `role_code`, `role_name`, `remarks`, `role_status`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`) VALUES (4, 'role001', '管理员', NULL, '1', '2020-08-12 16:12:22', NULL, '2020-11-19 17:01:03', 'admin12', 0);


CREATE TABLE `sys_role_power` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色code',
  `power_type` varchar(2) DEFAULT NULL COMMENT '权限类型 1、按钮权限  2、模块权限',
  `power_name` varchar(64) DEFAULT NULL COMMENT '权限名',
  `power_module` varchar(64) DEFAULT NULL COMMENT '权限模块',
  `power_action` varchar(32) DEFAULT NULL COMMENT '权限访问',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志：1 ：删除   0：未删除',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `role_module_action` (`role_code`,`power_module`,`power_action`) USING BTREE COMMENT '角色-模块-操作唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=755 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (1, 'role001', '2', '角色模块', 'roleModule', 'handle', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:07', NULL, 0, '添加、删除、分配用户角色');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (2, 'role001', '2', '角色模块', 'roleModule', 'view', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:09', NULL, 0, '添加、删除、分配用户角色');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (3, 'role001', '2', '用户模块', 'userModule', 'handle', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:11', NULL, 0, '添加、删除、启停、重置用户数据');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (4, 'role001', '2', '用户模块', 'userModule', 'view', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:12', NULL, 0, '添加、删除、启停、重置用户数据');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (5, 'role001', '2', '字典模块', 'dictModule', 'handle', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:14', NULL, 0, '模块作用');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (6, 'role001', '2', '字典模块', 'dictModule', 'view', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:16', NULL, 0, '模块作用');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (7, 'role001', '2', '权限模块', 'authModule', 'handle', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:18', NULL, 0, '修改、更新角色接口访问权限');
INSERT INTO `my_pro`.`sys_role_power`(`id`, `role_code`, `power_type`, `power_name`, `power_module`, `power_action`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`, `remarks`) VALUES (8, 'role001', '2', '权限模块', 'authModule', 'view', '2020-12-17 17:22:15', 'wei123', '2021-02-23 17:09:21', NULL, 0, '修改、更新角色接口访问权限');


CREATE TABLE `sys_user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_no` varchar(32) DEFAULT NULL COMMENT '用户账号',
  `user_code` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `remarks` varchar(64) DEFAULT NULL COMMENT '用户信息描述',
  `user_status` int(1) DEFAULT '1' COMMENT '用户状态 0、停用 1、可用',
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色Code',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_user` varchar(32) DEFAULT NULL COMMENT '更新人',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '删除标志：1 ：删除   0：未删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `user_code_indexes` (`user_code`) USING BTREE COMMENT '用户编号唯一索引',
  UNIQUE KEY `account_no_indexex` (`account_no`) USING BTREE COMMENT '账号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户信息表';

INSERT INTO `my_pro`.`sys_user_info`(`id`, `account_no`, `user_code`, `user_name`, `password`, `remarks`, `user_status`, `role_code`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`) VALUES (42, 'wei123', 'user1119092631009026', '喂', '495a41d55001a3970d99ec20f38c76020e9c71860f938539', '123', 1, 'role001', '2020-11-19 17:12:27', 'admin12', '2021-02-19 13:40:23', 'wei123', 0);
INSERT INTO `my_pro`.`sys_user_info`(`id`, `account_no`, `user_code`, `user_name`, `password`, `remarks`, `user_status`, `role_code`, `create_time`, `create_user`, `update_time`, `update_user`, `is_del`) VALUES (45, 'admin', 'user0128092632005356', '1', '766c2e24511999730da3da5d07586a22df7a575568506908', '1', 1, 'role001', '2021-01-28 17:18:23', 'admin12', '2021-01-28 17:18:23', NULL, 0);
