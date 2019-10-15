package com.movefeng.hexoblogadmin.dao;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.model.User;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Mapper
public interface UserDao {

    /**
     * 插入用户
     *
     * @param user
     */
    void insertUser(User user);

    /**
     * 根据email查询用户信息
     *
     * @param email
     * @return
     */
    @Select("select * from user where email = #{email}")
    User selectUserByEmail(String email);

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username}")
    User selectUserByUsername(String username);

    /**
     * 根据email更新用户信息
     *
     * @param commentVO
     */
    @Update("update user set username = #{username} where email = #{userMail}")
    void updateUserByEmail(CommentVO commentVO);

    /**
     * 查询所有用户,带模糊查询
     *
     * @param param
     * @return
     */
    Page<User> selectUserAll(@Param("param") Map<String, Object> param);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Delete("delete from user where id = #{id}")
    Integer deleteById(Integer id);

    /**
     * 根据id批量删除用户
     *
     * @param idList
     */
    void deleteBatchById(List<Integer> idList);

    /**
     * 根据id查询user信息
     *
     * @param replyUserId
     * @return
     */
    @Select("select * from user where id=#{replyUserId} ;")
    User selectUserById(Integer replyUserId);
}
