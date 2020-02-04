<template></template>

<script>
export default {
  data() {
    return {
      toast: this.$toast,
      timeout: null
    };
  },
  methods: {
    displayInfo(title, text) {
      this.$bvToast.toast(text, {
        title: title,
        variant: "primary",
        noAutoHide: false,
        appendToast: true
      });
    },
    diaplayWarning(text, error) {
      this.$toast.toast(
        `${text} : ${error.response.status} - ${error.response.data}`,
        {
          title: "Attention",
          variant: "warning",
          noAutoHide: true,
          appendToast: true
        }
      );
    },
    diaplayError(title, error) {
      this.$toast.toast(this.getMsg(error), {
        title: title,
        variant: "danger",
        noAutoHide: false,
        autoHideDelay: 20000,
        appendToast: true
      });
    },
    getMsg(error) {
      const h = this.$createElement;

      let violoations = [];
      if (error.response.data.violations) {
        error.response.data.violations.forEach(v =>
          violoations.push(h("li", { style: "font-style: italic;" }, v))
        );
      }

      let msg = h("span", {}, [
        h(
          "div",
          { style: "margin: 10px 0px 15px 0px;" },
          `${error.response.statusText} (${error.response.status}):`
        ),
        violoations.length === 0 ? "-" : h("ul", {}, violoations)
      ]);

      return msg;
    },
    displayMessages() {
      // TODO
      this.messages.forEach(msg => this.displayInfo("Yo", msg.text));
      this.$store.commit('messages_clear');
      this.timeout = setTimeout(this.displayMessages, 500);
    }
  },
  computed: {
    messages: function() {
      return this.$store.getters.messages;
    }
  },
  destroyed: function() {
    clearTimeout(this.timeout);
  },
  created: function() {
    this.displayMessages();
  }
};
</script>
