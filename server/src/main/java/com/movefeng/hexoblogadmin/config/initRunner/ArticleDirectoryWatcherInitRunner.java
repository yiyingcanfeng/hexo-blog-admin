package com.movefeng.hexoblogadmin.config.initRunner;

import com.movefeng.hexoblogadmin.model.Article;
import com.movefeng.hexoblogadmin.model.SystemConfig;
import com.movefeng.hexoblogadmin.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实时监控文章目录中文件的变化并写入数据库
 *
 * @author z
 */
@Order(2)
@Component
@Slf4j
public class ArticleDirectoryWatcherInitRunner implements ApplicationRunner {

    @Resource
    private ArticleService articleService;

    @Resource
    private SystemConfig systemConfig;

    @Resource
    private TaskExecutor taskExecutor;

    @Override
    public void run(ApplicationArguments args) {
        // 需放在子线程中执行，避免出现异常时整个应用终止
        taskExecutor.execute(this::directoryWatcher);
    }

    private void directoryWatcher() {
        try {
            log.info("DirectoryWatcher 已启动");
            String articlePath = systemConfig.getArticlePath();
            File file = new File(articlePath);
            writeArticleFileInfoToDataBase(file);

            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(articlePath);
            path.register(
                    watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    log.info(String.format("文章目录变化: 事件:%s,受影响的文件:%s", event.kind(), event.context()));
                }
                writeArticleFileInfoToDataBase(file);
                key.reset();
            }
        } catch (IOException | ParseException | InterruptedException e) {
            log.info("DirectoryWatcher 异常终止");
            // 如果出现不可预料的异常时，再新建一个子线程执行任务
            taskExecutor.execute(this::directoryWatcher);
            e.printStackTrace();
        }
    }

    /**
     * 读取文章目录中文件的信息并写入数据库
     *
     * @param file
     * @throws IOException
     * @throws ParseException
     */
    private void writeArticleFileInfoToDataBase(File file) throws IOException, ParseException {
        Collection<File> mdFiles = FileUtils.listFiles(file, FileFilterUtils.suffixFileFilter("md"), null);
        log.info("mdFileList: \n {}", StringUtils.join(mdFiles.stream().map(File::getName).collect(Collectors.toList()), '\n'));
        List<Article> articleList = new ArrayList<>();
        for (File mdFile : mdFiles) {
            String s = FileUtils.readFileToString(mdFile, "utf-8");
            Date createTime = null;
            if (!"".equals(s) && s.contains("date:")) {
                String s1 = s.split("date:")[1];
                String dateString;
                if (s1.contains("\n")) {
                    dateString = s1.substring(0, s1.indexOf("\n")).trim();
                    // 如果文件只有一行的情况
                } else {
                    dateString = s1;
                }
                createTime = FastDateFormat.getInstance("yyyy-MM-dd").parse(dateString);
            }
            String fileName = mdFile.getName();
            Article article = new Article();
            article.setTitle(fileName.substring(0, fileName.lastIndexOf(".md")));
            article.setCreateTime(createTime);
            articleList.add(article);
        }
        articleService.updateArticleBatch(articleList);
    }
}
