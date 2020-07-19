import { login, logout, getInfo } from '@/api/user'
import { getToken, setToken, removeToken, getUsername, removeAdminInfo } from '@/utils/auth'
import { resetRouter } from '@/router'

const state = {
  token: getToken(),
  username: getUsername(),
  nickname: '',
  email: '',
  avatar: '',
  roles: []
}

const mutations = {
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_USERNAME: (state, username) => {
    state.username = username
  },
  SET_NICKNAME: (state, nickname) => {
    state.nickname = nickname
  },
  SET_EMAIL: (state, email) => {
    state.email = email
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password, jsessionid } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password, jsessionid: jsessionid }).then(response => {
        const { data } = response
        commit('SET_TOKEN', data.token)
        commit('SET_USERNAME', data.username)
        // commit('SET_NICKNAME', data.nickname)
        commit('SET_EMAIL', data.email)
        setToken(data.token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.username).then(response => {
        const { data } = response
        if (!data) {
          reject('验证失败，请重新登录')
        }
        const { nickname, avatar, email } = data
        commit('SET_NICKNAME', nickname)
        commit('SET_AVATAR', avatar)
        commit('SET_EMAIL', email)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        removeAdminInfo()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

