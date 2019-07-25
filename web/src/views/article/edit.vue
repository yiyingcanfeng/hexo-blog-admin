<template>
  <div class="app-container">
    <router-link :to="{ path: '/article/list' }">
      <el-button>返回</el-button>
    </router-link>
    <el-button type="primary" class="save-button" @click="saveArticle">保存</el-button>
    <el-button type="primary" :loading="generateLoading" @click="generate">生成</el-button>
    <el-button type="primary" @click="modifyTitle">修改标题</el-button>
    <el-button type="danger" @click="deleteArticle">删除</el-button>
    <br>
    <el-form label-position="right" label-width="60px" style="margin-top: 10px;">
      <el-form-item label="标题:">
        <span :style="`display: ${titleSpanDisplay};font-size: 18px;`">{{ title }}</span>
        <el-input
          v-model="title"
          placeholder="标题"
          :style="`display: ${titleInputDisplay};`"
        />
      </el-form-item>
    </el-form>
    <Editormd ref="articleContent" :content="content" />
    <el-dialog
      title="确定删除？"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
    >
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { param2Obj } from '@/utils/index'
import Editormd from '@/layout/components/Editormd'
import request from '@/utils/request'

export default {
  name: 'Edit',
  components: {
    Editormd
  },
  data() {
    return {
      oldTitle: '',
      title: '',
      content: '',
      titleSpanDisplay: 'inline',
      titleInputDisplay: 'none',
      generateLoading: false
    }
  },
  mounted() {
    const param = param2Obj(window.location.href)
    if (param.title) {
      this.oldTitle = param.title
      this.title = param.title
      this.fetchData(param.title)
    }
  },
  methods: {
    fetchData(title) {
      request({
        url: '/article/content',
        method: 'post',
        params: {
          title: title
        }
      }).then(response => {
        this.content = response.data
      })
    },
    saveArticle() {
      request({
        url: '/article/save',
        method: 'post',
        data: {
          oldTitle: this.oldTitle,
          title: this.title,
          content: this.$refs.articleContent.$refs.content.innerText
        }
      }).then(response => {
        this.$message({
          message: response.message,
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
      })
    },
    generate() {
      this.generateLoading = true
      this.$message({
        message: '正在生成中...',
        type: 'info',
        duration: 3 * 1000
      })
      request({
        url: '/article/generate',
        method: 'get',
        timeout: 50000
      }).then(response => {
        this.generateLoading = false
        this.$message({
          message: response.message,
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
      }).catch(reason => {
        this.generateLoading = false
      })
    },
    modifyTitle() {
      this.titleInputDisplay = 'block'
      this.titleSpanDisplay = 'none'
    },
    deleteArticle() {
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        request({
          url: '/article/delete',
          method: 'post',
          data: {
            title: this.title
          }
        }).then(response => {
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.$router.push('/article/list')
        })
      })
    }
  }
}
</script>

<style scoped>
  .title-input {
    margin-top: 10px;
  }

  .save-button {
    margin-left: 10px;
  }
</style>
