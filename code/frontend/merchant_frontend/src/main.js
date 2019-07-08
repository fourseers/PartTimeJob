import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import Vuex from 'vuex'
import routes from './router/path.js'
import token from './util/token.js'
import iView from 'iview'
import axios from 'axios'
import qs from 'qs'

import 'iview/dist/styles/iview.css';

import echarts from 'echarts'
Vue.prototype.$echarts = echarts

axios.defaults.baseURL = "http://202.120.40.8:30552";
//部署服务器

Vue.prototype.$qs = qs;
Vue.prototype.axios = axios;
Vue.prototype.$token = token;

const router = new VueRouter({
  mode: 'history',
  routes: routes
});
Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(iView);
Vue.use(Vuex);

new Vue({
  router,
  render: h => h(App),
  baseURL:axios.defaults.baseURL
}).$mount('#app');
