<template>

    <Content class="content">
    <Button class="ivu-btn" @click="logout()" >登出</Button>
    </Content>
</template>

<script>
    export default {
        name: "Logout",
        methods: {
            logout()
            {
                this.$token.deleteToken();
                console.log(this.$token.loadToken());

                this.$root.logged = false;
                this.$router.push({ name: "login"})
                this.$Message.success('登出成功');
            }

        }
    }
</script>

<style scoped>

    .content{
        padding:100px;
        background-color: #fff;
    }
</style>
