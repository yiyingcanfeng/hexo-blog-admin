package com.movefeng.hexoblogadmin.dao;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.model.Comment;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.CommentVOs;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Mapper
public interface CommentDao {

    /**
     * 插入评论
     *
     * @param comment
     */
    void insertComment(Comment comment);

    /**
     * 根据文章标题查询所有父级评论
     *
     * @param articleTitle
     * @return
     */
    List<CommentVOs> selectCommentByArticleTitle(String articleTitle);

    /**
     * 查询某个评论下的所有子评论
     *
     * @param articleTitle
     * @param parentId
     * @return
     */
    List<CommentVO> selectChildComment(String articleTitle, Integer parentId);

    /**
     * 查询个父评论下的子评论数
     *
     * @param articleTitle
     * @param parentId
     * @return
     */
    int selectChildCommentCount(String articleTitle, Integer parentId);

    /**
     * 查询某篇文章的评论数
     *
     * @param articleTitle
     * @return
     */
    int selectCommentCount(String articleTitle);

    /**
     * 查询所有文章的评论,带模糊查询
     *
     * @return param
     */
    Page<CommentVO> selectAllComments(@Param("param") Map<String, Object> param);

    /**
     * 删除评论
     *
     * @param id
     */
    @Delete("delete from comment where id = #{id}")
    Integer deleteById(String id);

    /**
     * 审核评论
     *
     * @param id
     * @param audit
     * @return
     */
    @Update("update comment set audit_status = #{audit} where id = #{id}")
    Integer audit(String id, int audit);

    /**
     * 审核多条评论
     *
     * @param list
     */
    void auditBatch(List<Map<String, Object>> list);

    /**
     * 根据评论id批量删除评论
     *
     * @param idList
     */
    void deleteBatchById(List idList);

    /**
     * 根据评论id批量删除评论
     *
     * @param userIdList
     */
    void deleteBatchByUserId(List userIdList);

    /**
     * 根据用户id删除该用户的评论
     *
     * @param userId
     */
    @Delete("delete from comment where user_id = #{userId}")
    void deleteByUserId(Integer userId);

}
