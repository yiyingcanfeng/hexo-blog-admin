export default {
  index: 'Index',
  route: {
    dashboard: 'Dashboard',
    articleManage: 'ArticleManage',
    articleList: 'ArticleList',
    editArticle: 'EditArticle',
    addArticle: 'AddArticle',
    commentManage: 'CommentManage',
    commentList: 'CommentList',
    userManage: 'UserManage',
    userList: 'UserList',
    systemSettings: 'SystemSettings',
    blogIndex: 'BlogIndex',
    errorPages: 'Error Pages',
    page401: '401',
    page404: '404',
    i18n: 'I18n',
    externalLink: 'External Link',
    profile: 'Profile'
  },
  pickerOptions: {
    recentWeek: 'Recent Week',
    recentMonth: 'Recent Month',
    recentThreeMonth: 'Recent Three Month'
  },
  deleteConfirm: {
    title: 'Confirm Delete？',
    message: 'Warning',
    cancel: 'Cancel',
    confirm: 'Confirm'
  },
  operateConfirm: {
    title: 'Confirm Operate?',
    message: 'Warning',
    cancel: 'Cancel',
    confirm: 'Confirm'
  },
  articleManage: {
    articleList: {
      addArticle: 'Add',
      generate: 'Generate',
      searchByTitle: 'Please enter the title',
      searchByCommentCount: 'Please enter the comment count',
      searchByDateStart: 'Date Start',
      searchByDateTo: 'to',
      searchByDateEnd: 'Date End',
      searchByDateRecentWeek: 'Recent Week',
      searchByDateRecentMonth: 'Recent Month',
      searchByDateRecentThreeMonth: 'Recent Three Month',
      table: {
        number: {
          columnName: 'No.',
          width: '60'
        },
        title: {
          columnName: 'Title'
        },
        createTime: {
          columnName: 'Create Time',
          width: '200'
        },
        commentCount: {
          columnName: 'Comment Count',
          width: '170'
        },
        operate: {
          columnName: 'Operate',
          width: '200'
        },
        articlePage: 'Article Page'
      }
    },
    editArticle: {
      title: 'title',
      goBack: 'GoBack',
      save: 'Save',
      generate: 'Generate',
      modifyTitle: 'ModifyTitle',
      deleteArticle: 'Delete',
      generating: 'generating...',
      deleteDialog: {
        title: 'Confirm delete？',
        message: 'Warning',
        cancel: 'Cancel',
        confirm: 'Confirm'
      }
    },
    addArticle: {
      title: 'title',
      goBack: 'GoBack',
      save: 'Save'
    }
  },
  commentManage: {
    commentList: {
      batchOperate: 'OperateBatch',
      deleteBatch: 'DeleteBatch',
      passAuditBatch: 'PassAuditBatch',
      cancelAuditBatch: 'CancelAuditBatch',
      searchUsername: 'Please enter the username',
      searchTitle: 'Please enter the article title',
      searchComment: 'Please enter the comment content',
      searchDatetimeStart: 'Date Start',
      searchDatetimeTo: 'to',
      searchDatetimeEnd: 'Date End',
      table: {
        number: {
          columnName: 'No.',
          width: '65'
        },
        username: {
          columnName: 'Username',
          width: '120'
        },
        replyUsername: {
          columnName: 'ReplyUsername',
          width: '135'
        },
        createTime: {
          columnName: 'CommentTime',
          width: '170'
        },
        articleTitle: {
          columnName: 'Article'
        },
        content: {
          columnName: 'CommentContent'
        },
        auditStatus: {
          columnName: 'AuditStatus',
          width: '120'
        },
        auditSuccess: 'AuditSuccess',
        waitAudit: 'WaitAudit',
        operate: {
          columnName: 'Operate',
          width: '300',
          passAudit: 'PassAudit',
          cancelAudit: 'CancelAudit',
          deleteComment: 'Delete',
          reply: 'Reply'
        }
      }
    },
    replyCommentDialog: {
      title: 'Reply Comment',
      userMail: 'Email',
      userMailPlaceholder: 'Default smtp Email',
      username: 'Username',
      usernamePlaceholder: 'Please enter the username',
      content: 'ReplyContent',
      contentPlaceholder: 'Please enter the reply content',
      replyComment: ''
    },
    message: {
      operateSuccess: 'Operate Success!',
      chooseData: 'Please choose date to operate!',
      chooseDataDelete: 'Please choose date to delete!',
      noCommentNeedAudit: 'No comment need to audit!',
      noCommentNeedCancelAudit: 'No comment need to cancel audit!'
    }
  },
  userManage: {
    userList: {
      batchOperate: 'OperateBatch',
      deleteBatch: 'DeleteBatch',
      searchUsername: 'Please enter the username',
      searchEmail: 'Please enter the email',
      searchCommentCount: 'Please enter the comment count',
      table: {
        number: {
          columnName: 'No.',
          width: '65'
        },
        username: {
          columnName: 'Username'
        },
        email: {
          columnName: 'Email'
        },
        website: {
          columnName: 'Website'
        },
        commentCount: {
          columnName: 'CommentCount',
          width: '200'
        },
        operate: {
          columnName: 'Operate',
          width: '175',
          deleteUser: 'Delete'
        }
      }
    },
    message: {
      operateSuccess: 'Operate Success!',
      deleteConfirmMessage: 'The user\'s comment data will also be deleted, are you sure?',
      deleteConfirmMessageTitle: 'Warning',
      chooseDataDelete: 'Please choose date to delete!！',
      cancel: 'Cancel',
      confirm: 'Confirm'
    }
  },
  systemSettings: {
    formLabelWidth: '200px',
    switch: {
      active: 'Open',
      inactive: 'Close'
    },
    title: 'Blog Settings',
    hexoVisitUrl: 'The url to visit the blog',
    hexoVisitUrlPlaceholder: 'Please enter the url to visit the blog',
    hexoVisitUrlTooltip: 'Open with http/https,end with / for example: http://127.0.0.1/blog/',
    hexoAdminUrl: 'The url to visit the admin',
    hexoAdminUrlPlaceholder: 'Please enter the url to visit the admin',
    hexoAdminUrlTooltip: 'Open with http/https,end with / for example: http://127.0.0.1/admin/',
    hexoPath: 'Hexo Path',
    hexoPathPlaceholder: 'Please enter the Hexo Path',
    articlePath: 'Article Path',
    articlePathPlaceholder: 'Please enter the article path',
    articlePathTooltip: 'Generally as: <hexo path>/sources/_posts',
    publicPath: 'Hexo Public Path',
    publicPathPlaceholder: 'Please enter the hexo public path',
    publicPathTooltip: 'The access path to a compiled hexo static resource，such as: <br>\n' +
      '            nginx: /usr/share/nginx/html/<your path><br>\n' +
      '            Apache: /var/www/html/<your path>',
    adminMailReport: 'Admin Mail Report',
    adminMailReportTooltip: 'Notify administrators when users post comments',
    userMailReport: 'User Mail Report',
    userMailReportTooltip: 'Notify users when their comments are responded to',
    smtpSender: 'Default Sender Email',
    smtpSenderPlaceholder: 'Please enter the default sender email',
    commit: 'commit',
    operateSuccess: 'Operate success!'
  },
  navbar: {
    dashboard: 'Dashboard',
    github: 'Github',
    logOut: 'Log Out',
    profile: 'Profile',
    theme: 'Theme',
    size: 'Global Size'
  },
  login: {
    title: 'Login Form',
    logIn: 'Login',
    username: 'Username',
    password: 'Password',
    any: 'any',
    thirdparty: 'Or connect with',
    thirdpartyTips: 'Can not be simulated on local, so please combine you own business simulation! ! !'
  },
  tagsView: {
    refresh: 'Refresh',
    close: 'Close',
    closeOthers: 'Close Others',
    closeAll: 'Close All'
  },
  settings: {
    title: 'Page style setting',
    theme: 'Theme Color',
    tagsView: 'Open Tags-View',
    fixedHeader: 'Fixed Header',
    sidebarLogo: 'Sidebar Logo'
  }
}
