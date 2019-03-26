package com.andy.recruitment.web.controller.recruitment.response;

import java.io.Serializable;

/**
 * 申请结果
 *
 * @author 庞先海 2019-03-25
 */
public class ApplicationResultVO implements Serializable {

    /**
     * 是否成功
     */
    private Boolean success;

    /**
     * 跳转地址
     */
    private String redirectUrl;

    public ApplicationResultVO() {
        this.success = true;
    }

    public ApplicationResultVO(String redirectUrl) {
        this.success = false;
        this.redirectUrl = redirectUrl;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }


}
