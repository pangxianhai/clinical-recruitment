package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.web.controller.user.request.UserQueryRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.commons.page.PageResult;
import com.xgimi.converter.MyParameter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 管理员controller
 *
 * @author 庞先海 2019-02-23
 */
@Controller
@RequestMapping("/user/manager")
public class ManageController {

    private final UserAO userAO;

    @Autowired
    public ManageController(UserAO userAO) {
        this.userAO = userAO;
    }

    @RequestMapping(value = "/list-pc", method = RequestMethod.GET)
    public String manageList(Map<String, Object> model) {
        return "user/manageList-pc";
    }

    @RequestMapping(value = "/listPcInfo", method = RequestMethod.GET)
    public String manageListInfo(@MyParameter UserQueryRQ queryRQ, Map<String, Object> model) {
        UserQueryParam queryParam = UserUtil.transformUserQueryParam(queryRQ);
        queryParam.setUserType(UserType.ADMIN);
        PageResult<UserInfo> pageResult = this.userAO.getUserInfo(queryParam, queryRQ.getPaginator());
        List<UserInfoVO> userInfoVOList = UserUtil.transformUserInfoVO(pageResult.getData());
        model.put("userInfoVOList", userInfoVOList);
        model.put("paginator", pageResult.getPaginator());
        return "user/manageLoginInfo-pc";
    }
}
