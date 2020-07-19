<template>
  <div class="settings-container">
    <h2>{{ $t('systemSettings.title') }}</h2>
    <el-form
      v-loading="systemConfigFormLoading"
      label-position="right"
      :label-width="$t('systemSettings.formLabelWidth')"
    >
      <el-form-item :label="$t('systemSettings.hexoVisitUrl')">
        <el-tooltip class="item" effect="dark" :content="$t('systemSettings.hexoVisitUrlTooltip')" placement="right">
          <el-input
            v-model="systemConfigForm.hexoVisitUrl"
            :placeholder="$t('systemSettings.hexoVisitUrlPlaceholder')"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.hexoAdminUrl')">
        <el-tooltip class="item" effect="dark" :content="$t('systemSettings.hexoAdminUrlTooltip')" placement="right">
          <el-input
            v-model="systemConfigForm.hexoAdminUrl"
            :placeholder="$t('systemSettings.hexoAdminUrl')"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.hexoPath')">
        <el-input
          v-model="systemConfigForm.hexoPath"
          :placeholder="$t('systemSettings.hexoPathPlaceholder')"
          clearable
          size="medium"
          type="text"
          style="width: 20%"
        />
      </el-form-item>
      <el-form-item :label="$t('systemSettings.articlePath')">
        <el-tooltip class="item" effect="dark" :content="$t('systemSettings.articlePathTooltip')" placement="right">
          <el-input
            v-model="systemConfigForm.articlePath"
            :placeholder="$t('systemSettings.articlePathPlaceholder')"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.publicPath')">
        <el-tooltip class="item" effect="dark" placement="right">
          <div slot="content" v-html="$t('systemSettings.publicPathTooltip')">
          </div>
          <el-input
            v-model="systemConfigForm.publicPath"
            :placeholder="$t('systemSettings.publicPathPlaceholder')"
            clearable
            size="medium"
            type="text"
            style="width: 20%"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.adminMailReport')">
        <el-tooltip class="item" effect="dark" :content="$t('systemSettings.adminMailReportTooltip')" placement="right">
          <el-switch
            v-model="systemConfigForm.adminMailReport"
            :active-value="1"
            :inactive-value="0"
            :active-text="$t('systemSettings.switch.active')"
            :inactive-text="$t('systemSettings.switch.inactive')"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.userMailReport')">
        <el-tooltip class="item" effect="dark" :content="$t('systemSettings.userMailReportTooltip')" placement="right">
          <el-switch
            v-model="systemConfigForm.userMailReport"
            :active-value="1"
            :inactive-value="0"
            :active-text="$t('systemSettings.switch.active')"
            :inactive-text="$t('systemSettings.switch.inactive')"
          />
        </el-tooltip>
      </el-form-item>
      <el-form-item :label="$t('systemSettings.smtpSender')">
        <el-input
          v-model="systemConfigForm.smtpSender"
          :placeholder="$t('systemSettings.smtpSenderPlaceholder')"
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
        >{{ $t('systemSettings.commit') }}
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
      systemConfigFormLoading: false,
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
      this.systemConfigFormLoading = true
      request({
        url: '/system/listSettings',
        method: 'get'
      }).then(response => {
        this.systemConfigFormLoading = false
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
          message: this.$t('systemSettings.operateSuccess'),
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
