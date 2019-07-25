# Hexo Blog Admin

> 一个基于[vue-admin-template](https://github.com/PanJiaChen/electron-vue-admin)的hexo博客后台管理。包含了文章管理，文章发布，评论管理，用户管理等功能。

[博客demo](http://118.25.83.223)

[后台管理demo](http://118.25.83.223/admin)

## 截图

![screenshoot/1.png](https://raw.githubusercontent.com/yiyingcanfeng/hexo-blog-admin/master/screenshoot/1.png)
![screenshoot/2.png](https://raw.githubusercontent.com/yiyingcanfeng/hexo-blog-admin/master/screenshoot/2.png)
![screenshoot/3.png](https://raw.githubusercontent.com/yiyingcanfeng/hexo-blog-admin/master/screenshoot/3.png)
![screenshoot/4.png](https://raw.githubusercontent.com/yiyingcanfeng/hexo-blog-admin/master/screenshoot/4.png)
![screenshoot/5.png](https://raw.githubusercontent.com/yiyingcanfeng/hexo-blog-admin/master/screenshoot/5.png)

## 技术栈

### 前端

1. Vue

2. Vue-cli 3

3. vue-router

4. ElementUI

5. axios

   ......

### 后端

1. JDK 11
2. SpringBoot
3. MyBatis
4. MySQL 8

## 构建

### 克隆项目

```bash
git clone https://github.com/yiyingcanfeng/hexo-blog-admin.git
```

### 前端构建

#### 构建

```bash
# 进入项目目录
cd web
# 安装依赖
npm install 
# 启动服务
npm run dev
```

浏览器访问 http://localhost:9527

#### 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

#### 其它

```bash
# 预览发布环境效果
npm run preview

# 预览发布环境效果 + 静态资源分析
npm run preview -- --report

# 代码格式检查
npm run lint

# 代码格式检查并自动修复
npm run lint -- --fix
```



### 后台构建

#### 注意：需要使用JDK 11 进行构建和运行

- 由于文章生成要用到 hexo-cli ，还需要全局安装 hexo-cli 
```bash
npm install -g hexo-cli
# 进入hexo文件夹，安装hexo所需的依赖
cd <hexo-blog>
npm install
```
- 导入数据库

  使用 server/hexo-blog.sql 文件创建数据库，

  然后在 sys_config 表中插入一条数据，如下：

  | id   | article_path                 | hexo_path      | public_path           |
  | ---- | ---------------------------- | -------------- | --------------------- |
  | 1    | /opt/hexo-blog/source/_posts | /opt/hexo-blog | /usr/share/nginx/html |

  字段说明：

  id：需要设置成1  

  article_path：  文章的markdown文件目录，一般在hexo路径下的 source/_posts 文件夹

  hexo_path：hexo目录  

  public_path：发布路径，即http服务器的访问路径  

- 后台源码打包
```bash
cd server
# 使用maven编译
mvn compile
# 打包
mvn package
# 或者，打包，并跳过测试
mvn package -DskipTests
# 运行,默认端口是8866，可以将 application.yml 复制到启动命令所在的目录，然后根据自己的需要修改配置
java -jar hexoblogadmin.war
# 指定外部的log4j2.xml
java -jar hexoblogadmin.war -Dlogging.config=/xxx/xxx/log4j2.xml
# 或者使用放到tomcat中
```

假如需要在CentOS7服务器上构建，服务器同时安装了jdk1.8和jdk11，这时CentOS7默认使用的是jdk1.8，所以构建之前需要导入环境变量

```bash
export PATH=/usr/lib/jvm/java-11-openjdk/bin:$PATH
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
```



## 部署

前端使用 nginx 或 apache ，

需要将 /blog/api 请求反向代理至后端服务器

以nginx为例：

```bash
location /blog/api/ {
	proxy_pass http://127.0.0.1:8866/;
}
```

后端可以使用 java -jar hexoblogadmin.war 的方式直接运行，

或者部署到tomcat容器中

可以使用supervisor 或 systemd 托管springboot应用，避免退出shell后进程终止

### TODO

- 头像上传
- 系统参数设置
- 增加统计功能，比如文章数、评论数、用户数等等，并增加图表展示
- 评论邮件通知

## 相关项目
[hexo-theme-yilia](https://github.com/yiyingcanfeng/hexo-theme-yilia)

[vue-element-admin](https://github.com/PanJiaChen/vue-element-admin)

[vue-admin-template](https://github.com/PanJiaChen/electron-vue-admin)


## 欢迎访问[我的博客](https://www.movefeng.com)

## License

[MIT](https://github.com/yiyingcanfeng/hexo-blog-admin/blob/master/LICENSE) license.

Copyright (c) 2019-present yiyingcanfeng
