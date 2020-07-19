import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'dashboard',
    meta: { title: 'dashboard', icon: 'dashboard' },
    children: [{
      path: 'dashboard',
      name: 'dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'dashboard', icon: 'dashboard' }
    }]
  },
  {
    path: '/article',
    component: Layout,
    name: 'ArticleManage',
    meta: { title: 'articleManage', icon: 'article' },
    redirect: '/article/list',
    children: [
      {
        path: 'list',
        name: 'ArticleList',
        component: () => import('@/views/article/index'),
        meta: { title: 'articleList', icon: 'article' }
      },
      {
        path: 'edit',
        name: 'EditArticle',
        component: () => import('@/views/article/edit'),
        meta: { title: 'editArticle', icon: 'edit' },
        hidden: true
      },
      {
        path: 'add',
        name: 'AddArticle',
        component: () => import('@/views/article/add'),
        meta: { title: 'addArticle', icon: 'edit' },
        hidden: true
      }
    ]
  },
  {
    path: '/comment',
    component: Layout,
    redirect: '/comment/list',
    name: 'CommentManage',
    meta: { title: 'commentManage', icon: 'comment' },
    children: [
      {
        path: 'list',
        name: 'CommentList',
        component: () => import('@/views/comment/index'),
        meta: { title: 'commentList', icon: 'comment' }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: '/user/list',
    name: 'UserManage',
    meta: { title: 'userManage', icon: 'user' },
    children: [
      {
        path: 'list',
        name: 'UserList',
        component: () => import('@/views/user/list'),
        meta: { title: 'userList', icon: 'user' }
      }
    ]
  },
  {
    path: '/systemSettings',
    component: Layout,
    redirect: '/systemSettings/index',
    name: 'SystemSettings',
    meta: { title: 'systemSettings', icon: 'system' },
    children: [
      {
        path: 'index',
        name: 'SystemSettings',
        component: () => import('@/views/SystemSettings/index'),
        meta: { title: 'systemSettings', icon: 'system' }
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    name: 'BlogIndex',
    meta: { title: 'blogIndex', icon: 'link' },
    children: [
      {
        path: 'https://www.movefeng.com',
        meta: { title: 'blogIndex', icon: 'link' }
      }
    ]
  },
  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]
export const asyncRoutes = [
]
const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
