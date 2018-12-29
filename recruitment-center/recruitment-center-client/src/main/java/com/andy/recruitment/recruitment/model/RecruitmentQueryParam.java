package com.andy.recruitment.recruitment.model;

import com.andy.recruitment.recruitment.constant.RecruitmentStatus;
import java.io.Serializable;
import java.util.Date;

/**
 * 招募信息查询参数
 *
 * @author 庞先海 2018-12-29
 */
public class RecruitmentQueryParam implements Serializable {

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
     * 标题
     */
    private String title;
    /**
     * 启始时间开始
     */
    private Date startTimeBegin;
    /**
     * 启始时间结束
     */
    private Date startTimeEnd;
    /**
     * 截至时间开始
     */
    private Date stopTimeBegin;
    /**
     * 截至时间结束
     */
    private Date stopTimeEnd;
    /**
     * 招募状态
     */
    private RecruitmentStatus status;

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    public Long getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(Long recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(Date startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public Date getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(Date startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public Date getStopTimeBegin() {
        return stopTimeBegin;
    }

    public void setStopTimeBegin(Date stopTimeBegin) {
        this.stopTimeBegin = stopTimeBegin;
    }

    public Date getStopTimeEnd() {
        return stopTimeEnd;
    }

    public void setStopTimeEnd(Date stopTimeEnd) {
        this.stopTimeEnd = stopTimeEnd;
    }

    public RecruitmentStatus getStatus() {
        return status;
    }

    public void setStatus(RecruitmentStatus status) {
        this.status = status;
    }
}
