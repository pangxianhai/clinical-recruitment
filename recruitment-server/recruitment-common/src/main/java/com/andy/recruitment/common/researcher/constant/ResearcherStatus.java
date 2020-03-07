package com.andy.recruitment.common.researcher.constant;

import com.andy.spring.type.BaseType;
import java.util.List;

/**
 * 研究员状态
 *
 * @author 庞先海 2020-02-02
 */
public class ResearcherStatus extends BaseType {

    private static final long serialVersionUID = 217574523126780636L;

    public static final ResearcherStatus NORMAL = new ResearcherStatus(1, "正常");

    public static final ResearcherStatus NON_EXAMINE = new ResearcherStatus(2, "待审核");

    public static final ResearcherStatus FROZEN = new ResearcherStatus(3, "冻结");

    public ResearcherStatus(int code, String desc) {
        super(code, desc);
    }

    public static List<ResearcherStatus> getValues() {
        return BaseType.getValues(ResearcherStatus.class);
    }

    public static ResearcherStatus parse(Integer code) {
        return BaseType.parse(ResearcherStatus.getValues(), code);
    }

}
