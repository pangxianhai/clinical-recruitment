package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;

/**
 * 招募申请信息更新参数
 *
 * @author 庞先海 2019-01-25
 */
public class RecruitmentApplicationUpdateRQ implements Serializable {

    /**
     * 申请状态
     */
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
