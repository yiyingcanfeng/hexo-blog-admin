<template>
  <div class="app-container">
    <div class="container-head-button">
      <router-link :to="{ path: '/article/add' }">
        <el-button size="medium" type="primary">写文章</el-button>
      </router-link>
      <el-button size="medium" type="primary" :loading="generateLoading" @click="generate">生成</el-button>
      <el-input
        v-model="searchForm.searchTitle"
        placeholder="请输入文章标题"
        clearable
        size="medium"
        type="text"
        style="width: 20%"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchCommentCount"
        placeholder="请输入评论数"
        clearable
        size="medium"
        type="text"
        style="width: 10%"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-date-picker
        v-model="searchForm.searchDatetime"
        size="medium"
        type="datetimerange"
        value-format="yyyy-MM-dd HH:mm:ss"
        :picker-options="pickerOptions"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        align="right"
        clearable
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
      <el-table-column align="center" label="序号" width="50">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" sortable prop="title" label="标题">
        <template slot-scope="scope">
          <router-link tag="a" :to="{ path: '/article/edit', query: { title: scope.row.title }}">
            <a>
              {{ scope.row.title }}
            </a>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="createTime" label="创建时间" width="200">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="commentCount" label="评论数" width="90">
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
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
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
        this.data = response.data
        this.listLoading = false
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
    }
  }
}
</script>

<style scoped>
  a:hover{
    color: #409EFF;
  }
</style>
