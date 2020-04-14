package com.xiaoadong.community.dto;

import lombok.Data;

/**
 * github 需要的数据
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;


}
