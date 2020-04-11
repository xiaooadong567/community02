package com.xiaoadong.community.controller;

import com.xiaoadong.community.model.User;
import com.xiaoadong.community.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;


    @GetMapping({"/", "index"})
    public String index(HttpServletRequest request) {
        //验证cookies信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null) { // 判断不为空 避免空指针
            for (Cookie cookie : cookies) { // 循环cookies数组
                if (cookie.getName().equals("token")) { // 判断cookie信息
                    String token = cookie.getValue(); // 获取cookies
                    User user = userMapper.findByToken(token); // mapper接口
                    if (user != null) {
                        //添加到session中
                        request.getSession().setAttribute("user", user);
                    }

                    break;
                }
            }
        }
        return "index";
    }
}
