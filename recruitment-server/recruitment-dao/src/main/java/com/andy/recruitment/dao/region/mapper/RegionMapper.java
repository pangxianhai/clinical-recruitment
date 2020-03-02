package com.andy.recruitment.dao.region.mapper;

import com.andy.recruitment.dao.region.entity.RegionDO;
import com.andy.spring.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 地区mapper接口
 *
 * @author 庞先海 2018-12-26
 */
@Repository
public interface RegionMapper {

    /**
     * 查询
     *
     * @param regionDO 查询参数
     * @return 地区列表
     * @author 庞先海 2017-07-17
     */
    List<RegionDO> select(RegionDO regionDO);

    /**
     * 分页查询
     *
     * @param regionDO 查询参数
     * @param page     分页查询
     * @return 地区列表
     * @author 庞先海 2017-07-17
     */
    List<RegionDO> select(RegionDO regionDO, Page page);

    /**
     * 插入
     *
     * @param regionDO 待插入的的地区信息
     * @return 影响条数
     * @author 庞先海 2017-07-17
     */
    Long insert(RegionDO regionDO);

    /**
     * 更新
     *
     * @param regionDO 待更新的的地区信息
     * @return 影响条数
     * @author 庞先海 2017-07-17
     */
    int update(RegionDO regionDO);
}
