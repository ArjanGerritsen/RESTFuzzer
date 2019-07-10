export default class MessageService {
  toast;

  constructor(toast) {
    this.toast = toast;
  }

  error(text, error) {
    this.toast.toast(`${text} : ${error.response.status} - ${error.response.data.message}`, {
      title: 'Something went wrong',
      variant: 'danger',
      noAutoHide: true,
      appendToast: true
    })
  }

  warn(text, error) {
    this.toast.toast(`${text} : ${error.response.status} - ${error.response.data.message}`, {
      title: 'Attention',
      variant: 'warning',
      noAutoHide: true,
      appendToast: true
    })
  }  

  info(title, text) {
    this.toast.toast(text, {
      title: title,
      variant: 'primary',
      noAutoHide: false,
      appendToast: true
    })
  }
}