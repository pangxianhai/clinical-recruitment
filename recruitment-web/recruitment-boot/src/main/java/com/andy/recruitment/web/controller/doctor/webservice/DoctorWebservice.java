package com.andy.recruitment.web.controller.doctor.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.constant.Gender;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.andy.recruitment.web.controller.doctor.request.DoctorQueryRQ;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.context.ServletContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医生webservice接口
 *
 * @author 庞先海 2019-01-20
 */
@RestController
@RequestMapping("/doctor")
public class DoctorWebservice {

    private final DoctorAO doctorAO;

    private final RegionAO regionAO;

    private final UserAO userAO;

    @Autowired
    public DoctorWebservice(DoctorAO doctorAO, RegionAO regionAO, UserAO userAO) {
        this.doctorAO = doctorAO;
        this.regionAO = regionAO;
        this.userAO = userAO;
    }

    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public boolean register(@RequestBody DoctorAddRQ doctorAddRQ) {
        UserInfo userInfo = this.userAO.getUserInfoByPhone(doctorAddRQ.getPhone());
        UserInfo addUserInfo = DoctorUtil.transformUserInfo(doctorAddRQ);
        if (null == userInfo) {
            Long userId = this.userAO.addUserInfo(addUserInfo, doctorAddRQ.getName());
            addUserInfo.setUserId(userId);
            DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(doctorAddRQ, regionAO);
            doctorInfo.setUserId(addUserInfo.getUserId());
            this.doctorAO.addDoctorInfo(doctorInfo, doctorAddRQ.getName());
        } else {
            addUserInfo.setUserId(userInfo.getUserId());
            this.userAO.updateUserInfo(addUserInfo, addUserInfo.getRealName());
            DoctorInfo existDoctor = this.doctorAO.getDoctorInfoByUserId(addUserInfo.getUserId());
            DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(doctorAddRQ, regionAO);
            if (null == existDoctor) {
                doctorInfo.setUserId(addUserInfo.getUserId());
                this.doctorAO.addDoctorInfo(doctorInfo, doctorAddRQ.getName());
            } else {
                doctorInfo.setDoctorId(existDoctor.getDoctorId());
                this.doctorAO.updateDoctorInfo(doctorInfo, doctorAddRQ.getName());
            }
        }
        this.userAO.saveUserInfoCookie(addUserInfo, ServletContext.getResponse());
        return true;
    }

    @Login
    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    public PageResult<DoctorInfoVO> getDoctorInfo(DoctorQueryRQ queryRQ) {
        DoctorQueryParam queryParam = DoctorUtil.transformDoctorQueryParam(queryRQ);
        PageResult<DoctorInfo> pageResult = this.doctorAO.getDoctorInfo(queryParam, queryRQ.getPaginator());
        List<DoctorInfoVO> doctorInfoVOList = DoctorUtil.transformDoctorInfoVO(pageResult.getData());
        for (DoctorInfoVO doctorInfoVO : doctorInfoVOList) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(doctorInfoVO.getUserId());
            doctorInfoVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
        }
        return new PageResult<>(doctorInfoVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/add.json", method = RequestMethod.POST)
    public boolean add(@RequestBody DoctorAddRQ addRQ) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();

        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(addRQ.getPhone());
        userInfo.setRealName(addRQ.getName());
        userInfo.setGender(Gender.parse(addRQ.getGender()));
        Long userId = this.userAO.addUserInfo(userInfo, loginInfo.getRealName());

        try {
            DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(addRQ, regionAO);
            doctorInfo.setUserId(userId);
            this.doctorAO.addDoctorInfo(doctorInfo, loginInfo.getRealName());
        } catch (Exception e) {
            this.userAO.delete(userId);
            throw e;
        }
        return true;
    }
}
