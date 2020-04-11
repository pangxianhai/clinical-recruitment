-- 启动mysql mysql.server start
-- 创建数据库 CREATE DATABASE medical_recruitment DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci
-- 创建用户 CREATE USER 'medical_recruitment-user'@'%' IDENTIFIED BY 'mmmm8888';
-- 给用户分配权限 GRANT ALL ON medical_recruitment.* TO 'medical_recruitment-user'@'%';

drop table  if exists user_info;
create table user_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  open_id varchar(64) COLLATE utf8_bin COMMENT '微信OPEN_ID',
  union_id varchar(64)  COLLATE utf8_bin COMMENT '微信唯一ID',
  nickname varchar(64)  COLLATE utf8_bin COMMENT '昵称',
  real_name varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '姓名',
  gender TINYINT  NOT NULL COMMENT '性别: 1-男 2-女',
  phone varchar(64)  COLLATE utf8_bin COMMENT '联系方式',
  password varchar(128)  COLLATE utf8_bin COMMENT '密码',
  status TINYINT  NOT NULL COMMENT '用户状态 1-正常,2-冻结',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(open_id),
  UNIQUE(union_id),
  UNIQUE(phone)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户信息表';

INSERT INTO `user_info`(id,real_name,phone,gender,password,status,created_by,created_time) VALUES (1,'pxh','17780583960',1,'E117B87A9926EE6AB196F0E28F7FCB626633C4EAD180686BEFF1A58142F836B3',1,'系统','2019-02-15 21:56:34');

drop table  if exists organization_info;
drop table  if exists hospital_info;
create table hospital_info(
 id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 name varchar(128)  NOT NULL COLLATE utf8_bin COMMENT '机构名称',
 province_id bigint(20) NOT NULL COMMENT '省ID',
 city_id bigint(20) NOT NULL COMMENT '市ID',
 district_id bigint(20) COMMENT '区ID',
 created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
 created_time datetime NOT NULL COMMENT '创建时间',
 updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
 updated_time datetime COMMENT '更新时间',
 PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='医院信息';

drop table  if exists organization_department_info;
drop table  if exists department_info;
create table department_info(
 id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 name varchar(128)  NOT NULL COLLATE utf8_bin COMMENT '科室名称',
 hospital_id bigint(20) NOT NULL COMMENT '所属医院id',
 created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
 created_time datetime NOT NULL COMMENT '创建时间',
 updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
 updated_time datetime COMMENT '更新时间',
 PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='科室信息';

drop table if exists researcher_info;
drop table  if exists doctor_info;
drop table  if exists reference_info;
create table reference_info(
 id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
 user_id bigint(20) NOT NULL COMMENT '用户ID',
 hospital_id bigint(20) NOT NULL COMMENT '医院ID',
 department_id bigint(20) NOT NULL COMMENT '科室ID',
 medical_institution varchar(1024) COMMENT '执业机构',
 medical_category varchar(1024) COMMENT '执业类别',
 remark varchar(1024) COMMENT '备注',
 role TINYINT  NOT NULL COMMENT '角色 1-科室主任,2-医生',
 status TINYINT  NOT NULL COMMENT '推荐人状态 1-正常,2-冻结',
 created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
 created_time datetime NOT NULL COMMENT '创建时间',
 updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
 updated_time datetime COMMENT '更新时间',
 PRIMARY KEY (id),
 UNIQUE(user_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='推荐人信息';

drop table if exists patient_info;
create table patient_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  province_id bigint(20) NOT NULL COMMENT '省ID',
  city_id bigint(20) NOT NULL COMMENT '市ID',
  district_id bigint(20) COMMENT '区ID',
  age int COMMENT '年龄',
  status TINYINT  NOT NULL COMMENT '患者状态 1-正常,2-冻结',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(user_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='患者信息表';

drop table if exists medical_clinical_recruitment_info;
drop table if exists recruitment_info;
create table recruitment_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  register_code varchar(32) NOT NULL COLLATE utf8_bin COMMENT '登记编号',
  title varchar(32) NOT NULL COLLATE utf8_bin COMMENT '标题',
  category_id bigint(20) NOT NULL COMMENT '类目ID',
  practice_stages varchar(32) NOT NULL COLLATE utf8_bin COMMENT '实验分期',
  indication varchar(32) NOT NULL COLLATE utf8_bin COMMENT '适应症状',
  drug_name varchar(1024) NOT NULL COLLATE utf8_bin COMMENT '药物名称',
  drug_type varchar(512) NOT NULL COLLATE utf8_bin COMMENT '药物类型',
  recruitment_number INT  NOT NULL  COMMENT '招募人数',
  introduction TEXT COMMENT '简介',
  treatment_plan TEXT COMMENT '治疗方案',
  screening_standard TEXT COMMENT '初筛要点',
  entry_criteria TEXT COMMENT '入排标准',
  patient_rights TEXT COMMENT '患者权益',
  start_time datetime NOT NULL COMMENT '启始时间',
  stop_time datetime NOT NULL COMMENT '截至时间',
  bid_party varchar(512) COLLATE utf8_bin COMMENT '申办方',
  status TINYINT  NOT NULL COMMENT '招募状态 1:进行中 2已结束',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='临床医学招募项目';

drop table if exists research_organization_info;
drop table if exists recruitment_organization_info;
drop table if exists recruitment_department;
create table recruitment_department(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  recruitment_id bigint(20) NOT NULL COMMENT '招募ID',
  hospital_id bigint(20) NOT NULL COMMENT '医院ID',
  department_id bigint(20) NOT NULL COMMENT '所属科室id',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(recruitment_id,department_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='项目研究科室';

drop table if exists medical_clinical_recruitment_application;
drop table if exists recruitment_application_record;
create table recruitment_application_record(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  recruitment_id bigint(20) COMMENT '招募ID',
  recruitment_register_code varchar(32) COLLATE utf8_bin COMMENT '登记编号',
  hospital_id bigint(20) NOT NULL COMMENT '患者选择的医院ID',
  department_id bigint(20) NOT NULL COMMENT '患者选择的科室ID',
  patient_user_id bigint(20) NOT NULL COMMENT '患者用户ID',
  reference_user_id bigint(20) COMMENT '推荐人用户ID',
  disease_desc varchar(2048) COLLATE utf8_bin COMMENT '病症描述',
  genetic_disease_desc varchar(2048) COLLATE utf8_bin COMMENT '遗传病描述',
  disease_image varchar(2048) COLLATE utf8_bin COMMENT '病例图片',
  status TINYINT  NOT NULL COMMENT '患者状态',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(recruitment_id,patient_user_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='招募申报信息';

drop table if exists administrator_info;
create table administrator_info(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  user_id bigint(20) NOT NULL COMMENT '用户ID',
  type TINYINT  NOT NULL COMMENT '管理员类型 1-系统管理员 2-业务管理员',
  status TINYINT  NOT NULL COMMENT '管理员状态 1-正常 2-冻结',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id),
  UNIQUE(user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='管理员信息';

INSERT INTO `administrator_info`(user_id,type,status,created_by,created_time) VALUES (10,1,1,'系统','2019-02-15 21:56:34');

drop table if exists business_administrator_auth;
create table business_administrator_auth(
  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  admin_user_id bigint(20) NOT NULL COMMENT '管理员用户id',
  recruitment_id bigint(20) COMMENT '招募ID',
  organization_id bigint(20) COMMENT '机构ID',
  created_by varchar(64)  NOT NULL COLLATE utf8_bin COMMENT '创建者',
  created_time datetime NOT NULL COMMENT '创建时间',
  updated_by varchar(64)   COLLATE utf8_bin COMMENT '更新者',
  updated_time datetime COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务管理员权限';

