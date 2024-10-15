package com.oyb.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆请求体
 */
@Data
public class UserLoginRequest implements Serializable {


    private String userAccount;

    private String userPassword;

}
