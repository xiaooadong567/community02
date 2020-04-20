package com.xiaoadong.community.service;

import com.xiaoadong.community.dto.PaginationDTO;
import com.xiaoadong.community.dto.QuestionsDTO;
import com.xiaoadong.community.mapper.QuestionMapper;
import com.xiaoadong.community.mapper.UserMapper;
import com.xiaoadong.community.model.Question;
import com.xiaoadong.community.model.QuestionExample;
import com.xiaoadong.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        //

        Integer totalCount = (int) questionMapper.countByExample(new QuestionExample());


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
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionsDTO> questionsDTOList = new ArrayList<>();


        for (Question question : questions) {
            User userMapperById = userMapper.selectByPrimaryKey(question.getCreator());
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(UserId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);

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
        QuestionExample example =  new QuestionExample();
        example.createCriteria().andCreatorEqualTo(UserId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        List<QuestionsDTO> questionsDTOList = new ArrayList<>();


        for (Question question : questions) {
            User userMapperById = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionsDTO questionsDTO = new QuestionsDTO();
            BeanUtils.copyProperties(question, questionsDTO);
            questionsDTO.setUser(userMapperById);
            questionsDTOList.add(questionsDTO);
        }
        paginationDTO.setQuestions(questionsDTOList);
        return paginationDTO;
    }

    public QuestionsDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        QuestionsDTO questionsDTO = new QuestionsDTO();
        BeanUtils.copyProperties(question,questionsDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionsDTO.setUser(user);
        return questionsDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else{
            // 更新
            question.setGmtModified(question.getGmtCreate());
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andCreatorEqualTo(question.getId());

            questionMapper.updateByExampleSelective(updateQuestion,example);
        }
    }
}
