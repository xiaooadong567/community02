package com.xiaoadong.community.controller;

import com.xiaoadong.community.mapper.QuesstionMapper;
import com.xiaoadong.community.model.Question;
import com.xiaoadong.community.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Resource
    private QuesstionMapper quesstionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title", required=false) String title,
            @RequestParam(value = "description", required=false) String description,
            @RequestParam(value = "tag", required=false) String tag,
            HttpServletRequest request,
            Model model
    ) {

        //出问题 回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //不能为空
        if (title == null || title == "") {
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error","问题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        //用户没有登录
        if (user ==null) {
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        //添加操作
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        quesstionMapper.create(question);
        return "redirect:index";
    }
}
