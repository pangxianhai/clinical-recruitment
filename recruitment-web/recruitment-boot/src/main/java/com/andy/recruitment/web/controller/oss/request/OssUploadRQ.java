package com.andy.recruitment.web.controller.oss.request;

import java.io.Serializable;

/**
 * 上传参数
 *
 * @author 庞先海 2019-04-13
 */
public class OssUploadRQ implements Serializable {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
