package com.andy.recruitment.web.controller.user.request;

import java.io.Serializable;
import lombok.Data;

/**
 * 微信登录参数
 *
 * @author 庞先海 2020-02-22
 */
@Data
public class WxLoginReq implements Serializable {

    private static final long serialVersionUID = 2467426119527719235L;

    private String code;
}
