<template>
  <div class="app-container">
    <div class="search-container">
      <el-input
        v-model="searchForm.searchUsername"
        placeholder="请输入用户名"
        clearable
        size="medium"
        type="text"
        style="width: 10%"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchEmail"
        placeholder="请输入邮箱"
        clearable
        size="medium"
        type="text"
        style="width: 10%"
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
      style="width: 100%"
    >
      <el-table-column align="center" label="序号" width="50">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="用户名">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="邮箱">
        <template slot-scope="scope">
          <a :href="'mailto://'+scope.row.email">{{ scope.row.email }}</a>
        </template>
      </el-table-column>
      <el-table-column align="left" label="网站">
        <template slot-scope="scope">
          <a v-if="scope.row.website!==''" :href="'//'+scope.row.website">{{ scope.row.website }}</a>
          <span v-else>{{ scope.row.website }}</span>
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
  name: 'List',
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
        searchUsername: '',
        searchEmail: ''
      }
    }
  },
  mounted() {
    this.fetchData(this.currentPage, this.pageSize)
  },
  methods: {
    fetchData(pageNum, pageSize, searchParams) {
      this.listLoading = true
      request({
        url: '/user/list',
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
