<template>
  <div id="app">
          <Toolbar/>
        <div id="core-view">
          <v-fade-transition mode="out-in">
            <router-view />
          </v-fade-transition>
        </div>
  </div>
</template>

<script>

import Login from './views/Login.vue'
import Register from './views/Register.vue'
import Toolbar from './components/Toolbar.vue'
export default {
  name: 'app',
  components: {
    Login,
    Register,
    Toolbar
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
