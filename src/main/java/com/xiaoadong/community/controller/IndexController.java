package com.xiaoadong.community.controller;

import com.xiaoadong.community.dto.PaginationDTO;
import com.xiaoadong.community.dto.QuestionsDTO;
import com.xiaoadong.community.mapper.QuesstionMapper;
import com.xiaoadong.community.model.Question;
import com.xiaoadong.community.model.User;
import com.xiaoadong.community.mapper.UserMapper;
import com.xiaoadong.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuestionService questionService;


    @GetMapping({"/", "index"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size) {

        //获取user信息 验证cookies信息
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

        //展示数据
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
