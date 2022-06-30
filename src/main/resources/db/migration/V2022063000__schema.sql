
CREATE TABLE IF NOT EXISTS `account` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `role` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL DEFAULT '无',
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `account_group` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  account_id bigint(20) NOT NULL,
  group_id bigint(20) NOT NULL,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `group` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(100) NOT NULL DEFAULT '无',
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `group_role` (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  group_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  updated_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (id)
);
