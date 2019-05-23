package com.andy.recruitment.web.controller.user.webservice;

import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.constant.UserType;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.user.model.UserQueryParam;
import com.andy.recruitment.web.controller.user.request.ManageAddRQ;
import com.andy.recruitment.web.controller.user.request.ManagerUpdateRQ;
import com.andy.recruitment.web.controller.user.request.MangeLoginRQ;
import com.andy.recruitment.web.controller.user.request.UserQueryRQ;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.asserts.AssertUtil;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理員接口
 *
 * @author 庞先海 2019-03-03
 */
@RestController
@RequestMapping("/user/manager")
public class ManageWebservice {

    private final UserAO userAO;

    @Autowired
    public ManageWebservice(UserAO userAO) {
        this.userAO = userAO;
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Boolean manageAdd(@RequestBody ManageAddRQ addRQ) {
        UserInfo userInfo = UserUtil.transformUserInfo(addRQ);
        UserInfo existUserInfo = this.userAO.getUserInfoByPhone(addRQ.getPhone());
        AssertUtil.assertBoolean(null == existUserInfo, () -> {
            throw new BusinessException(BusinessErrorCode.USER_PHONE_HAS_REGISTER);
        });
        this.userAO.registerUser(userInfo, ServletContext.getLoginUname());
        return true;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserInfoVO manageLogin(@RequestBody MangeLoginRQ loginRQ) {
        UserInfo userInfo = this.userAO.loginByPhone(loginRQ.getPhone(), loginRQ.getPassword());
        return UserUtil.transformUserInfoVO(userInfo);
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<UserInfoVO> getManage(@MyParameter UserQueryRQ queryRQ) {
        UserQueryParam queryParam = UserUtil.transformUserQueryParam(queryRQ);
        queryParam.setUserType(UserType.ADMIN);
        PageResult<UserInfo> pageResult = this.userAO.getUserInfo(queryParam, queryRQ.getPaginator());
        List<UserInfoVO> userInfoVOList = UserUtil.transformUserInfoVO(pageResult.getData());
        return new PageResult<>(userInfoVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/{userId:\\d+}", method = RequestMethod.GET)
    public UserInfoVO getManagerInfo(@PathVariable Long userId) {
        UserInfo userInfo = this.userAO.getUserInfoByUserId(userId);
        return UserUtil.transformUserInfoVO(userInfo);
    }

    @Login
    @RequestMapping(value = "/{userId:\\d+}", method = RequestMethod.PUT)
    public boolean updateManagerInfo(@PathVariable Long userId, @RequestBody ManagerUpdateRQ updateRQ) {
        String operator = UserUtil.getOperator(null);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setRealName(updateRQ.getRealName());
        userInfo.setGender(Gender.parse(updateRQ.getGender()));
        this.userAO.updateUserInfo(userInfo, operator);
        return true;
    }
}
