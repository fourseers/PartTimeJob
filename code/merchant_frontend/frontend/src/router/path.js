
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'

import PostJob from '../views/PostJob.vue'
import AddShop from '../views/AddShop.vue'

import ManageShop from '../views/ManageShop.vue'
import InfoConfig from '../views/InfoConfig.vue'
const  routes =[
        {
            path: '/',
            view: 'Login'
        },
    {
        path: '/login',
        name: 'Login',
        component:Login
    },
    {
        path: '/register',
        name: 'register',
        component:Register
    },
    {
        path: '/addshop',
        name: 'addshop',
        component:AddShop
    }
    ,
    {
        path: '/manageshop',
        name: 'manageshop',
        component:ManageShop
    },
    {
        path: '/postjob',
        name: 'post job',
        component:PostJob
    },
    {
        path: '/infoconfig',
        name: 'info config',
        component:InfoConfig
    }

]

export default routes;