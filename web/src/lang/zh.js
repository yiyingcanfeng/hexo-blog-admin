export default {
  index: '首页',
  route: {
    dashboard: '首页',
    articleManage: '文章管理',
    articleList: '文章列表',
    editArticle: '编辑文章',
    addArticle: '新增文章',
    commentManage: '评论管理',
    commentList: '评论列表',
    userManage: '用户管理',
    userList: '用户列表',
    systemSettings: '系统设置',
    blogIndex: '博客首页',
    errorPages: '错误页面',
    page401: '401',
    page404: '404',
    i18n: '国际化',
    externalLink: '外链',
    profile: '个人中心'
  },
  pickerOptions: {
    recentWeek: '最近一周',
    recentMonth: '最近一个月',
    recentThreeMonth: '最近三个月'
  },
  deleteConfirm: {
    title: '确认删除？',
    message: '提示',
    cancel: '取消',
    confirm: '确定'
  },
  operateConfirm: {
    title: '确认删除？',
    message: '提示',
    cancel: '取消',
    confirm: '确定'
  },
  articleManage: {
    articleList: {
      addArticle: '写文章',
      generate: '生成',
      searchByTitle: '请输入文章标题',
      searchByCommentCount: '请输入评论数',
      searchByDateStart: '开始日期',
      searchByDateTo: '至',
      searchByDateEnd: '结束日期',
      searchByDateRecentWeek: '最近一周',
      searchByDateRecentMonth: '最近一个月',
      searchByDateRecentThreeMonth: '最近三个月',
      table: {
        number: {
          columnName: '序号',
          width: '60'
        },
        title: {
          columnName: '标题'
        },
        createTime: {
          columnName: '创建时间',
          width: '200'
        },
        commentCount: {
          columnName: '评论数',
          width: '90'
        },
        operate: {
          columnName: '操作',
          width: '200'
        },
        articlePage: '文章页面'
      }
    },
    editArticle: {
      title: '标题',
      goBack: '返回',
      save: '保存',
      generate: '生成',
      modifyTitle: '修改标题',
      deleteArticle: '删除',
      generating: '正在生成中...'
    },
    addArticle: {
      title: '标题',
      goBack: '返回',
      save: '保存'
    }
  },
  commentManage: {
    commentList: {
      batchOperate: '批量操作',
      deleteBatch: '批量删除',
      passAuditBatch: '通过审核',
      cancelAuditBatch: '取消审核',
      searchUsername: '请输入用户名',
      searchTitle: '请输入文章标题',
      searchComment: '请输入评论内容',
      searchDatetimeStart: '开始日期',
      searchDatetimeTo: '至',
      searchDatetimeEnd: '结束日期',
      table: {
        number: {
          columnName: '序号',
          width: '65'
        },
        username: {
          columnName: '用户名',
          width: '120'
        },
        replyUsername: {
          columnName: '回复的人',
          width: '130'
        },
        createTime: {
          columnName: '评论时间',
          width: '170'
        },
        articleTitle: {
          columnName: '文章'
        },
        content: {
          columnName: '评论内容'
        },
        auditStatus: {
          columnName: '审核状态',
          width: '120'
        },
        auditSuccess: '审核通过',
        waitAudit: '未通过',
        operate: {
          columnName: '操作',
          width: '300',
          passAudit: '通过审核',
          cancelAudit: '取消审核',
          deleteComment: '删除',
          reply: '回复'
        }
      }
    },
    replyCommentDialog: {
      title: '回复评论',
      userMail: '邮箱地址',
      userMailPlaceholder: '默认为smtp发信邮箱',
      username: '用户名',
      usernamePlaceholder: '请填写用户名',
      content: '回复内容',
      contentPlaceholder: '请填写回复内容',
      reply: '回复'
    },
    message: {
      operateSuccess: '操作成功！',
      chooseData: '请选择要操作的数据！',
      chooseDataDelete: '请选择需要删除的数据！',
      noCommentNeedAudit: '没有需要审核通过的评论！',
      noCommentNeedCancelAudit: '没有需要取消审核的评论！'
    }
  },
  userManage: {
    userList: {
      batchOperate: '批量操作',
      deleteBatch: '批量删除',
      searchUsername: '请输入用户名',
      searchEmail: '请输入邮件',
      searchCommentCount: '请输入评论数',
      table: {
        number: {
          columnName: '序号',
          width: '65'
        },
        username: {
          columnName: '用户名'
        },
        email: {
          columnName: '邮箱'
        },
        website: {
          columnName: '网站'
        },
        commentCount: {
          columnName: '评论数',
          width: '90'
        },
        operate: {
          columnName: '操作',
          width: '175',
          deleteUser: '删除'
        }
      }
    },
    message: {
      operateSuccess: '操作成功！',
      deleteConfirmMessage: '该用户相关的评论数据也将一并删除，是否确认操作？',
      deleteConfirmMessageTitle: '提示',
      chooseDataDelete: '请选择需要删除的数据！',
      cancel: '取消',
      confirm: '确定'
    }
  },
  systemSettings: {
    formLabelWidth: '150px',
    switch: {
      active: '开启',
      inactive: '关闭'
    },
    title: '博客相关设置',
    hexoVisitUrl: '博客的访问url',
    hexoVisitUrlPlaceholder: '请输入博客的访问url',
    hexoVisitUrlTooltip: '以 http/https 开头,结尾需要有 / 例: http://127.0.0.1/blog/',
    hexoAdminUrl: '后台管理的访问url',
    hexoAdminUrlPlaceholder: '请输入后台管理的访问url',
    hexoAdminUrlTooltip: '以 http/https 开头,结尾需要有 / 例: http://127.0.0.1/admin/',
    hexoPath: 'hexo目录',
    hexoPathPlaceholder: '请输入hexo目录',
    articlePath: '文章目录',
    articlePathPlaceholder: '请输入文章目录',
    articlePathTooltip: '一般为: <hexo目录>/sources/_posts',
    publicPath: 'hexo发布目录',
    publicPathPlaceholder: '请输入hexo发布目录',
    publicPathTooltip: 'hexo编译后静态资源的访问路径，比如 <br>\n' +
      '            nginx: /usr/share/nginx/html/你的目录<br>\n' +
      '            Apache: /var/www/html/你的目录',
    adminMailReport: '管理员邮件通知',
    adminMailReportTooltip: '当用户发表评论时通知管理员',
    userMailReport: '用户邮件通知',
    userMailReportTooltip: '当用户评论被回复时通知用户',
    smtpSender: '默认发信邮箱',
    smtpSenderPlaceholder: '请输入默认发信邮箱',
    commit: '提交',
    operateSuccess: '操作成功!'
  },
  navbar: {
    dashboard: '首页',
    github: '项目地址',
    logOut: '退出登录',
    profile: '个人中心',
    theme: '换肤',
    size: '布局大小'
  },
  login: {
    title: '系统登录',
    logIn: '登录',
    username: '账号',
    password: '密码',
    any: '随便填',
    thirdparty: '第三方登录',
    thirdpartyTips: '本地不能模拟，请结合自己业务进行模拟！！！'
  },
  theme: {
    change: '换肤',
    documentation: '换肤文档',
    tips: 'Tips: 它区别于 navbar 上的 theme-pick, 是两种不同的换肤方法，各自有不同的应用场景，具体请参考文档。'
  },
  tagsView: {
    refresh: '刷新',
    close: '关闭',
    closeOthers: '关闭其它',
    closeAll: '关闭所有'
  },
  settings: {
    title: '系统布局配置',
    theme: '主题色',
    tagsView: '开启 Tags-View',
    fixedHeader: '固定 Header',
    sidebarLogo: '侧边栏 Logo'
  }
}
