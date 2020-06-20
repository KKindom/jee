package com.itheima.web.client;

import com.itheima.dao.UserMapper;
import com.itheima.model.domain.User;
import com.itheima.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.util.Date;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;

    //跳转到注册界面
    @GetMapping(value = "/register")
    public String register(){
        return "comm/register";
    }

    //注册用户
    @PostMapping(value = "/add")
    public String add(HttpServletRequest request, @RequestParam String username,@RequestParam String password,@RequestParam String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setDate(new Date());
        User existUser = userMapper.findUserByName(user.getUsername());
            if(existUser != null)
            {
                //提示用户名已存在
                System.out.println("用户已存在，注册失败！");
                request.setAttribute("error","用户名重复！！");
                return "comm/register";
            }else
                {
                userService.addUser(user);
                //提示注册成功

                System.out.println("注册成功");
                return "comm/login_1";
            }
//text
        }


}
