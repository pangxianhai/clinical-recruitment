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
     * 适应症状
     */
    private String indication;
    /**
     * 查询文本
     */
    private String queryText;

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
}
