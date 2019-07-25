# Hexo Blog Admin
> [Hexo Blog Admin](https://github.com/yiyingcanfeng/hexo-blog-admin) hexo博客后台管理项目的服务端，基于SpringBoot+MyBatis

## 如何构建

```bash
git clone https://github.com/yiyingcanfeng/hexo-blog-admin-server.git

cd hexo-blog-admin-server

mvn package -DskipTests

java -jar hexo-blog-admin-server.jar
```

### 开发环境

工具 | 版本号 | 下载
----|----|----
JDK | 11 | https://www.oracle.com/technetwork/java/javase/downloads/index.html 
Mysql | 8.0 | https://dev.mysql.com/downloads/mysql/ 
### TIPS

- 可以将 application.yml 复制到启动命令所在的目录，springboot启动时就会放弃读取jar中的 application.yml，而是读取这个配置文件里的信息。
- 建议将 application.yml 中部分敏感信息配置在 application-dev.yml 中，并将 application-dev.yml 添加到 .gitignore 文件中。

