<template>
    <div></div>
</template>

<script>
    export default {
        name: "MonthlyBill",

        created:function(){
            if(!this.$root.logged)this.$Message.warning('请登录');
        }
    }
</script>

<style scoped>

</style>
