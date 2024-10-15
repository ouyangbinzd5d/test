package com.oyb.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyb.usercenter.commmon.BaseResponse;
import com.oyb.usercenter.commmon.ErrorCode;
import com.oyb.usercenter.commmon.ResultUtils;
import com.oyb.usercenter.exception.BusinessException;
import com.oyb.usercenter.model.domain.User;
import com.oyb.usercenter.model.domain.request.UserLoginRequest;
import com.oyb.usercenter.model.domain.request.UserRegisterRequest;
import com.oyb.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.oyb.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.oyb.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author oyb
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

/**
     * 用户注册
     * @param userRegisterRequest
     * @return*/


    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if(userRegisterRequest == null){
           // return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return null;
        }
       long result = userService.userRegister(userAccount,userPassword,checkPassword);
        return  ResultUtils.success(result);

    }

/**
     * 用户登陆
     * @param userLoginRequest
     * @param request
     * @return*/


    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        User result = userService.userlogin(userAccount,userPassword,request);
        return  ResultUtils.success(result);
    }

/**
     * 查询用户
     * @param username
     * @param request
     * @return*/


    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request){
        //仅管理员可查询
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list =  userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

/**
     * 删除用户
     * @param id
     * @param request
     * @return*/


    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        //仅管理员可删除
        if(!isAdmin(request)){
            return null;
        }
        if(id <= 0){
            return null;
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

/**
     * 是否为管理员
     *
     * @param request
     * @return*/


    private boolean isAdmin(HttpServletRequest request){
        Object userobj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userobj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
