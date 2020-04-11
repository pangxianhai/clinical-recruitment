package com.andy.recruitment.common.reference.constant;

import com.andy.spring.type.BaseType;
import java.util.List;

/**
 * 推荐人状态
 *
 * @author 庞先海 2020-02-15
 */
public class ReferenceStatus extends BaseType {

    private static final long serialVersionUID = 822724782920861453L;

    public static final ReferenceStatus FREEZE = new ReferenceStatus(0, "冻结");

    public static final ReferenceStatus UNAUDITED = new ReferenceStatus(1, "待审核");

    public static final ReferenceStatus REFUSE = new ReferenceStatus(2, "审核拒绝");

    public static final ReferenceStatus ADOPT = new ReferenceStatus(3, "审核通过");


    public ReferenceStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<ReferenceStatus> getValues() {
        return BaseType.getValues(ReferenceStatus.class);
    }

    public static ReferenceStatus parse(Integer code) {
        return BaseType.parse(ReferenceStatus.getValues(), code);
    }

}
