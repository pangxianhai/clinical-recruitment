-- 启动mysql sudo mysqld_safe
-- 创建数据库 CREATE DATABASE medical_recruitment DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci
-- 创建用户 CREATE USER 'medical_recruitment-user'@'%' IDENTIFIED BY 'mmmm8888';
-- 给用户分配权限 GRANT ALL ON medical_recruitment.* TO 'medical_recruitment-user'@'%';

drop table  if exists user_info;
create table user_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  open_id varchar(64) NOT NULL COLLATE utf8_bin COMMENT '微信OPEN_ID',
  union_id varchar(64)  COLLATE utf8_bin COMMENT '微信唯一ID',
  real_name varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '姓名',
  sex TINYINT  NOT NULL COMMENT '性别',
  user_type TINYINT NOT NULL COMMENT '用户类型 1:管理员，2:医生，3病人',
  phone varchar(64)  COLLATE utf8_bin COMMENT '联系方式',
  status TINYINT  NOT NULL COMMENT '用户状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(open_id),
  UNIQUE(real_name)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

drop table  if exists doctor_info;
create table doctor_info(
 id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 province_id bigint(20) NOT NULL COMMENT '省ID',
 city_id bigint(20) NOT NULL COMMENT '市ID',
 district_id bigint(20) COMMENT '区ID',
 medical_institution varchar(1024) COMMENT '执业机构',
 medical_category varchar(1024) COMMENT '执业类别',
 status TINYINT  NOT NULL COMMENT '医生状态',
 created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
 created_time bigint(20)  NOT NULL COMMENT '创建时间',
 updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
 updated_time bigint(20)  COMMENT '更新时间',
 PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医生信息表';

drop table if exists patient_info;
create table doctor_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  province_id bigint(20) NOT NULL COMMENT '省ID',
  city_id bigint(20) NOT NULL COMMENT '市ID',
  district_id bigint(20) COMMENT '区ID',
  age int COMMENT '年龄',
  status TINYINT  NOT NULL COMMENT '患者状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者信息表';

drop table if exists medical_clinical_recruitment_info;
create table medical_clinical_recruitment_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  register_code varchar(32) NOT NULL COLLATE utf8_bin COMMENT '登记编号',
  title varchar(32) NOT NULL COLLATE utf8_bin COMMENT '登记编号',
  practice_stages varchar(32) NOT NULL COLLATE utf8_bin COMMENT '实验分期',
  indication varchar(32) NOT NULL COLLATE utf8_bin COMMENT '适应症状',

  status TINYINT  NOT NULL COMMENT '患者状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time bigint(20)  NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time bigint(20)  COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='临床医学招募消息';


