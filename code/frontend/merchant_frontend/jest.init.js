import VueTestUtils from '@vue/test-utils'

import token from "./src/util/token.js"
import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from "./src/router/path";

import axios from 'axios'
axios.defaults.baseURL = "http://47.103.112.85:30552";
VueTestUtils.config.mocks['$token'] =  token
VueTestUtils.config.mocks['$Message'] =  {
    success:function()
    {
        return "success";
    },
    error:function()
    {
        return "error";
    },
    warning:function()
    {
        return "warning";
    }
}
VueTestUtils.config.mocks['$root'] =Vue

const router = new VueRouter({
    mode: 'history',
    routes: routes
});
VueTestUtils.config.mocks['$router']= router
