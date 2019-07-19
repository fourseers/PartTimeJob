<template>
    <div class="content">
        <Table border :columns="columns7" :data="bill">
            <div slot="header" class="table-height">月末账单</div>
            <div style="margin: 10px;overflow: hidden">
                <div style="float: right;">
                    <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
                </div>
            </div>
            <div slot="footer" class="table-height">
                本月需支付总金额：{{}}

                <Button class="ivu-btn" @click="handleSubmit('formInline')" >确认支付</Button>
            </div>


        </Table>

    </div>
</template>

<script>
    export default {
        name: "ShowJobs",
        data() {
            return {
                total_elements:10,
                pagenum:1,
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
                        key: 'emplyee_name'
                    },
                    {
                        title: '上班开始时间',
                        key: 'begin_time',
                        render: (h, params) => {
                            var dateee = new Date(params.row.begin_time).toJSON();
                            return h('div', new Date(new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '').substr(0, 16))
                        }
                    },
                    {
                        title: '上班结束时间',
                        key: 'end_time',
                        render: (h, params) => {
                            var dateee = new Date(params.row.end_time).toJSON();
                            return h('div', new Date(new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '').substr(0, 16))
                        }
                    },
                    {
                        title: '薪水',
                        key: 'salary'
                    },
                    {
                        title: '支付状态',
                        key: 'paid'
                    }
                ],
                bill:[]
            }
        },
        watch:
            {},
        created: function () {

            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else
            {
                //获取第一页账单
                this.mockTableData1(0)
            }

        },
        methods: {
            mockTableData1(pagenum) {
                //get bills
                var prefix="/warehouse"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/billing",
                    params:{
                        page_count:pagenum,
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        this.bill=response.data.data.content;
                        console.log("success");
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })

            }, changePage(index) {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index - 1);
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
    .ivu-btn {
        padding-left:20px;
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
