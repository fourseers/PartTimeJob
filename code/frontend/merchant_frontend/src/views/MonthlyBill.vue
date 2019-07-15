<template>
    <div class="content">
        <Table border :columns="columns7" :data="bill"></Table>

    </div>
</template>

<script>
    export default {
        name: "ShowJobs",
        data() {
            return {
                columns7: [
                    {
                        title: '店铺名称',
                        key: 'shop_name'
                    },
                    {
                        title: '岗位名称',
                        key: 'job_name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.job_name)
                            ]);
                        }
                    },
                    {
                        title: '员工名字',
                        key: 'need_amount'
                    },
                    {
                        title: '上班开始时间',
                        key: 'begin_apply_date',
                        render: (h, params) => {
                            var dateee = new Date(params.row.begin_apply_date).toJSON();
                            return h('div', new Date(new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '').substr(0, 16))
                        }
                    },
                    {
                        title: '上班结束时间',
                        key: 'end_apply_date',
                        render: (h, params) => {
                            var dateee = new Date(params.row.end_apply_date).toJSON();
                            return h('div', new Date(new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '').substr(0, 16))
                        }
                    },
                    {
                        title: '日薪',
                        key: 'salary'
                    },
                    {
                        title: '确认状态',
                        key: 'confirmed'
                    }
                ],
            }
        },
        watch:
            {},
        created: function () {

            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else
            {


            }

        }
    }
</script>

<style scoped>

    .content{
        margin-top:20px;
        margin-left: 100px;
        margin-right:50px;
        background-color: #fff;
    }
    .ivu-table {
        width: inherit;
        height: 100%;
        max-width: 100%;
        overflow: hidden;
        color: #515a6e;
        font-size: 19px;
        background-color: #fff;
        box-sizing: border-box;
    }
    .selector{
        margin :20px;
        width:200px;

    }
</style>
