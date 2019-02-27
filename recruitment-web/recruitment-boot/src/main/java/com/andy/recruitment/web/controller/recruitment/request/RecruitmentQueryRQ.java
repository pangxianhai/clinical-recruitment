package com.andy.recruitment.web.controller.recruitment.request;

import com.xgimi.base.BaseQueryRQ;

/**
 * 招募信息查询参数
 *
 * @author 庞先海 2019-02-06
 */
public class RecruitmentQueryRQ extends BaseQueryRQ {

    /**
     * 地区文本
     */
    private String addressText;
    /**
     * 查询文本
     */
    private String queryText;

    /**
     * 登记编号
     */
    private String registerCode;
    /**
     * 标题
     */
    private String title;
    /**
     * 适应症状
     */
    private String indication;
    /**
     * 启始时间开始
     */
    private String startTimeBegin;
    /**
     * 启始时间结束
     */
    private String startTimeEnd;
    /**
     * 截至时间开始
     */
    private String stopTimeBegin;
    /**
     * 截至时间结束
     */
    private String stopTimeEnd;
    /**
     * 招募状态
     */
    private Integer status;

    public String getAddressText() {
        return addressText;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
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

    public String getStartTimeBegin() {
        return startTimeBegin;
    }

    public void setStartTimeBegin(String startTimeBegin) {
        this.startTimeBegin = startTimeBegin;
    }

    public String getStartTimeEnd() {
        return startTimeEnd;
    }

    public void setStartTimeEnd(String startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }

    public String getStopTimeBegin() {
        return stopTimeBegin;
    }

    public void setStopTimeBegin(String stopTimeBegin) {
        this.stopTimeBegin = stopTimeBegin;
    }

    public String getStopTimeEnd() {
        return stopTimeEnd;
    }

    public void setStopTimeEnd(String stopTimeEnd) {
        this.stopTimeEnd = stopTimeEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
