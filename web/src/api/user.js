import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/login',
    method: 'post',
    data
  })
}

export function getInfo(username) {
  return request({
    url: '/admin/info',
    method: 'get',
    params: { username }
  })
}

export function logout() {
  return request({
    url: '/admin/logout',
    method: 'post'
  })
}
