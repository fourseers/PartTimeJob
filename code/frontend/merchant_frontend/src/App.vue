<template>
  <div id="app">
      <Col span="4">
      <Toolbar/>
      </Col>
      <Col span="20">
        <div id="core-view">
          <v-fade-transition mode="out-in">
            <router-view />
          </v-fade-transition>
        </div>
      </Col>
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
      PostStat
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
