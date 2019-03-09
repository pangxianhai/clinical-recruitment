package com.andy.recruitment.web.controller.doctor.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.request.DoctorQueryRQ;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.user.response.UserInfoVO;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.context.ServletContext;
import com.xgimi.converter.MyParameter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 医生controller
 *
 * @author 庞先海 2019-01-20
 */
@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final UserAO userAO;

    private final DoctorAO doctorAO;

    private final RegionAO regionAO;

    @Autowired
    public DoctorController(UserAO userAO, DoctorAO doctorAO, RegionAO regionAO) {
        this.userAO = userAO;
        this.doctorAO = doctorAO;
        this.regionAO = regionAO;
    }

    @Login
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(String redirectURL, Integer userType, Map<String, Object> model) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        if (null != userInfo.getPhone()) {
            return "redirect:" + redirectURL;
        } else {
            model.put("redirectURL", redirectURL);
            model.put("userType", userType);
            return "doctor/register";
        }
    }

    @Login
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public String doctorInfo(Map<String, Object> model) {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        UserInfoVO userInfoVO = UserUtil.transformUserInfoVO(this.userAO.getUserInfoByUserId(loginInfo.getUserId()));
        DoctorInfoVO doctorInfoVO = DoctorUtil.transformDoctorInfoVO(
            this.doctorAO.getDoctorInfoByUserId(loginInfo.getUserId()));
        doctorInfoVO.setAddress(this.regionAO.parseAddressName(doctorInfoVO.getProvinceId(), doctorInfoVO.getCityId(),
                                                               doctorInfoVO.getDistrictId()));
        model.put("userInfoVO", userInfoVO);
        model.put("doctorInfoVO", doctorInfoVO);
        return "doctor/me";
    }

    @Login
    @RequestMapping(value = "/list-pc", method = RequestMethod.GET)
    public String doctorListPc(Map<String, Object> model) {
        return "doctor/list-pc";
    }

    @Login
    @RequestMapping(value = "/listPcInfo", method = RequestMethod.GET)
    public String doctorListPcInfo(@MyParameter DoctorQueryRQ queryRQ, Map<String, Object> model) {
        DoctorQueryParam queryParam = DoctorUtil.transformDoctorQueryParam(queryRQ);
        PageResult<DoctorInfo> pageResult = this.doctorAO.getDoctorInfo(queryParam, queryRQ.getPaginator());
        List<DoctorInfoVO> doctorInfoVOList = DoctorUtil.transformDoctorInfoVO(pageResult.getData());
        for (DoctorInfoVO doctorInfoVO : doctorInfoVOList) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(doctorInfoVO.getUserId());
            doctorInfoVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
            String address = this.regionAO.parseAddressName(doctorInfoVO.getProvinceId(), doctorInfoVO.getCityId(),
                                                            doctorInfoVO.getDistrictId());
            doctorInfoVO.setAddress(address);
        }
        model.put("doctorInfoVOList", doctorInfoVOList);
        model.put("paginator", pageResult.getPaginator());
        return "doctor/listInfo-pc";
    }

}
