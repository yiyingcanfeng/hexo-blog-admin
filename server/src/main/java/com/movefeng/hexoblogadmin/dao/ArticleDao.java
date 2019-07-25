package com.movefeng.hexoblogadmin.dao;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.model.Comment;
import com.movefeng.hexoblogadmin.vo.ArticleVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@Mapper
public interface ArticleDao {

    /**
     * 插入文章
     *
     * @param article
     */
    @Insert("insert into article (title) values (#{title});")
    void insertArticle(Article article);

    /**
     * 批量插入文章
     *
     * @param articleList
     */
    void insertArticleBatch(List<Article> articleList);

    /**
     * 批量删除文章
     *
     * @param articleList
     */
    void deleteBatch(List<Article> articleList);

    /**
     * 查询所有文章
     *
     * @return
     */
    @Select("select * from article")
    List<Article> selectArticleList();

    /**
     * 查询所有文章,包含评论数信息
     *
     * @return
     */
    Page<ArticleVO> selectArticleVOList(@Param("param") Map<String, Object> param);

    /**
     * 根据标题查询文章信息
     *
     * @param title
     * @return
     */
    @Select("select * from article where title = #{title};")
    Article selectArticleByTitle(String title);

}
