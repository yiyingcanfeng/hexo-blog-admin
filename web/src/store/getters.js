const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  username: state => state.user.username,
  nickname: state => state.user.nickname,
  email: state => state.user.email
}
export default getters
