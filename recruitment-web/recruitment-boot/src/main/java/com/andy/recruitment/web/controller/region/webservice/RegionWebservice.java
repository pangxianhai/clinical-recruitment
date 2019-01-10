package com.andy.recruitment.web.controller.region.webservice;

import com.andy.recruitment.region.ao.RegionAO;
import com.andy.recruitment.region.model.Region;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 地区字典
 *
 * @author 庞先海 2018-12-27
 */
@RestController
@RequestMapping("/region")
public class RegionWebservice {

    private final RegionAO regionAO;

    @Autowired
    public RegionWebservice(RegionAO regionAO) {
        this.regionAO = regionAO;
    }

    @RequestMapping(value = "/parent/{regionId:\\d+}", method = {RequestMethod.GET})
    public List<Region> getRegionByParentId(@PathVariable Long regionId) {
        return this.regionAO.getRegionByParentId(regionId);
    }

    @RequestMapping(value = "/parent", method = {RequestMethod.GET})
    public List<Region> getRegionByParentId() {
        return this.regionAO.getRegionByParentId(null);
    }

}
