import Vue from "vue";
import Router from "vue-router";

import Projects from "./components/projects/projects";
import Settings from "./components/settings/settings";
import Tasks from "./components/tasks/tasks";
import About from "./components/other/about";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/projects",
      name: "projects",
      component: Projects
    },
    {
      path: "/settings",
      name: "settings",
      component: Settings
    },
    {
      path: "/about",
      name: "about",
      component: About
      // // route level code-splitting
      // // this generates a separate chunk (about.[hash].js) for this route
      // // which is lazy-loaded when the route is visited.
      // component: () =>
      //   import(/* webpackChunkName: "about" */ "./views/About.vue")
    },
    {
      path: "/admin/tasks",
      name: "tasks",
      component: Tasks
    }
  ]
});