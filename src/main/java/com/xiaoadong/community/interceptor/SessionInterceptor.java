package com.xiaoadong.community.interceptor;

import com.xiaoadong.community.mapper.UserMapper;
import com.xiaoadong.community.model.User;
import com.xiaoadong.community.model.UserExample;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取user信息 验证cookies信息
        Cookie[] cookies = request.getCookies();
        if (cookies != null) { // 判断不为空 避免空指针
            for (Cookie cookie : cookies) { // 循环cookies数组
                if (cookie.getName().equals("token")) { // 判断cookie信息
                    String token = cookie.getValue(); // 获取cookies
                    UserExample userExample = new UserExample();
                    userExample.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        //添加到session中
                        request.getSession().setAttribute("user", users.get(0));
                    }

                    break;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
