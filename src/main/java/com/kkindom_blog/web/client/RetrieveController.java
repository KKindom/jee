package com.kkindom_blog.web.client;

import com.kkindom_blog.dao.UserMapper;
import com.kkindom_blog.model.domain.User;
import com.kkindom_blog.service.impl.UserServiceImpl;
import com.kkindom_blog.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RetrieveController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    MailUtils mailUtils;

    //跳转到找回密码界面
    @GetMapping(value = "/retrieve")
    public String retrieve(){
        return "comm/retrieve";
    }

    //重置用户密码并把密码发到用户邮箱上
    @PostMapping(value = "/reset")
    public String add(HttpServletRequest request, @RequestParam String username, @RequestParam String email) {
        User existUser = userMapper.findUserByName(username);
        if(existUser != null)
        {
            System.out.println(existUser.getEmail());
            System.out.println(email);
            if(existUser.getEmail().equals(email)){
                userService.updateUser(existUser);
                mailUtils.sendSimpleEmail(email,"个人博客系统密码找回","您的密码已重置为：123456");
                System.out.println("密码已重置，请查看自己的邮箱接受密码信息！");
                request.setAttribute("error","密码已重置，请查看自己的邮箱接受密码信息！");
                return "comm/retrieve";
            }else {
                System.out.println("输入邮箱与该用户邮箱不同！");
                request.setAttribute("error","输入邮箱与该用户邮箱不同！");
                return "comm/retrieve";
            }
        }else{
            System.out.println("用户名不存在！");
            request.setAttribute("error","用户名不存在！");
            return "comm/retrieve";
        }
    }
}
