import axios from "axios";
import store from "../../store";

export default class RestService {
  static toast;

  constructor(toast) {
    RestService.toast = toast;
  }

  getSuts() {
    axios
      .get("/rest/suts")
      .then(response => {
        store.commit("suts_set", { suts: response.data });
      })
      .catch(error => {
        RestService.handleError("Couldn't retrieve suts.", error);
        store.commit("suts_set", { suts: [] });
      });
  }

  static handleError(text, error) {
    this.toast.toast(text, {
      title: `AJAX call failed: ${error}`,
      variant: "danger",
      noAutoHide: true,
      appendToast: true
    });
  }

  static displayToast(title, text) {
    this.toast.toast(text, {
      title: title,
      variant: "primary",
      noAutoHide: false,
      appendToast: true
    });
  }
}