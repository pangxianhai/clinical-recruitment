package com.andy.recruitment.dao.patient.mapper;

import com.andy.recruitment.dao.patient.entity.PatientInfoDO;
import com.andy.recruitment.dao.patient.entity.PatientQuery;
import com.soyoung.base.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 患者信息Mapper
 *
 * @author 庞先海 2018-12-27
 */
@Repository
public interface PatientInfoMapper {

    /**
     * 患者信息查询
     *
     * @param queryParam 查询参数
     * @return 患者信息
     */
    List<PatientInfoDO> select(PatientQuery queryParam);

    /**
     * 分页查询患者信息
     *
     * @param queryParam 查询参数
     * @param page       分页信息
     * @return 患者信息
     */
    List<PatientInfoDO> select(PatientQuery queryParam, Page page);

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
