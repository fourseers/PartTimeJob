
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
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
    }
]

export default routes;