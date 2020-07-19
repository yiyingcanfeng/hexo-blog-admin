import Cookies from 'js-cookie'

const TokenKey = 'hexoBlogAdminToken'
const adminInfoKey = 'hexoBlogAdminInfo'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function getUsername() {
  return Cookies.get(adminInfoKey) ? JSON.parse(Cookies.get(adminInfoKey)).username : ''
}

export function setAdminInfo(adminInfo) {
  return Cookies.set(adminInfoKey, JSON.stringify(adminInfo), { expires: 3 })
}

export function getAdminInfo() {
  return Cookies.get(adminInfoKey) ? JSON.parse(Cookies.get(adminInfoKey)) : {}
}

export function removeAdminInfo() {
  return Cookies.remove(adminInfoKey)
}
