package com.movefeng.hexoblogadmin.dao;

import com.movefeng.hexoblogadmin.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author z
 */
@Mapper
public interface AdminDao {

    /**
     * 根据名称查询管理员信息
     *
     * @param admin
     * @return
     */
    @Select("select * from admin where username = #{username} and password=#{password}")
    Admin selectAdminByUsernameAndPassword(Admin admin);

    /**
     * 根据名称查询管理员信息
     *
     * @param admin
     * @return
     */
    @Select("select * from admin where username = #{username}")
    Admin selectAdminByUsername(Admin admin);

    /**
     * 查询第一个管理员的信息
     *
     * @return
     */
    @Select("select * from admin limit 1;")
    Admin selectFirstOne();

    /**
     * 根据邮箱获取管理员
     *
     * @param smtpSender
     * @return
     */
    @Select("select * from admin where email = #{smtpSender} ;")
    Admin selectAdminByEmail(String smtpSender);
}
