import request from '@/utils/request'

export function login(data) {
  const headers = {}
  if (data.jsessionid) {
    headers.jsessionid = data.jsessionid
  }
  return request({
    url: '/admin/login',
    method: 'post',
    data: {
      username: data.username,
      password: data.password
    },
    headers: headers
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
