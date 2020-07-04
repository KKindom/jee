package com.kkindom_blog.web.client;

import com.kkindom_blog.dao.AuthorityMapper;
import com.kkindom_blog.dao.UserMapper;
import com.kkindom_blog.model.domain.Authority;
import com.kkindom_blog.model.domain.User;
import com.kkindom_blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class RegisterController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private AuthorityMapper authorityMapper;
    //跳转到注册界面
    @GetMapping(value = "/register")
    public String register(){
        return "comm/register";
    }

    //注册用户
    @PostMapping(value = "/add")
    public String add(HttpServletRequest request, @RequestParam String username,@RequestParam String password,@RequestParam String email) {
        String hashpassword = encoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashpassword);
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
                System.out.println("密码："+ password + " 加密后的密码:"+ hashpassword);
                //提示注册成功
                User user1 = userMapper.findUserByName(username);
                System.out.println(user1.toString());
                Authority authority = new Authority();
                authority.setUser_id(user1.getId());
                authority.setAuthority_id(1);
                authorityMapper.authority(authority);
                System.out.println("注册成功");
                return "comm/login_1";
            }
    }


}
