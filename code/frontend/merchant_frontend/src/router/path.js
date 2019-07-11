
import testLogin from '../views/testLogin.vue'
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
import ShopDetail from '../views/ShopDetail.vue'

import Logout from '../views/Logout.vue'

const  routes =[
    {
        path: '/',
        view: 'Login',
        component:testLogin
    },
    {
        path: '/login',
        name: 'login',
        component:testLogin
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
        name: 'postjob',
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
    ,
    {
        path: '/shopdetail',
        name: 'shopdetail',
        component:ShopDetail
    }
    ,
    {
        path: '/logout',
        name: 'logout',
        component:Logout
    }




]

export default routes;
