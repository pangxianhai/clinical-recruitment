package com.andy.recruitment.web.controller.recruitment.request;

import com.soyoung.base.base.BaseQueryReq;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 招募项目查询参数
 *
 * @author 庞先海 2020-01-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RecruitmentQueryReq extends BaseQueryReq implements Serializable {

    private static final long serialVersionUID = - 7715495107118381705L;

    /**
     * 地区文本
     */
    @Length(max = 64)
    private String addressText;
    /**
     * 查询文本
     */
    @Length(max = 64)
    private String queryText;
    /**
     * 登记编号
     */
    @Length(max = 64)
    private String registerCode;
    /**
     * 标题
     */
    @Length(max = 64)
    private String title;
    /**
     * 类目
     */
    private Integer category;
    /**
     * 适应症状
     */
    @Length(max = 64)
    private String indication;
    /**
     * 启始时间开始
     */
    @Length(max = 64)
    private String startTimeBegin;
    /**
     * 启始时间结束
     */
    @Length(max = 64)
    private String startTimeEnd;
    /**
     * 截至时间开始
     */
    @Length(max = 64)
    private String stopTimeBegin;
    /**
     * 截至时间结束
     */
    @Length(max = 64)
    private String stopTimeEnd;
    /**
     * 招募状态
     */
    private Integer status;

}
