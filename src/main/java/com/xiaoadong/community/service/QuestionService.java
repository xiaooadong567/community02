package com.xiaoadong.community.service;

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

    public List<QuestionsDTO> list() {
        List<Question> questionList = quesstionMapper.list();
        List<QuestionsDTO> questionsDTOList = new ArrayList<>();
        for (Question question : questionList) {
            User userMapperById = userMapper.findById(question.getCreator());
            QuestionsDTO questionsDTO = new QuestionsDTO();
            BeanUtils.copyProperties(question,questionsDTO);
            questionsDTO.setUser(userMapperById);
            questionsDTOList.add(questionsDTO);

        }

        return questionsDTOList;
    }
}
