export default class MessageService {
  vueComponent;
  toast;

  constructor(vueComponent) {
    this.vueComponent = vueComponent;
    this.toast = vueComponent.$bvToast;
  }

  error(title, error) {
    this.toast.toast(this.getMsg(error), {
      title: title,
      variant: 'danger',
      noAutoHide: false,
      autoHideDelay: 20000,
      appendToast: true
    })
  }

  warn(text, error) {
    this.toast.toast(`${text} : ${error.response.status} - ${error.response.data}`, {
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

  getMsg(error) {
    const h = this.vueComponent.$createElement;

    let violoations = [];
    if (error.response.data.violations) {
      error.response.data.violations.forEach(v => violoations.push(h('li', { style : 'font-style: italic;' }, v)));
    }

    let msg = h('span', {}, [
         h('div', { style : 'margin: 10px 0px 15px 0px;' }, `${error.response.statusText} (${error.response.status}):` ),
         violoations.length === 0 ? '-' : h('ul', {}, violoations)
       ]
    );

    return msg;
  }
}