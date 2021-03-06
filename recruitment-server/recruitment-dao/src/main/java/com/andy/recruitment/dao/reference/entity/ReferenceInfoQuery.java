package com.andy.recruitment.dao.reference.entity;

import com.andy.recruitment.common.reference.constant.ReferenceStatus;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * 推荐人查询参数
 *
 * @author 庞先海 2020-01-27
 */
@Data
public class ReferenceInfoQuery implements Serializable {

    private static final long serialVersionUID = - 2442097856732489920L;
    /**
     * 推荐人ID
     */
    private Long referenceId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户ID列表
     */
    private List<Long> userIdList;
    /**
     * 医院ID
     */
    private Long hospitalId;
    /**
     * 科室ID
     */
    private Long departmentId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 手机号码模糊查询
     */
    private String phoneLike;
    /**
     * 推荐人状态
     */
    private ReferenceStatus status;
}
