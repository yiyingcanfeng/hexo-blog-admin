<template>
  <div class="app-container">
    <div class="container-head-button">
      <router-link :to="{ path: '/article/add' }">
        <el-button size="medium" type="primary">{{ $t('articleManage.articleList.addArticle') }}</el-button>
      </router-link>
      <el-button size="medium" type="primary" :loading="generateLoading" style="margin-left: 5px;" @click="generate">{{ $t('articleManage.articleList.generate') }}</el-button>
      <el-input
        v-model="searchForm.searchTitle"
        :placeholder="$t('articleManage.articleList.searchByTitle')"
        clearable
        size="medium"
        type="text"
        style="width: 20%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchTitle,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchCommentCount"
        :placeholder="$t('articleManage.articleList.searchByCommentCount')"
        clearable
        size="medium"
        type="text"
        style="width: 20%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchCommentCount,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-date-picker
        v-model="searchForm.searchDatetime"
        size="medium"
        type="datetimerange"
        value-format="yyyy-MM-dd HH:mm:ss"
        :picker-options="pickerOptions"
        :range-separator="$t('articleManage.articleList.searchByDateTo')"
        :start-placeholder="$t('articleManage.articleList.searchByDateStart')"
        :end-placeholder="$t('articleManage.articleList.searchByDateEnd')"
        align="right"
        clearable
        style="margin-left: 5px;"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-search"
        @click="searchComment"
      />
    </div>
    <el-table
      v-loading="listLoading"
      :data="data.list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      :default-sort="{prop: 'createTime', order: 'descending'}"
      style="width: 75%"
    >
      <el-table-column align="center" :label="$t('articleManage.articleList.table.number.columnName')" :width="$t('articleManage.articleList.table.number.width')">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" sortable prop="title" :label="$t('articleManage.articleList.table.title.columnName')">
        <template slot-scope="scope">
          <router-link tag="a" :to="{ path: '/article/edit', query: { title: scope.row.title }}">
            <a>
              {{ scope.row.title }}
            </a>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="createTime" :label="$t('articleManage.articleList.table.createTime.columnName')" :width="$t('articleManage.articleList.table.createTime.width')">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="commentCount" :label="$t('articleManage.articleList.table.commentCount.columnName')" :width="$t('articleManage.articleList.table.commentCount.width')">
        <template slot-scope="scope">
          <router-link v-if="scope.row.commentCount !== 0" tag="a" :to="{ path: '/comment/list', query: { searchTitle: scope.row.title,immediateSearch:true }}">
            <a>
              {{ scope.row.commentCount }}
            </a>
          </router-link>
          <span v-else>
            {{ scope.row.commentCount }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="left" :width="$t('articleManage.articleList.table.operate.width')" :label="$t('articleManage.articleList.table.operate.columnName')">
        <template slot-scope="scope">
          <a target="_blank" :href="systemConfig.hexoVisitUrl + scope.row.path">
            <el-button type="primary" size="medium">{{ $t('articleManage.articleList.table.articlePage') }}</el-button>
          </a>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      :current-page="currentPage"
      :page-sizes="[10, 15, 20]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import request from '@/utils/request'
export default {
  data() {
    return {
      data: {
        list: []
      },
      systemConfig: {
        articlePath: '',
        hexoVisitUrl: '',
        hexoAdminUrl: '',
        hexoPath: '',
        publicPath: '',
        adminMailReport: 0,
        userMailReport: 0,
        smtpSender: ''
      },
      listLoading: true,
      generateLoading: false,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        searchTitle: '',
        searchDatetime: '',
        searchCommentCount: ''
      },
      pickerOptions: {
        shortcuts: [{
          text: this.$t('pickerOptions.recentWeek'),
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('pickerOptions.recentMonth'),
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: this.$t('pickerOptions.recentThreeMonth'),
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  created() {
    this.fetchData(1, 10)
  },
  methods: {
    fetchData(pageNum, pageSize, searchParams) {
      this.listLoading = true
      request({
        url: '/article/list',
        method: 'post',
        params: {
          pageNum: pageNum,
          pageSize: pageSize
        },
        data: searchParams
      }).then(response => {
        request({
          url: '/system/listSettings',
          method: 'get'
        }).then(res => {
          this.systemConfig = res.data
          this.data = response.data
          this.listLoading = false
        })
      })
    },
    generate() {
      this.generateLoading = true
      this.$message({
        message: '正在生成中...',
        type: 'info',
        duration: 3 * 1000,
        showClose: true
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
    jumpToArticlePage(val) {
      console.log(val)
      window.open(this.systemConfig.hexoVisitUrl + val.path, '_blank')
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData(this.currentPage, val)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchData(val, this.pageSize)
    },
    searchComment() {
      this.fetchData(this.currentPage, this.pageSize, this.searchForm)
    },
    enterSearch(message, event) {
      // 捕获enter事件
      if (event.keyCode === 13) {
        this.fetchData(this.currentPage, this.pageSize, this.searchForm)
      }
    }
  }
}
</script>

<style scoped>
  a:hover{
    color: #409EFF;
  }
</style>
