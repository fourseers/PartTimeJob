<template>
    <Menu id="menu"  :theme="theme1"  active-name="1"   >
        <MenuItem name="1" to="/login">
            <Icon type="ios-paper"/>
            登录
        </MenuItem>
        <MenuItem name="2" to="/register">
            <Icon type="ios-people" />
            注册
        </MenuItem>
        <Submenu name="3">
            <template slot="title">
                <Icon type="ios-stats" />
                商户信息管理
            </template>
        <MenuItem name="4" to="/infoconfig">
            <Icon type="ios-people" />
            商户信息修改
        </MenuItem>
        <MenuItem name="5" to="/addcompany">
            <Icon type="ios-people" />
            注册公司
        </MenuItem>
        </Submenu>
        <MenuItem name="6" to="/screenCV">
            <Icon type="ios-people" />
            筛选简历
        </MenuItem>
        <Submenu name="7">
            <template slot="title">
                <Icon type="ios-stats" />
                店铺管理
            </template>
            <MenuItem name="8" to="/addshop">
                <Icon type="ios-people" />
                添加店铺
            </MenuItem>
            <MenuItem name="9" to="/manageshop">
                <Icon type="ios-people" />
                管理店铺
            </MenuItem>
        </Submenu>
            <MenuItem name="10" to="/postjob">
                <Icon type="ios-people" />
                发布岗位
            </MenuItem>
        <MenuItem name="11" to="/confirmcheckin">
            <Icon type="ios-people" />
            确认打卡
        </MenuItem>
        <MenuItem name="12" to="/monthlybill">
            <Icon type="ios-people" />
            结算月末账单
        </MenuItem>
        <Submenu name="13">
            <template slot="title">
                <Icon type="ios-stats" />
                统计信息
            </template>
            <MenuItem name="14" to="/poststat">
                <Icon type="ios-people" />
                岗位统计
            </MenuItem>
            <MenuItem name="15" to="/checkinstat">
                <Icon type="ios-people" />
                员工打卡
            </MenuItem>
            <MenuItem name="16" to="/salarystat">
            <Icon type="ios-people" />
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
        }

    }
</script>
