package com.andy.recruitment.web.controller.doctor.webservice;

import com.andy.recruitment.doctor.ao.DoctorAO;
import com.andy.recruitment.doctor.model.DoctorInfo;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.andy.recruitment.exception.BusinessErrorCode;
import com.andy.recruitment.exception.BusinessException;
import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.user.ao.UserAO;
import com.andy.recruitment.user.model.UserInfo;
import com.andy.recruitment.web.controller.doctor.request.DoctorAddRQ;
import com.andy.recruitment.web.controller.doctor.request.DoctorQueryRQ;
import com.andy.recruitment.web.controller.doctor.response.DoctorInfoVO;
import com.andy.recruitment.web.controller.doctor.util.DoctorUtil;
import com.andy.recruitment.web.controller.user.util.UserUtil;
import com.xgimi.auth.Login;
import com.xgimi.auth.LoginInfo;
import com.xgimi.commons.page.PageResult;
import com.xgimi.commons.util.asserts.AssertUtil;
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

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Long register(@RequestBody DoctorAddRQ doctorAddRQ) {
        DoctorInfo doctorInfo = DoctorUtil.transformDoctorInfo(doctorAddRQ, regionAO);
        UserInfo userInfo = DoctorUtil.transformUserInfo(doctorAddRQ);
        doctorInfo.setUserInfo(userInfo);
        String operator = UserUtil.getOperator(doctorAddRQ.getName());
        return this.doctorAO.registerDoctor(doctorInfo, operator).getUserId();
    }

    @Login
    @RequestMapping(value = "", method = RequestMethod.GET)
    public PageResult<DoctorInfoVO> getDoctorInfo(DoctorQueryRQ queryRQ) {
        DoctorQueryParam queryParam = DoctorUtil.transformDoctorQueryParam(queryRQ);
        PageResult<DoctorInfo> pageResult = this.doctorAO.getDoctorInfo(queryParam, queryRQ.getPaginator());
        List<DoctorInfoVO> doctorInfoVOList = DoctorUtil.transformDoctorInfoVO(pageResult.getData());
        for (DoctorInfoVO doctorInfoVO : doctorInfoVOList) {
            UserInfo userInfo = this.userAO.getUserInfoByUserId(doctorInfoVO.getUserId());
            doctorInfoVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
            doctorInfoVO.setAddress(
                this.regionAO.parseAddressName(doctorInfoVO.getProvinceId(), doctorInfoVO.getCityId(),
                                               doctorInfoVO.getDistrictId()));
        }
        return new PageResult<>(doctorInfoVOList, pageResult.getPaginator());
    }

    @Login
    @RequestMapping(value = "/currentInfo", method = RequestMethod.GET)
    public DoctorInfoVO getDoctorInfo() {
        LoginInfo loginInfo = ServletContext.getLoginInfo();
        DoctorInfo doctorInfo = this.doctorAO.getDoctorInfoByUserId(loginInfo.getUserId());
        AssertUtil.assertNull(doctorInfo, () -> {
            throw new BusinessException(BusinessErrorCode.OPERATE_ERROR);
        });
        DoctorInfoVO doctorInfoVO = DoctorUtil.transformDoctorInfoVO(doctorInfo);
        UserInfo userInfo = this.userAO.getUserInfoByUserId(loginInfo.getUserId());
        doctorInfoVO.setUserInfoVO(UserUtil.transformUserInfoVO(userInfo));
        doctorInfoVO.setAddress(this.regionAO.parseAddressName(doctorInfoVO.getProvinceId(), doctorInfoVO.getCityId(),
                                                               doctorInfoVO.getDistrictId()));
        return doctorInfoVO;
    }
}
