<template>
    <div>
    <p>打卡信息列表、平均打卡次数统计图、打卡信息列表、平均打卡次数统计图、准时率统计图</p>
        <p>准时率统计图示</p>
        <p>PNG导出</p>
    </div>
</template>

<script>
    export default {
        name: "CheckinStat",

        created:function(){
            if(!this.$root.logged)this.$Message.warning('请登录');
        },
    }
</script>

<style scoped>

</style>
