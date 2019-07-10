<template>
    <Button class="ivu-btn" @click="logout()" >登出</Button>
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
            }

        }
    }
</script>

<style scoped>

</style>
