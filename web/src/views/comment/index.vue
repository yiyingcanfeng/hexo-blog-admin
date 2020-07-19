<template>
  <div class="app-container">
    <div class="search-container">
      <el-dropdown trigger="click">
        <el-button type="primary" size="medium" :loading="batchOperateLoading">
          {{ $t('commentManage.commentList.batchOperate') }}<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="deleteBatch">{{ $t('commentManage.commentList.deleteBatch') }}</el-dropdown-item>
          <el-dropdown-item @click.native="passAuditBatch">{{ $t('commentManage.commentList.passAuditBatch') }}</el-dropdown-item>
          <el-dropdown-item @click.native="cancelAuditBatch">{{ $t('commentManage.commentList.cancelAuditBatch') }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input
        v-model="searchForm.searchUsername"
        :placeholder="$t('commentManage.commentList.searchUsername')"
        clearable
        size="medium"
        type="text"
        style="width: 15%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchUsername,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchTitle"
        :placeholder="$t('commentManage.commentList.searchTitle')"
        clearable
        size="medium"
        type="text"
        style="width: 20%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchTitle,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchComment"
        :placeholder="$t('commentManage.commentList.searchComment')"
        clearable
        size="medium"
        type="text"
        style="width: 20%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchComment,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-date-picker
        v-model="searchForm.searchDatetime"
        size="medium"
        type="datetimerange"
        value-format="yyyy-MM-dd HH:mm:ss"
        :picker-options="pickerOptions"
        :range-separator="$t('commentManage.commentList.searchDatetimeTo')"
        :start-placeholder="$t('commentManage.commentList.searchDatetimeStart')"
        :end-placeholder="$t('commentManage.commentList.searchDatetimeEnd')"
        align="right"
        clearable
        style="margin-left: 5px;"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-button
        type="primary"
        size="small"
        icon="el-icon-search"
        style="margin-left: 5px;"
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
      <el-table-column align="center" :label="$t('commentManage.commentList.table.number.columnName')" width="65">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('commentManage.commentList.table.username.columnName')" sortable prop="username" :width="$t('commentManage.commentList.table.username.width')">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('commentManage.commentList.table.replyUsername.columnName')" :width="$t('commentManage.commentList.table.replyUsername.width')">
        <template slot-scope="scope">
          {{ scope.row.replyUserName }}
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('commentManage.commentList.table.createTime.columnName')" sortable prop="createTime" :width="$t('commentManage.commentList.table.createTime.width')">
        <template slot-scope="scope">
          {{ scope.row.createTime }}
        </template>
      </el-table-column>
      <el-table-column
        align="left"
        sortable
        :label="$t('commentManage.commentList.table.articleTitle.columnName')"
        :filters="titleFilterArray"
        :filter-method="titleFilter"
        filter-placement="bottom-end"
      >
        <template slot-scope="scope">
          {{ scope.row.articleTitle }}
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('commentManage.commentList.table.content.columnName')">
        <template slot-scope="scope">
          {{ scope.row.content }}
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('commentManage.commentList.table.auditStatus.columnName')" :width="$t('commentManage.commentList.table.auditStatus.width')">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.auditStatus===auditStatus.AUDIT_SUCCESS" type="success">{{ $t('commentManage.commentList.table.auditSuccess') }}</el-tag>
          <el-tag v-if="scope.row.auditStatus===auditStatus.WAIT_AUDIT" type="danger">{{ $t('commentManage.commentList.table.waitAudit') }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('commentManage.commentList.table.operate.columnName')" :width="$t('commentManage.commentList.table.operate.width')">
        <template slot-scope="scope">
          <el-button v-if="scope.row.auditStatus===auditStatus.AUDIT_SUCCESS" size="small" type="danger" :loading="operateLoading" @click="cancelAudit(scope.row.id)">{{ $t('commentManage.commentList.table.operate.cancelAudit') }}</el-button>
          <el-button v-if="scope.row.auditStatus===auditStatus.WAIT_AUDIT" size="small" type="success" :loading="operateLoading" @click="passAudit(scope.row.id)">{{ $t('commentManage.commentList.table.operate.passAudit') }}</el-button>
          <el-button size="small" type="danger" @click="deleteComment(scope.row.id)">{{ $t('commentManage.commentList.table.operate.deleteComment') }}</el-button>
          <el-button size="small" type="primary" @click="replyCommentDialogShow(scope.row)">{{ $t('commentManage.commentList.table.operate.reply') }}</el-button>
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
    <el-dialog
      v-dialogDrag
      width="35%"
      :title="$t('commentManage.replyCommentDialog.title')"
      :visible.sync="replyCommentDialogVisible"
      @close="replyCommentDialogClose"
    >
      <el-form
        label-position="right"
        label-width="100px"
      >
        <el-form-item :label="$t('commentManage.replyCommentDialog.userMail')">
          <el-input
            v-model="replyCommentForm.userMail"
            :placeholder="$t('commentManage.replyCommentDialog.userMailPlaceholder')"
            size="small"
            clearable
          />
        </el-form-item>
        <el-form-item :label="$t('commentManage.replyCommentDialog.username')">
          <el-input
            v-model="replyCommentForm.username"
            :placeholder="$t('commentManage.replyCommentDialog.usernamePlaceholder')"
            size="small"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('commentManage.replyCommentDialog.content')">
          <el-input
            v-model="replyCommentForm.content"
            :placeholder="$t('commentManage.replyCommentDialog.contentPlaceholder')"
            size="small"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="replyCommentOperateLoading" @click="replyComment">{{ $t('commentManage.replyCommentDialog.reply') }}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import { param2Obj, arrayUnique } from '@/utils/index'
import AuditStatus from '@/utils/const'
import { mapGetters } from 'vuex'
export default {
  name: 'List',
  computed: {
    ...mapGetters([
      'email', 'nickname'
    ])
  },
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
      replyCommentOperateLoading: false,
      replyCommentDialogVisible: false,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        searchUsername: '',
        searchTitle: '',
        searchComment: '',
        searchDatetime: ''
      },
      replyCommentForm: {
        userMail: '',
        articleTitle: '',
        auditStatus: 1,
        parentId: 0,
        replyUserId: 0,
        username: '',
        content: ''
      },
      multipleSelection: [],
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
      this.$confirm(this.$t('deleteConfirm.title'), this.$t('deleteConfirm.message'), {
        confirmButtonText: this.$t('deleteConfirm.confirm'),
        cancelButtonText: this.$t('deleteConfirm.cancel'),
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
    replyCommentDialogShow(item) {
      this.replyCommentForm.userMail = this.email
      this.replyCommentForm.replyUserId = item.userId
      this.replyCommentForm.articleTitle = item.articleTitle
      this.replyCommentForm.parentId = item.id
      this.replyCommentDialogVisible = true
    },
    replyComment() {
      this.replyCommentOperateLoading = true
      request({
        url: '/comment/create',
        method: 'post',
        data: this.replyCommentForm
      }).then(response => {
        this.$uiUtils.showSuccessMessage(this.$t('commentManage.message.operate'))
        this.fetchData(this.currentPage, this.pageSize)
        this.replyCommentOperateLoading = false
        this.replyCommentDialogVisible = false
      }).catch(reason => {
        this.replyCommentOperateLoading = false
      })
    },
    replyCommentDialogClose() {
      this.replyCommentForm = {
        userMail: '',
        articleTitle: '',
        auditStatus: 1,
        parentId: 0,
        replyUserId: 0
      }
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
          message: this.$t('commentManage.message.chooseData'),
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
          message: this.$t('commentManage.message.noCommentNeedAudit'),
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm(this.$t('operateConfirm.title'), this.$t('operateConfirm.message'), {
        confirmButtonText: this.$t('operateConfirm.confirm'),
        cancelButtonText: this.$t('operateConfirm.cancel'),
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
          message: this.$t('commentManage.message.chooseData'),
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
          message: this.$t('commentManage.message.noCommentNeedCancelAudit'),
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm(this.$t('operateConfirm.title'), this.$t('operateConfirm.message'), {
        confirmButtonText: this.$t('operateConfirm.confirm'),
        cancelButtonText: this.$t('operateConfirm.cancel'),
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
          message: this.$t('commentManage.message.chooseDataDelete'),
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm(this.$t('deleteConfirm.title'), this.$t('deleteConfirm.message'), {
        confirmButtonText: this.$t('deleteConfirm.confirm'),
        cancelButtonText: this.$t('deleteConfirm.cancel'),
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
