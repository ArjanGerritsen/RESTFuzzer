import Vue from "vue";
import Router from "vue-router";

import Projects from "./components/projects/Projects";
import AdministrativeTasks from "./components/administrative/tasks/Tasks";
import About from "./components/About.vue";

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
      path: "/administrative-tasks",
      name: "administrative-tasks",
      component: AdministrativeTasks
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
    }
  ]
});