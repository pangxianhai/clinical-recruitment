package com.andy.recruitment.biz.hospital.service;

import com.andy.recruitment.api.hospital.response.HospitalRes;
import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import com.andy.spring.page.PageResult;
import com.andy.spring.page.Paginator;
import java.util.List;
import java.util.Map;

/**
 * 机构服务
 *
 * @author 庞先海 2020-01-30
 */
public interface HospitalService {

    /**
     * 添加机构信息
     *
     * @param hospitalDo 机构信息
     * @param operator   操作人
     */
    void addHospital(HospitalDO hospitalDo, String operator);

    /**
     * 更新机构信息
     *
     * @param hospitalDo 机构信息
     * @param operator   操作人
     */
    void updateHospital(HospitalDO hospitalDo, String operator);

    /**
     * 分页查询机构信息
     *
     * @param query     查询参数
     * @param paginator 分页参数
     * @return 机构信息分页查询结果
     */
    PageResult<HospitalRes> getHospital(HospitalQuery query, Paginator paginator);

    /**
     * 通过机构ID列表批量查询机构信息
     *
     * @param hospitalIdList 机构ID列表
     * @return 机构信息
     */
    List<HospitalDO> getHospitalByIdList(List<Long> hospitalIdList);

    /**
     * 通过机构ID列表批量查询机构信息
     *
     * @param hospitalIdList 机构ID列表
     * @return 机构信息
     */
    Map<Long, HospitalRes> getHospitalRes(List<Long> hospitalIdList);

    /**
     * 通过机构ID查询机构信息
     *
     * @param hospitalId 机构ID
     * @return 机构信息
     */
    HospitalDO getHospitalById(Long hospitalId);
}
