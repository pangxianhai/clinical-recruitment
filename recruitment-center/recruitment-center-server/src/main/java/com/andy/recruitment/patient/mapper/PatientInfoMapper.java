package com.andy.recruitment.patient.mapper;

import com.andy.recruitment.patient.model.PatientInfoDO;
import com.andy.recruitment.patient.model.PatientQueryParam;
import com.xgimi.mybatis.paginator.Page;
import java.util.List;

/**
 * 患者信息Mapper
 *
 * @author 庞先海 2018-12-27
 */
public interface PatientInfoMapper {

    /**
     * 患者信息查询
     *
     * @param queryParam 查询参数
     * @return 患者信息
     */
    List<PatientInfoDO> select(PatientQueryParam queryParam);

    /**
     * 分页查询患者信息
     *
     * @param queryParam 查询参数
     * @param page       分页信息
     * @return 患者信息
     */
    List<PatientInfoDO> select(PatientQueryParam queryParam, Page page);

    /**
     * 更新患者信息
     *
     * @param patientInfoDO 患者信息
     * @return 更新数量
     */
    int update(PatientInfoDO patientInfoDO);

    /**
     * 插入患者信息
     *
     * @param patientInfoDO 患者信息
     * @return 插入数量
     */
    int insert(PatientInfoDO patientInfoDO);

}
