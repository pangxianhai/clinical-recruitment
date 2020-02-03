package com.andy.recruitment.biz.user.service;

import com.andy.recruitment.dao.user.dao.UserDAO;
import com.andy.recruitment.dao.user.entity.UserInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务接口实现
 *
 * @author 庞先海 2020-02-02
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {this.userDAO = userDAO;}

    @Override
    public UserInfoDO getUserInfoByPhone(String phone) {
        return this.userDAO.getUserInfoByPhone(phone);
    }
}
