package com.xiaoadong.community.service;

import com.xiaoadong.community.dto.PaginationDTO;
import com.xiaoadong.community.dto.QuestionsDTO;
import com.xiaoadong.community.mapper.QuesstionMapper;
import com.xiaoadong.community.mapper.UserMapper;
import com.xiaoadong.community.model.Question;
import com.xiaoadong.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private QuesstionMapper quesstionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        //所有的数量
        Integer totalCount = quesstionMapper.count();
        paginationDTO.setPagination(totalCount, page, size);
        // 不让页面小于1
        if (page < 1) {
            page = 1;
        }
        // 不让页面无限大
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }
        Integer offset = size * (page - 1);
        List<Question> questions = quesstionMapper.list(offset, size);
        List<QuestionsDTO> questionsDTOList = new ArrayList<>();


        for (Question question : questions) {
            User userMapperById = userMapper.findById(question.getCreator());
            QuestionsDTO questionsDTO = new QuestionsDTO();
            BeanUtils.copyProperties(question, questionsDTO);
            questionsDTO.setUser(userMapperById);
            questionsDTOList.add(questionsDTO);
        }
        paginationDTO.setQuestions(questionsDTOList);


        return paginationDTO;
    }
}
