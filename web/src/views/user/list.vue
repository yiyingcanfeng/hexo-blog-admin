<template>
  <div class="app-container">
    <div class="search-container">
      <el-dropdown trigger="click">
        <el-button type="primary" size="medium" :loading="batchOperateLoading">
          {{ $t('userManage.userList.batchOperate') }}<i class="el-icon-arrow-down el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="deleteBatch">{{ $t('userManage.userList.deleteBatch') }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <el-input
        v-model="searchForm.searchUsername"
        :placeholder="$t('userManage.userList.searchUsername')"
        clearable
        size="medium"
        type="text"
        style="width: 15%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchUsername,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchEmail"
        :placeholder="$t('userManage.userList.searchEmail')"
        clearable
        size="medium"
        type="text"
        style="width: 15%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchEmail,$event)"
        @clear="fetchData(currentPage,pageSize,searchForm)"
      />
      <el-input
        v-model="searchForm.searchCommentCount"
        :placeholder="$t('userManage.userList.searchCommentCount')"
        clearable
        size="medium"
        type="number"
        style="width: 18%;margin-left: 5px;"
        @keyup.native="enterSearch(searchForm.searchCommentCount,$event)"
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
      @selection-change="handleSelectionChange"
    >
      <el-table-column
        type="selection"
        width="40"
      />
      <el-table-column align="center" :label="$t('userManage.userList.table.number.columnName')" width="50">
        <template slot-scope="scope">
          {{ scope.$index+1 }}
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('userManage.userList.table.username.columnName')">
        <template slot-scope="scope">
          <router-link
            tag="a"
            :to="{ path: '/comment/list', query: { searchUsername: scope.row.username, immediateSearch:true }}"
          >
            <a>
              {{ scope.row.username }}
            </a>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('userManage.userList.table.email.columnName')">
        <template slot-scope="scope">
          <a :href="'mailto://'+scope.row.email">{{ scope.row.email }}</a>
        </template>
      </el-table-column>
      <el-table-column align="left" :label="$t('userManage.userList.table.website.columnName')">
        <template slot-scope="scope">
          <a v-if="scope.row.website!==''" :href="'//'+scope.row.website">{{ scope.row.website }}</a>
          <span v-else>{{ scope.row.website }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" sortable prop="commentCount" :label="$t('userManage.userList.table.commentCount.columnName')" :width="$t('userManage.userList.table.commentCount.width')">
        <template slot-scope="scope">
          <router-link v-if="scope.row.commentCount !== 0" tag="a" :to="{ path: '/comment/list', query: { searchUsername: scope.row.username, immediateSearch:true }}">
            <a>
              {{ scope.row.commentCount }}
            </a>
          </router-link>
          <span v-else>
            {{ scope.row.commentCount }}
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" :label="$t('userManage.userList.table.operate.columnName')" :width="$t('userManage.userList.table.operate.width')">
        <template slot-scope="scope">
          <el-button type="danger" :loading="operateLoading" @click="deleteUser(scope.row.id)">{{ $t('userManage.userList.table.operate.deleteUser') }}</el-button>
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
      operateLoading: false,
      batchOperateLoading: false,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        searchUsername: '',
        searchEmail: ''
      },
      multipleSelection: []
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
    deleteUser(id) {
      this.$confirm(this.$t('userManage.message.deleteConfirmMessage'), this.$t('userManage.message.deleteConfirmMessageTitle'), {
        confirmButtonText: this.$t('userManage.message.confirm'),
        cancelButtonText: this.$t('userManage.message.cancel'),
        type: 'warning'
      }).then(() => {
        this.operateLoading = true
        request({
          url: '/user/delete',
          method: 'post',
          params: {
            id: id
          }
        }).then((response) => {
          this.operateLoading = false
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
    deleteBatch() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: this.$t('userManage.message.chooseDataDelete'),
          type: 'error',
          duration: 3 * 1000,
          showClose: true
        })
        return
      }
      this.$confirm(this.$t('userManage.message.deleteConfirmMessage'), this.$t('userManage.message.deleteConfirmMessageTitle'), {
        confirmButtonText: this.$t('userManage.message.confirm'),
        cancelButtonText: this.$t('userManage.message.cancel'),
        type: 'warning'
      }).then(_ => {
        this.batchOperateLoading = true
        request({
          url: '/user/deleteBatch',
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
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData(this.currentPage, val)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchData(val, this.pageSize)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
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
