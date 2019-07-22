<template>
    <div class="content">
        <Table border :columns="columns7" :data="bill">
            <div slot="header" class="table-height" style="
        font-size: 20px;">月末账单</div>
            <div slot="footer" class="table-height">
                本月需支付总金额：{{}}

                <Button class="ivu-btn" @click="paybill(this.bill_id)" >确认支付</Button>
            </div>


        </Table>

        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
            </div>
        </div>
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
                        key: 'employee_name'
                    },
                    {
                        title: '上班开始时间',
                        key: 'begin_time',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',new Date(params.row.begin_time).toLocaleDateString()),
                                h('div',new Date(params.row.begin_time).toTimeString().substr(0,5))
                            ])
                        }
                    },
                    {
                        title: '上班结束时间',
                        key: 'end_time',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',new Date(params.row.end_time).toLocaleDateString()),
                                h('div',new Date(params.row.end_time).toTimeString().substr(0,5))
                            ])
                        }
                    },
                    {
                        title: '薪水',
                        key: 'payment'
                    },
                    {
                        title: '支付状态',
                        key: 'paid',
                        render: (h, params) => {
                            return  h('div', params.row.paid?"已支付":"拒绝支付")
                        }
                    }
                ],
                bill:[],
                bill_id:0,
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
            paybill( bill_id)
            {
                //pay bills
                var prefix="/billing"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix +"/merchant/billing/pay",
                    data:{
                        bill_id: bill_id,
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");

                        this.$Message.success('支付成功');
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })

            },
            mockTableData1(pagenum) {
                //get bills
                var prefix="/billing"
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
                        this.bill_id = response.data.data.content.bill_id;

                    }
                })
                    .catch(error => {
                        console.log(error.response)

                        if (error.response) {
                            if (error.response.data.status === 400 && error.response.data.message === "user does not belong to any company") {
                                this.$Message.error('请添加公司');
                            }
                        }
                        this.$Message.error('支付失败');
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
