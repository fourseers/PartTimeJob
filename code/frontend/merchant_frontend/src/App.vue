<template>
    <div id="app">

        <Layout>
            <Header style="background-color: #fff" >
                    <div style="text-align:center">
                        <h1>灵活用工平台</h1>
                    </div>

            </Header>
            <Layout>
                <Sider hide-trigger
                       style="background-color: #fff">
                    <Toolbar/>
                </Sider>
                <Content  style="background-color: #fff" >
                    <v-fade-transition mode="out-in">
                        <router-view />
                    </v-fade-transition>
                </Content>
            </Layout>
            <Footer  style="background-color: #fff"></Footer>
        </Layout>


    </div>
</template>

<script>

    import Login from './views/Login.vue'
    import Register from './views/Register.vue'
    import PostJob from './views/PostJob.vue'
    import AddShop from './views/AddShop.vue'
    import ManageShop from './views/ManageShop.vue'
    import InfoConfig from './views/InfoConfig.vue'
    import Toolbar from './components/Toolbar.vue'
    import ConfirmCheckin from './views/ConfirmCheckin.vue'
    import MonthlyBill from './views/MonthlyBill.vue'
    import AddCompany from './views/AddCompany.vue'
    import SalaryStat from './views/SalaryStat.vue'
    import CheckinStat from './views/CheckinStat.vue'
    import ScreenCV from './views/ScreenCV.vue'
    import PostStat from './views/PostStat.vue'
    import ShowJobs from './views/ShowJobs.vue'
    import ShopDetail from './views/ShopDetail.vue'
    import Logout from './views/Logout.vue'
    export default {
        name: 'app',
        components: {
            Login,
            Register,
            Toolbar,
            PostJob,
            AddShop,
            ManageShop,
            InfoConfig,
            ConfirmCheckin,
            MonthlyBill,
            AddCompany,
            SalaryStat,
            CheckinStat,
            ScreenCV,
            PostStat,
            ShowJobs,
            ShopDetail,
            Logout
        }
    }
</script>

<style>
    #app {
        font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: black;
    }

    element.style {
        width: 200px;
        min-width: 200px;
        max-width: 200px;
        flex: 0 0 200px;
    }
</style>
