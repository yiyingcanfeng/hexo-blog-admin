<template>
  <div class="app-container">
    <router-link :to="{ path: '/article/list' }">
      <el-button>返回</el-button>
    </router-link>
    <el-button type="primary" class="save-button" @click="addArticle">保存</el-button>
    <br>
    <el-form label-position="right" label-width="60px" style="margin-top: 10px;">
      <el-form-item label="标题:">
        <el-input
          v-model="title"
          placeholder="标题"
        />
      </el-form-item>
    </el-form>
    <Editormd ref="articleContent" :content="content" />
  </div>
</template>

<script>
import Editormd from '@/layout/components/Editormd'
import request from '@/utils/request'

export default {
  name: 'Add',
  components: {
    Editormd
  },
  data() {
    return {
      title: '',
      content: ''
    }
  },
  methods: {
    addArticle() {
      request({
        url: '/article/add',
        method: 'post',
        data: {
          title: this.title,
          content: this.$refs.articleContent.$refs.content.innerText
        }
      }).then(response => {
        this.$message({
          message: '保存成功！',
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
        this.$router.push('/article/list')
      })
    }
  }
}
</script>

<style scoped>

</style>
