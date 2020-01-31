package com.andy.recruitment.web.controller.researcher.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 研究员添加参数
 *
 * @author 庞先海 2020-01-31
 */
@Data
public class ResearcherAddReq implements Serializable {

    private static final long serialVersionUID = - 3580079645942042249L;

    /**
     * 微信openId
     */
    private String openId;
    /**
     * 微信昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 执业机构
     */
    private String medicalInstitution;
    /**
     * 执业类别
     */
    private String medicalCategory;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 备注信息
     */
    private String remark;
}
