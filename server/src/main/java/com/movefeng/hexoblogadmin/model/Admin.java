package com.movefeng.hexoblogadmin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author z
 */
@Data
public class Admin {

    private String username;
    private String nickname;
    private String email;
    private String avatar;
    /**
     * password字段的值不包含在序列化中。
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String secret;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
