<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="primary" float="top" style="margin-bottom:15px;">
      <b-navbar-nav>
        <b-navbar-brand href="/">RESTFuzzer</b-navbar-brand>
        <b-nav-item href="#" to="/suts">Systems under test</b-nav-item>
        <b-nav-item-dropdown ref="fuzzing" text="Fuzzing">
          <b-dropdown-item href="#" to="/projects">Projects</b-dropdown-item>
          <b-dropdown-item href="#" to="/dictionaries">Dictionaries</b-dropdown-item>
          <b-dropdown-item href="#" to="/configurations">Configurations</b-dropdown-item>
        </b-nav-item-dropdown>
        <b-nav-item href="#" to="/tasks">Tasks</b-nav-item>
        <b-nav-item href="#" to="/about">About</b-nav-item>
      </b-navbar-nav>
    </b-navbar>
  </div>
</template>
