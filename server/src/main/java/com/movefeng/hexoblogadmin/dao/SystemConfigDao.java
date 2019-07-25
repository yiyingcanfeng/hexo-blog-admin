package com.movefeng.hexoblogadmin.dao;

import com.movefeng.hexoblogadmin.model.SystemConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 全局的配置信息，只处理数据库表中id为1的记录
 *
 * @author z
 */
@Mapper
public interface SystemConfigDao {

    /**
     * 查询配置信息
     *
     * @return
     */
    @Select("select * from sys_config where id = 1;")
    SystemConfig select();

    /**
     * 更新配置信息
     *
     * @param systemConfig
     * @return
     */
    @Update("update sys_config set article_path = #{articlePath} where id = 1;")
    Integer update(SystemConfig systemConfig);

}
