package com.andy.recruitment.manager.weixin.entity;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 微信返回的用户信息
 *
 * @author 庞先海 2019-01-13
 */
@Data
public class WxUserInfo implements Serializable {

    private static final long serialVersionUID = 7515257334312338212L;

    public static final String SUCCESS_CODE = "0";

    /**
     * 错误码
     */
    @SerializedName(value = "errCode", alternate = "errcode")
    private String errCode;

    @SerializedName(value = "openId", alternate = "openid")
    private String openId;

    private String nickname;

    private String sex;

    private String country;

    private String province;

    private String city;

    @SerializedName(value = "headImgUrl", alternate = "headimgurl")
    private String headImgUrl;

    private List<String> privilege;

    private String unionid;
}
