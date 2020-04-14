package com.xiaoadong.community.model;

import lombok.Data;

/**
 * 数据库的实体类
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
