package com.andy.recruitment.api.reference.response;

import com.andy.recruitment.api.user.response.UserInfoRes;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 推荐人详细信息
 *
 * @author 庞先海 2020-02-23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ReferenceDetailInfoRes extends ReferenceInfoRes {

    private static final long serialVersionUID = 8403795506642967016L;
    /**
     * 用户信息
     */
    private UserInfoRes userInfoRes;
}
