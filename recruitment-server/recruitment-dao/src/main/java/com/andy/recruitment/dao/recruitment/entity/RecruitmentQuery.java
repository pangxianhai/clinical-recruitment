package com.andy.recruitment.dao.recruitment.entity;

import com.andy.recruitment.dao.recruitment.constant.RecruitmentCategory;
import com.andy.recruitment.dao.recruitment.constant.RecruitmentStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 招募信息查询参数
 *
 * @author 庞先海 2018-12-29
 */
@Data
public class RecruitmentQuery implements Serializable {

    private static final long serialVersionUID = - 4211297996433304041L;

    /**
     * 搜索文本
     */
    private String queryText;
    /**
     * 招募ID
     */
    private Long recruitmentId;
    /**
     * 登记编号
     */
    private String registerCode;
    /**
     * 类目
     */
    private RecruitmentCategory category;
    /**
     * 标题
     */
    private String title;
    /**
     * 适应症状
     */
    private String indication;
    /**
     * 省Id
     */
    private Long provinceId;
    /**
     * 市Id
     */
    private Long cityId;
    /**
     * 区Id
     */
    private Long districtId;
    /**
     * 启始时间开始
     */
    private LocalDateTime startTimeBegin;
    /**
     * 启始时间结束
     */
    private LocalDateTime startTimeEnd;
    /**
     * 截至时间开始
     */
    private LocalDateTime stopTimeBegin;
    /**
     * 截至时间结束
     */
    private LocalDateTime stopTimeEnd;
    /**
     * 招募状态
     */
    private RecruitmentStatus status;
}
