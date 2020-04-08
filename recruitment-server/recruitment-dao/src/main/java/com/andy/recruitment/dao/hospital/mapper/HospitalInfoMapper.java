package com.andy.recruitment.dao.hospital.mapper;

import com.andy.recruitment.dao.hospital.entity.HospitalDO;
import com.andy.recruitment.dao.hospital.entity.HospitalQuery;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 机构信息 Mapper 接口
 *
 * @author 庞先海 2020-01-27
 */
@Repository
public interface HospitalInfoMapper {

    /**
     * 机构信息分页查询
     *
     * @param query 查询参数
     * @param page  分页参数
     * @return 机构信息列表
     */
    List<HospitalDO> select(HospitalQuery query, Page page);

    /**
     * 机构信息查询
     *
     * @param query 查询参数
     * @return 机构信息列表
     */
    List<HospitalDO> select(HospitalQuery query);

    /**
     * 插入机构信息
     *
     * @param hospitalDo 机构信息
     * @return 插入数量
     */
    int insert(HospitalDO hospitalDo);

    /**
     * 更新机构信息
     *
     * @param hospitalDo 机构信息
     * @return 更新数量
     */
    int update(HospitalDO hospitalDo);
}
