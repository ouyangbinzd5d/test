package com.oyb.usercenter.service;

import com.oyb.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;


    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("胡永宏");
        user.setUserAccount("123456");
        user.setAvatarUrl("https://xiaolincoding.com/logo.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        assertTrue(result);

    }

    @Test
    void userRegister() {
        //账号不能重复
        String userAccount = "ouyangbin";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        //账号不能包含特殊字符
        userAccount = "ou yang";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-5,result);

        //密码和校验密码不同
        userAccount = "ouyang";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-6,result);


/*
        userAccount = "yu";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-2,result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-3,result);
        userAccount = "1234";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-4,result);
        userAccount = "y upi";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-5,result);
        userAccount = "ouyang";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-6,result);
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);
*/



    }

    //根据标签查询用户
    @Test
    public void testSearchUsersByTags(){
        List<String> tagNameList = Arrays.asList("java", "python");
        List<User> userList = userService.searchUsersByTags(tagNameList);
        Assert.assertNotNull(userList);

    }



}