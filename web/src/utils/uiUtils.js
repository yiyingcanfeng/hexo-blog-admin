import { Message } from 'element-ui'

export function showSuccessMessage(msg) {
  Message({
    message: msg,
    type: 'success',
    duration: 3 * 1000,
    showClose: true
  })
}

export function showErrorMessage(msg) {
  Message({
    message: msg,
    type: 'error',
    duration: 3 * 1000,
    showClose: true
  })
}

export default {
  showSuccessMessage,
  showErrorMessage
}

