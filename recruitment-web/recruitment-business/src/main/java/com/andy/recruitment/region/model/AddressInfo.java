package com.andy.recruitment.region.model;

import java.io.Serializable;

/**
 * 地址信息
 *
 * @author 庞先海 2019-01-21
 */
public class AddressInfo implements Serializable {

    /**
     * 省
     */
    private Region province;
    /**
     * 市
     */
    private Region city;
    /**
     * 区
     */
    private Region district;

    public Region getProvince() {
        return province;
    }

    public void setProvince(Region province) {
        this.province = province;
    }

    public Region getCity() {
        return city;
    }

    public void setCity(Region city) {
        this.city = city;
    }

    public Region getDistrict() {
        return district;
    }

    public void setDistrict(Region district) {
        this.district = district;
    }
}
