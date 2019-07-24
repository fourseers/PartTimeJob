<template>
    <Menu id="menu"  :theme="theme1"  active-name="1"   >
        <MenuItem name="1" to="/login"  v-if="!this.$root.logged" >
            <Icon type="md-log-in" />
            登录
        </MenuItem>

        <MenuItem name="18" to="/logout" v-if="this.$root.logged " >
            <Icon type="md-log-out" />
            登出
        </MenuItem>
        <MenuItem name="19" to="/banuser" v-if="this.$root.admin " >
            <Icon type="ios-paper" />
            管理用户
        </MenuItem>
        <MenuItem name="2" to="/register">
            <Icon type="md-log-in" />
            注册
        </MenuItem>
        <Submenu name="3">
            <template slot="title">
                <Icon type="ios-paper" />
                商户管理
            </template>
            <MenuItem name="4" to="/infoconfig">
                <Icon type="ios-paper" />
                查看信息
            </MenuItem>
            <MenuItem name="5" to="/addcompany">
                <Icon type="ios-paper" />
                注册公司
            </MenuItem>
        </Submenu>

        <Submenu name="7">
            <template slot="title">
                <Icon type="md-settings" />
                店铺管理
            </template>
            <MenuItem name="8" to="/addshop">
                <Icon type="md-create" />
                添加店铺
            </MenuItem>
            <MenuItem name="9" to="/manageshop">
                <Icon type="md-settings" />
                管理店铺
            </MenuItem>
        </Submenu>
        <MenuItem name="10" to="/postjob">
            <Icon type="md-create" />
            发布岗位
        </MenuItem>
        <MenuItem name="17" to="/showjobs">
            <Icon type="ios-people" />
            岗位管理
        </MenuItem>
        <MenuItem name="6" to="/screenCV">
            <Icon type="ios-people" />
            筛选简历
        </MenuItem>
        <MenuItem name="11" to="/confirmcheckin">
            <Icon type="md-clock" />
            确认打卡
        </MenuItem>
        <MenuItem name="12" to="/monthlybill">
            <Icon type="ios-people" />
            月末账单
        </MenuItem>
        <Submenu name="13">
            <template slot="title">
                <Icon type="md-stats" />
                统计信息
            </template>
            <MenuItem name="14" to="/poststat">
                <Icon type="ios-list-box" />
                岗位统计
            </MenuItem>
            <MenuItem name="15" to="/checkinstat">
                <Icon type="md-clock" />
                员工打卡
            </MenuItem>
            <MenuItem name="16" to="/salarystat">
                <Icon type="ios-stats" />
                工资支出
            </MenuItem>

        </Submenu>

    </Menu>
</template>
<script>

    export default {
        data () {
            return {
                theme1: 'light'
            }
        },
        methods:{

        },
        computed:{
        }

    }
</script>


<style scoped>
    .ivu-menu,.ivu-menu-vertical .ivu-menu-item， .ivu-menu-vertical .ivu-menu-submenu  {
        display: block;
        outline: 0;
        list-style: none;
        font-size: 14px;
        position: relative;
        z-index: 1;
        cursor: pointer;
        transition: all .2s ease-in-out;
        text-align: left;
        padding-left: 30px;
    }
</style>
