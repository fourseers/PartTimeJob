<template>
    <Menu id="menu" mode="horizontal" :theme="theme1" active-name="1"  >
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
                店铺管理
            </template>
            <MenuItem name="3" to="/addshop">
                <Icon type="ios-people" />
                添加店铺
            </MenuItem>
            <MenuItem name="3" to="/manageshop">
                <Icon type="ios-people" />
                管理店铺
            </MenuItem>
        </Submenu>
        <Submenu name="4">
            <template slot="title">
                <Icon type="ios-stats" />
                岗位管理
            </template>
            <MenuItem name="4" to="/postjob">
                <Icon type="ios-people" />
                发布岗位
            </MenuItem>
        </Submenu>
        <MenuItem name="2" to="/infoconfig">
            <Icon type="ios-people" />
            商户信息
        </MenuItem>

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
