import VueTestUtils from '@vue/test-utils'

import token from "./src/util/token.js"
import Vue from 'vue'
import VueRouter from 'vue-router'
import routes from "./src/router/path";
VueTestUtils.config.mocks['$token'] =  token
VueTestUtils.config.mocks['$Message'] =  {
    success:function()
    {

    },
    error:function()
    {
        return "error";
    },
    warning:function()
    {

    }
}
VueTestUtils.config.mocks['$root'] =Vue

const router = new VueRouter({
    mode: 'history',
    routes: routes
});

VueTestUtils.config.mocks['$router']= router
