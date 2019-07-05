
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import PostJob from '../views/PostJob.vue'
import AddShop from '../views/AddShop.vue'
import ManageShop from '../views/ManageShop.vue'
import InfoConfig from '../views/InfoConfig.vue'
import ConfirmCheckin from '../views/ConfirmCheckin.vue'
import MonthlyBill from '../views/MonthlyBill.vue'
import AddCompany from '../views/AddCompany.vue'
import SalaryStat from '../views/SalaryStat.vue'
import CheckinStat from '../views/CheckinStat.vue'
import ScreenCV from '../views/ScreenCV.vue'
import PostStat from '../views/PostStat.vue'

import ShowJobs from '../views/ShowJobs.vue'
const  routes =[
    {
        path: '/',
        view: 'Login',
        component:Login
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
    },
    {
        path: '/confirmcheckin',
        name: 'confirmcheckin',
        component:ConfirmCheckin
    },
    {
        path: '/monthlybill',
        name: 'monthlybill',
        component:MonthlyBill
    },
    {
        path: '/addcompany',
        name: 'addcompany',
        component:AddCompany
    }
    ,
    {
        path: '/salarystat',
        name: 'salarystat',
        component:SalaryStat
    }
    ,
    {
        path: '/checkinstat',
        name: 'checkinstat',
        component:CheckinStat
    }

    ,
    {
        path: '/poststat',
        name: 'poststat',
        component:PostStat
    }
    ,
    {
        path: '/screenCV',
        name: 'screenCV',
        component:ScreenCV
    }
    ,
    {
        path: '/showjobs',
        name: 'showjobs',
        component:ShowJobs
    }




]

export default routes;
