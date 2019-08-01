package com.movefeng.hexoblogadmin.vo;

import com.movefeng.hexoblogadmin.model.User;
import lombok.Data;

/**
 * @author z
 */
@Data
public class UserVO extends User {

    private Integer commentCount;

}
