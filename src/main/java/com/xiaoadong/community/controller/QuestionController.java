package com.xiaoadong.community.controller;

import com.xiaoadong.community.dto.QuestionsDTO;
import com.xiaoadong.community.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

@Controller
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable Integer id, Model model){
        QuestionsDTO questionsDTO = questionService.getById(id);
        model.addAttribute("question",questionsDTO);
        return "question";
    }
}
