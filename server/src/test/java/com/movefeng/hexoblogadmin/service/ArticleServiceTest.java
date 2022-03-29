package com.movefeng.hexoblogadmin.service;

import com.github.pagehelper.Page;
import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.model.SystemConfig;
import com.movefeng.hexoblogadmin.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;


/**
 * @author z
 */
@SpringBootTest
@Slf4j
public class ArticleServiceTest {

    @Resource
    private ArticleService articleService;
    @Resource
    private SystemConfig systemConfig;

    @Test
    @Transactional
    @Rollback
    public void updateArticleBatch() throws IOException, ParseException, InterruptedException {
        String articlePath = systemConfig.getArticlePath();
        log.info(articlePath);
        File file = new File(articlePath);
        Collection<File> mdFiles = FileUtils.listFiles(file, FileFilterUtils.suffixFileFilter("md"), null);
        List<Article> articleList = new ArrayList<>();
        for (File mdFile : mdFiles) {
            String s = FileUtils.readFileToString(mdFile, "utf-8");
            String s1 = s.split("date:")[1];
            String dateString = s1.substring(0, s1.indexOf("\n")).trim();
            Date createTime = FastDateFormat.getInstance("yyyy-MM-dd").parse(dateString);
            String fileName = mdFile.getName();
            Article article = new Article();
            article.setTitle(fileName);
            article.setCreateTime(createTime);
            articleList.add(article);
            System.out.println(mdFile.getAbsolutePath());
        }
        articleService.updateArticleBatch(articleList);
        log.error("修改成功！");
    }

    @Test
    public void queryArticle() {
        HashMap<String, Object> hashMap = new HashMap<>();
        Page<ArticleVO> articleVOS = articleService.queryArticle(hashMap);
        log.info(articleVOS+"");
    }
}