import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/zh-CN' // lang i18n

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import '@/icons' // icon
import '@/permission' // permission control
import i18n from './lang'
import '@/utils/directives.js'
import utils from '@/utils'
import uiUtils from '@/utils/uiUtils'

Vue.use(ElementUI, { i18n: (key, value) => i18n.t(key, value) })
Vue.prototype.$uiUtils = uiUtils
Vue.prototype.$utils = utils

Vue.config.productionTip = false
if (process.env.NODE_ENV === 'development') {
  Vue.config.devtools = true
}
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
