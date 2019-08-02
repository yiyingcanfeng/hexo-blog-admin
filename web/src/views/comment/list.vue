<template>
  <div class="app-container">
    <div class="search-container">
      <el-dropdown trigger="click">
        <el-button type="primary" size="medium" :loading="batchOperateLoading">
          批量操作<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="deleteBatch">批量删除</el-dropdown-item>
          <el-dropdown-item @click.native="passAuditBatch">通过审核</el-dropdown-item>
          <el-dropdown-item @click.native="cancelAuditBatch">取消审核</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input
        v-model="searchForm.searchUsername"
        placeholder="请输入用户名"
        clearable
        size="medium"
        type="text"
        style="width: 10%"
        @keyup.native="enterSearch(searchForm.searchUsername,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchTitle"
        placeholder="请输入文章标题"
        clearable
        size="medium"
        type="text"
        style="width: 20%"
        @keyup.native="enterSearch(searchForm.searchTitle,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchComment"
        placeholder="请输入评论内容"
        clearable
        size="medium"
        type="text"
        style="width: 20%"
        @keyup.native="enterSearch(searchForm.searchComment,$event)"
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
      style="width: 100%"
      :default-sort="{prop: 'createTime', order: 'descending'}"
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="40"
      />
      <el-table-column align="center" label="序号" width="65">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="用户名" sortable prop="username" width="90">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="回复的人" width="95">
        <template slot-scope="scope">
          {{ scope.row.replyUserName }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="评论时间" sortable prop="createTime" width="175">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column
        align="left"
        sortable
        label="文章"
        :filters="titleFilterArray"
        :filter-method="titleFilter"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          {{ scope.row.articleTitle }}
        </template>
      </el-table-column>
      <el-table-column align="left" label="评论内容">
        <template slot-scope="scope">
          {{ scope.row.content }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="审核状态" width="90">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.auditStatus===auditStatus.AUDIT_SUCCESS" type="success">通过</el-tag>
          <el-tag v-if="scope.row.auditStatus===auditStatus.WAIT_AUDIT" type="danger">未通过</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作" width="275">
        <template slot-scope="scope">
          <el-button v-if="scope.row.auditStatus===auditStatus.AUDIT_SUCCESS" type="danger" :loading="operateLoading" @click="cancelAudit(scope.row.id)">取消审核</el-button>
          <el-button v-if="scope.row.auditStatus===auditStatus.WAIT_AUDIT" type="success" :loading="operateLoading" @click="passAudit(scope.row.id)">通过审核</el-button>
          <el-button type="danger" @click="deleteComment(scope.row.id)">删除</el-button>
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
import { param2Obj, arrayUnique } from '@/utils/index'
import AuditStatus from '@/utils/const'
export default {
  name: 'List',
  data() {
    return {
      data: {
        list: []
      },
      auditStatus: AuditStatus,
      titleFilterArray: [],
      listLoading: true,
      operateLoading: false,
      batchOperateLoading: false,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        searchUsername: '',
        searchTitle: '',
        searchComment: '',
        searchDatetime: ''
      },
      multipleSelection: [],
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
  mounted() {
    const param = param2Obj(window.location.href)
    if (param.searchTitle) {
      this.searchForm.searchTitle = param.searchTitle
    }
    if (param.searchUsername) {
      this.searchForm.searchUsername = param.searchUsername
    }
    if (param.immediateSearch) {
      this.fetchData(this.currentPage, this.pageSize, this.searchForm)
      return
    }

    this.fetchData(this.currentPage, this.pageSize)
  },
  methods: {
    fetchData(pageNum, pageSize, searchParams) {
      this.listLoading = true
      request({
        url: '/comment/listAll',
        method: 'post',
        params: {
          pageNum: pageNum,
          pageSize: pageSize
        },
        data: searchParams
      }).then(response => {
        this.data = response.data
        let titleArr = response.data.list.map(a => a.articleTitle)
        titleArr = arrayUnique(titleArr)
        this.titleFilterArray = []
        for (const titleArrElement of titleArr) {
          this.titleFilterArray.push({
            text: titleArrElement,
            value: titleArrElement
          })
        }
        this.listLoading = false
      })
    },
    cancelAudit(id) {
      request({
        url: '/comment/audit',
        method: 'post',
        params: {
          id: id,
          audit: AuditStatus.WAIT_AUDIT
        }
      }).then(response => {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
        this.fetchData(this.currentPage, this.pageSize)
      })
    },
    passAudit(id) {
      request({
        url: '/comment/audit',
        method: 'post',
        params: {
          id: id,
          audit: AuditStatus.AUDIT_SUCCESS
        }
      }).then(response => {
        this.listLoading = false
        this.$message({
          message: response.message,
          type: 'success',
          duration: 3 * 1000,
          showClose: true
        })
        this.fetchData(this.currentPage, this.pageSize)
      })
    },
    deleteComment(id) {
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        request({
          url: '/comment/delete',
          method: 'post',
          params: {
            id: id
          }
        }).then(response => {
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.fetchData(this.currentPage, this.pageSize)
        })
      })
    },
    searchComment() {
      this.fetchData(this.currentPage, this.pageSize, this.searchForm)
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData(this.currentPage, val, this.searchForm)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchData(val, this.pageSize, this.searchForm)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    titleFilter(value, row) {
      return row.articleTitle === value
    },
    enterSearch(message, event) {
      // 捕获enter事件
      if (event.keyCode === 13) {
        this.fetchData(this.currentPage, this.pageSize, this.searchForm)
      }
    },
    passAuditBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选择要操作的数据！',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      const sendData = []
      for (const item of this.multipleSelection) {
        if (item.auditStatus === AuditStatus.AUDIT_SUCCESS) {
          continue
        }
        const obj = {
          id: item.id,
          audit: AuditStatus.AUDIT_SUCCESS
        }
        sendData.push(obj)
      }
      if (sendData.length === 0) {
        this.$message({
          message: '没有需要审核通过的评论！',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm('确认操作？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        this.batchOperateLoading = true
        request({
          url: '/comment/auditBatch',
          method: 'post',
          data: sendData
        }).then(response => {
          this.batchOperateLoading = false
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.fetchData(this.currentPage, this.pageSize)
        }).catch(() => {
          this.batchOperateLoading = false
        })
      })
    },
    cancelAuditBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选择要操作的数据！',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      const sendData = []
      for (const item of this.multipleSelection) {
        if (item.auditStatus === AuditStatus.WAIT_AUDIT) {
          continue
        }
        const obj = {
          id: item.id,
          audit: AuditStatus.WAIT_AUDIT
        }
        sendData.push(obj)
      }
      if (sendData.length === 0) {
        this.$message({
          message: '没有需要取消审核的评论！',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm('确认操作？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        this.batchOperateLoading = true
        request({
          url: '/comment/auditBatch',
          method: 'post',
          data: sendData
        }).then(response => {
          this.batchOperateLoading = false
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.fetchData(this.currentPage, this.pageSize)
        }).catch(() => {
          this.batchOperateLoading = false
        })
      })
    },
    deleteBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选择要删除的数据！',
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm('确认删除？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(_ => {
        this.batchOperateLoading = true
        request({
          url: '/comment/deleteBatch',
          method: 'post',
          data: {
            id: this.multipleSelection.map(a => a.id)
          }
        }).then(response => {
          this.batchOperateLoading = false
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.fetchData(this.currentPage, this.pageSize)
        }).catch(() => {
          this.batchOperateLoading = false
        })
      })
    }
  }
}

</script>

<style scoped>
.pagination{
  margin-top: 10px;
}

</style>
