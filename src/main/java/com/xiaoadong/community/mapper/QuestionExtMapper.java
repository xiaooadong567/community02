package com.xiaoadong.community.mapper;

import com.xiaoadong.community.model.Question;
import com.xiaoadong.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    // 阅读数
    int incView(Question record);
    // 评论数
    int incCommentCount(Question record);
}