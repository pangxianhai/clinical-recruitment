package com.andy.recruitment.web.controller.region.webservice;

import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.web.controller.region.request.RegionQueryRQ;
import com.andy.recruitment.web.controller.region.response.RegionVO;
import com.andy.recruitment.web.controller.region.util.RegionUtil;
import com.xgimi.auth.Login;
import com.xgimi.commons.util.StringUtil;
import com.xgimi.converter.MyParameter;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 地区字典
 *
 * @author 庞先海 2019-03-23
 */
@Controller
@RequestMapping("/region")
public class RegionController {

    private final RegionAO regionAO;

    @Autowired
    public RegionController(RegionAO regionAO) {
        this.regionAO = regionAO;
    }

    @Login
    @RequestMapping(value = "/selectPc", method = RequestMethod.GET)
    public String regionSelectPc(@MyParameter RegionQueryRQ queryRQ, Map<String, Object> model) {
        String address = this.regionAO.parseAddressName(queryRQ.getProvinceId(), queryRQ.getCityId(),
                                                        queryRQ.getDistrictId());
        List<RegionVO> regionVOList = RegionUtil.buildSelectOptions(null, regionAO);
        model.put("regionVOList", regionVOList);
        if (StringUtil.isNotEmpty(address)) {
            model.put("address", address);
            model.put("provinceId", queryRQ.getProvinceId());
            model.put("cityId", queryRQ.getCityId());
            model.put("districtId", queryRQ.getDistrictId());
        }
        return "region/select-pc";
    }

}
