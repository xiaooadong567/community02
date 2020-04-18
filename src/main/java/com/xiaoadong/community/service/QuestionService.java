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
        Integer totalPage;
        //所有的数量
        Integer totalCount = quesstionMapper.count();

        // 总页码
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 不让页面小于1
        if (page < 1) {
            page = 1;
        }
        // 不让页面无限大
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

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

    public PaginationDTO list(Integer UserId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //所有的数量
        Integer totalCount = quesstionMapper.countByUserId(UserId);

        // 总页码
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        // 不让页面小于1
        if (page < 1) {
            page = 1;
        }
        // 不让页面无限大
        if (page > totalPage) {
            page = totalPage;
        }

        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);
        List<Question> questions = quesstionMapper.listByUserId(UserId, offset, size);
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

    public QuestionsDTO getById(Integer id) {
        Question question = quesstionMapper.getById(id);
        QuestionsDTO questionsDTO = new QuestionsDTO();
        BeanUtils.copyProperties(question,questionsDTO);
        User user = userMapper.findById(question.getCreator());
        questionsDTO.setUser(user);
        return questionsDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            quesstionMapper.create(question);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
        } else{
            // 更新
            question.setGmtModified(question.getGmtCreate());
            quesstionMapper.update(question);
        }
    }
}
