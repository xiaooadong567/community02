package com.xiaoadong.community.dto;

import lombok.Data;

/**
 * github author对象 拿到的数据
 */
@Data
public class GitHubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
