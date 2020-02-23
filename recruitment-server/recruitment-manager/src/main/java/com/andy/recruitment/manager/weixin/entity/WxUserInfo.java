package com.andy.recruitment.manager.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(name = "errcode")
    private String errCode;

    @JSONField(name = "openid")
    private String openId;

    private String nickname;

    private String sex;

    private String country;

    private String province;

    private String city;

    @JSONField(name = "headimgurl")
    private String headImgUrl;

    private List<String> privilege;

    private String unionid;
}
