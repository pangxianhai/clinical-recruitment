package com.andy.recruitment.web.controller.recruitment.request;

import java.io.Serializable;

/**
 * 研究中心添加参数
 *
 * @author 庞先海 2019-02-06
 */
public class ResearchCenterAddRQ implements Serializable {

    /**
     * 研究中心名称
     */
    private String name;

    /**
     * 研究中心地址
     */
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
