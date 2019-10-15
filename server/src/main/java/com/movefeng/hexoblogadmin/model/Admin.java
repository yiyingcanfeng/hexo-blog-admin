package com.movefeng.hexoblogadmin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author z
 */
@Data
@Accessors(chain = true)
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
