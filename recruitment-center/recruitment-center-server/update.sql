ALTER TABLE medical_clinical_recruitment_application CHANGE recruitment_id recruitment_id bigint(20) COMMENT '招募ID';
ALTER TABLE medical_clinical_recruitment_application CHANGE recruitment_register_code recruitment_register_code varchar(32) COLLATE utf8_bin COMMENT '登记编号';

alter table medical_clinical_recruitment_application add disease_desc varchar(2048) COLLATE utf8_bin COMMENT '病症描述';
alter table medical_clinical_recruitment_application add genetic_disease_desc varchar(2048) COLLATE utf8_bin COMMENT '遗传病描述';
alter table medical_clinical_recruitment_application add disease_image varchar(2048) COLLATE utf8_bin COMMENT '病例图片';