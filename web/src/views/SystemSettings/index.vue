<template>
  <div class="settings-container">
    <h2>博客相关设置</h2>
    <el-form
      label-position="right"
      label-width="120px"
    >
      <el-form-item label="博客的访问url">
        <el-tooltip class="item" effect="dark" content="以 http/https 开头,结尾需要有 / 例: http://127.0.0.1/blog/" placement="right">
          <el-input
            v-model="systemConfigForm.hexoVisitUrl"
            placeholder="请输入博客的访问url"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="后台管理的访问url">
        <el-tooltip class="item" effect="dark" content="以 http/https 开头,结尾需要有 / 例: http://127.0.0.1/admin/" placement="right">
          <el-input
            v-model="systemConfigForm.hexoAdminUrl"
            placeholder="请输入后台管理的访问url"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="hexo目录">
        <el-input
          v-model="systemConfigForm.hexoPath"
          placeholder="请输入hexo目录"
          clearable
          size="medium"
          type="text"
          style="width: 20%"
        />
      </el-form-item>
      <el-form-item label="文章目录">
        <el-tooltip class="item" effect="dark" content="一般为: <hexo目录>/sources/_posts" placement="right">
          <el-input
            v-model="systemConfigForm.articlePath"
            placeholder="请输入文章目录"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="hexo发布目录">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content">
            hexo编译后静态资源的访问路径，比如 <br>
            nginx: /usr/share/nginx/html/你的目录<br>
            Apache: /var/www/html/你的目录
          </div>
          <el-input
            v-model="systemConfigForm.publicPath"
            placeholder="请输入hexo发布目录"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="管理员邮件通知">
        <el-tooltip class="item" effect="dark" content="当用户发表评论时通知管理员" placement="right">
          <el-switch
            v-model="systemConfigForm.adminMailReport"
            :active-value="1"
            :inactive-value="0"
            active-text="开启"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="用户邮件通知">
        <el-tooltip class="item" effect="dark" content="当用户评论被回复时通知用户" placement="right">
          <el-switch
            v-model="systemConfigForm.userMailReport"
            :active-value="1"
            :inactive-value="0"
            active-text="开启"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="默认发信邮箱">
        <el-input
          v-model="systemConfigForm.smtpSender"
          placeholder="请输入默认发信邮箱"
          clearable
          size="medium"
          type="text"
          style="width: 20%"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          size="small"
          :loading="systemConfigSubmitLoading"
          @click="systemConfigSubmit"
        >提交
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import request from '@/utils/request'
export default {
  name: 'SystemSettings',
  data() {
    return {
      systemConfigDialogVisible: false,
      systemConfigSubmitLoading: false,
      systemConfigForm: {
        articlePath: '',
        hexoVisitUrl: '',
        hexoAdminUrl: '',
        hexoPath: '',
        publicPath: '',
        adminMailReport: 0,
        userMailReport: 0,
        smtpSender: ''
      }
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      request({
        url: '/system/listSettings',
        method: 'get'
      }).then(response => {
        this.systemConfigForm = response.data
      })
    },
    systemConfigSubmit() {
      this.systemConfigSubmitLoading = true
      request({
        url: '/system/updateSettings',
        method: 'post',
        data: this.systemConfigForm
      }).then(response => {
        this.$message({
          message: '操作成功',
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
        this.systemConfigSubmitLoading = false
        this.fetchData()
      }).catch(reason => {
        this.systemConfigSubmitLoading = false
      })
    }
  }
}
</script>

<style scoped lang="scss">
.settings-container {
  margin-top: 20px;
  margin-left: 20px;
}
</style>
