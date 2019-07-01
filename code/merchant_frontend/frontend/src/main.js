import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'

import Vuex from 'vuex'


// routers
import routes from './router/path.js'

import iView from 'iview'

import 'iview/dist/styles/iview.css';



const router = new VueRouter({
  mode: 'history',
  routes: routes
})
Vue.config.productionTip = false

Vue.use(VueRouter);
Vue.use(iView);
Vue.use(Vuex)

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
