package com.andy.recruitment.doctor.mapper;

import com.andy.recruitment.doctor.model.DoctorInfoDO;
import com.andy.recruitment.doctor.model.DoctorQueryParam;
import com.xgimi.mybatis.paginator.Page;
import java.util.List;

/**
 * 医生信息Mapper
 *
 * @author 庞先海 2018-12-26
 */
public interface DoctorInfoMapper {

    /**
     * 医生信息查询
     *
     * @param queryParam 查询参数
     * @return 医生消息列表
     */
    List<DoctorInfoDO> select(DoctorQueryParam queryParam);

    /**
     * 医生信息分页查询
     *
     * @param queryParam 查询参数
     * @param page       分页参数
     * @return 医生消息列表
     */
    List<DoctorInfoDO> select(DoctorQueryParam queryParam, Page page);

    /**
     * 更新医生信息
     *
     * @param doctorInfoDO 更新后的医生信息
     * @return 更新数量
     */
    int update(DoctorInfoDO doctorInfoDO);

    /**
     * 添加医生信息
     *
     * @param doctorInfoDO 医生信息
     * @return 添加数量
     */
    int insert(DoctorInfoDO doctorInfoDO);
}
