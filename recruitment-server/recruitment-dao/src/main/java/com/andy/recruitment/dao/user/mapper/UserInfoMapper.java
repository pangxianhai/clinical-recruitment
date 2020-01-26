package com.andy.recruitment.dao.user.mapper;

import com.andy.recruitment.dao.user.entity.UserInfoDO;
import com.andy.recruitment.dao.user.entity.UserQuery;
import com.soyoung.base.mybatis.paginator.Page;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 用户信息Mapper
 *
 * @author 庞先海 2018-12-28
 */
@Repository
public interface UserInfoMapper {

    /**
     * 分页查询用户信息
     *
     * @param queryParam 查询参数
     * @param page       分页参数
     * @return 用户信息
     */
    List<UserInfoDO> select(UserQuery queryParam, Page page);

    /**
     * 用户信息查询
     *
     * @param queryParam 查询参数
     * @return 用户信息
     */
    List<UserInfoDO> select(UserQuery queryParam);

    /**
     * 插入用户信息
     *
     * @param userInfoDO 用户信息
     * @return 插入数量
     */
    int insert(UserInfoDO userInfoDO);

    /**
     * 更新用户信息
     *
     * @param userInfoDO 用户信息
     * @return 更新数量
     */
    int update(UserInfoDO userInfoDO);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     * @return 删除数量
     */
    int delete(Long userId);

}
