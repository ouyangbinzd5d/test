package com.oyb.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oyb.usercenter.model.domain.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author Administrator
 * &#064;description  针对表【user(用户)】的数据库操作Service
 * &#064;createDate  2024-03-11 15:20:18
 */
public interface UserService extends IService<User> {


    /**
     * 用户注册
     *
     * @param userAccount   用户注册
     * @param userPassword  用户密码
     * @param checkPassword 用户校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     *用户登陆
     * @param userAccount   用户注册
     * @param userPassword  用户密码
     * @return 返回脱敏后的用户信息
     */
    User userlogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 根据标签查询用户
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);
}
