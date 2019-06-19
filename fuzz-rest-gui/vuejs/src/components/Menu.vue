<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="primary" fixed="top">
      <b-navbar-brand href="/">FuzzREST</b-navbar-brand>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <router-link class="nav-link" to="/projects">Projects</router-link>
          <router-link class="nav-link" to="/tasks">Tasks</router-link>
          <router-link class="nav-link" to="/about">About</router-link>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>