<template>
    <div class="content">
        <Table border :columns="columns7" :data="bill">
            <div slot="header" class="table-height" style="
        font-size: 20px;">本月需支付总金额：{{this.sum}}
            </div>
            <div slot="footer" class="table-height">

                <Button class="ivu-btn" @click="paybill(bill_id)" >确认支付</Button>
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
                                h('div', params.row.begin_time )
                            ])
                        }
                    },
                    {
                        title: '上班结束时间',
                        key: 'end_time',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',params.row.end_time)
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
                            return  h('div', params.row.paid?"已支付给员工":"拒绝支付")
                        }
                    }
                ],
                bill:[],
                bill_id:0,
                sum: this.getsum(),
            }
        },
        watch:
            {},
        created: function () {

            if (!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            } else
            {

                //获取第一页账单
                this.mockTableData1(0)

            }

        },
        methods: {
            former_month()
            {

                var myDate = new Date();
                var newd=new Date;
                newd.setTime(myDate.getTime() - 8*3600*1000);

                newd.getFullYear(); //获取完整的年份(4位,1970-????)
                newd.getMonth(); //获取当前月份(0-11,0代表1月)
                var month = newd.getMonth() === 0?12:newd.getMonth()
                newd.setDate(0);
                var year=newd.getMonth() === 0?  newd.getFullYear()-1:  newd.getFullYear();
                return {
                    from:year.toString()+"-"+month.toString()+"-"+"1",
                    to:year.toString()+"-"+month.toString()+"-"+newd.getDate()
                }
            },
            former_month2()
            {

                var myDate = new Date();
                var newd=new Date;
                newd.setTime(myDate.getTime() - 8*3600*1000);

                newd.getFullYear(); //获取完整的年份(4位,1970-????)
                newd.getMonth(); //获取当前月份(0-11,0代表1月)
                var month = newd.getMonth() === 0?12:newd.getMonth()
                var year=newd.getMonth() === 0?  newd.getFullYear()-1:  newd.getFullYear();
                return {
                    year:year.toString(),
                    month:month.toString()
                }
            },
            getsum()
            {

                console.log( this.former_month())
                var prefix="/billing"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/billing/sum",
                    params:{
                        from:this.former_month().from,
                        to:this.former_month().to
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        this.sum = response.data.data.amount;

                    }
                })
                    .catch(error => {
                        console.log(error)

                    })
            },

            paybill( )
            {
                var prefix="/billing"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix +"/merchant/billing/monthly-pay",
                    data:{
                        year:this.former_month2().year,
                        month:this.former_month2().month
                    }
                }).then(response => {
                    console.log(response.data.data);
                    if(response.status ===  200)
                    {//response.data.data;

                        let dwSafari
                        dwSafari=window.open();
                        dwSafari.document.open();
                        let dataObj=response.data.data;
                        let html=  dataObj.replace(/[^\u0000-\u00FF]/g,function($0){return escape($0).replace(/(%u)(\w{4})/gi,"&#x$2;")});
                        dwSafari.document.write("<html><head><title></title><meta charset='utf-8'><body>"+dataObj+"</body></html>")
                        dwSafari.document.forms[0].submit()
                        dwSafari.document.close()
                        let config={
                            title: '请确认支付',
                            //   content: '<p>Content of dialog</p><p>Content of dialog</p>',
                            okText: '我已支付',
                            cancelText: '取消支付',
                            onOk: () => {
                              this.get_payment_status();
                            },
                            onCancel: () => {
                                this.$Message.info('Clicked cancel');
                            },
                            style:{

                            }
                        }
                        this.$Modal.confirm(config)
                    }
                })
                    .catch(error => {
                        console.log(error)

                    })

            },
            get_payment_status()
            {
                var prefix="/billing"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/billing/monthly-pay",
                    params:{
                        year:this.former_month2().year,
                        month:this.former_month2().month
                    }
                }).then(response => {
                    console.log(response.data.data);
                    if(response.status ===  200)
                    {
                        this.$Message.success('支付成功');
                       this.$router.push({name: "postjob"})
                    }
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
                        from:this.former_month().from,
                        to:this.former_month().to
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
                            else if(error.response.data.status === 400 && error.response.data.message === "no bills")
                            {
                                this.$Message.error('暂无账单');
                            }
                        }
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
