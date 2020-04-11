package com.andy.recruitment.api.admin.response;

import com.andy.recruitment.api.user.response.UserInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 管理员详情
 *
 * @author 庞先海 2020-04-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminInfoDetailRes extends AdminInfoRes {

    private static final long serialVersionUID = - 3598899591110300928L;

    /**
     * 用户信息
     */
    private UserInfoRes userInfoRes;
}
