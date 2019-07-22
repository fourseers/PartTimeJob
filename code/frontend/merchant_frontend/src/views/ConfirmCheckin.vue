<template>
    <Content class="content">
        <Row>
            <Col span="4">
                <Select v-model="shop_chosen" placeholder="显示单个店铺的打卡">
                    <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id">{{ item.shop_name }}</Option>

                    <Page :total="total_elements_shop" :current="pagenum2"  @on-change="changeselectPage"></Page>
                </Select>
            </Col>
        </Row>
        <Row>
            <Table border :columns="columns7" :data="data6"></Table>

        </Row>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
            </div>
        </div>
    </Content>
</template>
<script>

    import {getShops} from '../util/getShops.js'
    export default {
        name: "ConfirmCheckin",
        data () {
            return {
                columns7: [
                    {
                        title: '员工名字',
                        key: 'employee_name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.employee_name)
                            ]);
                        }
                    },

                    {
                        title: '店铺名',
                        key: 'shop_name'
                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_time'
                    },
                    {
                        title: '签到时间',
                        key: 'checkin'
                    },
                    {
                        title: '工作结束时间',
                        key: 'end_time'
                    },
                    {
                        title: '签离时间',
                        key: 'checkout'
                    },
                    {
                        title: '打分',
                        key: 'score',
                        width: 190,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Rate', {
                                    props:{
                                        value:params.row.score
                                    },
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.row.score)
                                        }
                                    }
                                }, 'View')
                            ]);
                        }
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 150,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Button', {
                                    props: {
                                        type:'success',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#fff',
                                        marginRight: '5px',
                                        marginTop:'5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.pay(params.row.workid)

                                        }
                                    }
                                }, '发放工资'),
                                h('Button', {
                                    props: {
                                        type:'error',
                                        size: 'small'
                                    },
                                    style: {
                                        color: '#d63031',
                                        borderColor:'#d63031',
                                        backgroundColor:'#fff',
                                        marginRight: '5px',
                                        marginTop:'5px',
                                        marginBottom:'5px'

                                    },

                                    on: {
                                        click: () => {

                                        }
                                    }
                                }, '拒绝发放')
                            ]);
                        }
                    }
                ],
                data6: [
                ],

                shop_chosen:"",
                shops:[],
            }
        },
        created:function() {
            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else {
                //get checkin
               // this.mockTableData1(0)
                //get shops
                this.mockTableData2(0)

            }
        },
        watch:
            {
                shop_chosen:{
                    handler(val, oldVal){
                        // console.log(val);
                        // console.log(oldVal)

                        if (typeof(val) == "undefined"){
                            //翻页按钮
                        }
                        else {
                            this.mockTableData1 (0)
                        }
                    }
                }
            },
        methods: {
            changePage (index) {
                if(this.shop_chosen === "")
                {this.mockTableData1(index-1);}
                else
                {this.mockTableData1(index-1,this.shop_chosen);}

            },
            changeselectPage(index)
            {
                this.mockTableData2(index-1);
            },
            mockTableData1 (pagenum) {
                //get checkin
                var prefix="/arrangement"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/works",
                    params:{
                        shop_id: this.shop_chosen,
                        page_count:pagenum
                    }
                }).then(response => {
                    this.data6=response.data.data.content
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");
                    }
                })
                    .catch(error => {
                        console.log(error)
                        if (error.response) {
                            if (error.response.data.status === 400 && error.response.data.message === "work not exist") {
                                console.log(error.response);
                                this.$Message.error('暂无打卡');
                            }
                        }

                    })

            },
            mockTableData2 (pagenum) {
                //get shops
                getShops(pagenum).then(res => {
                        console.log(res.data)
                        this.shops = res.data.content
                        this.total_elements_shop=res.data.total_elements
                    },
                    error => {
                        if (error.response) {
                            if (error.response.data.status === 400 && error.response.data.message === "no shops") {
                                console.log(error.response);
                                this.$Message.error('暂无店铺');
                            } else if (error.response.data.status === 400 && error.response.data.message === "incorrect param") {
                                console.log(error.response);
                                this.$Message.error('参数错误');
                            }
                        }

                    }
                )
            }
            ,
            pay(workid)
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
                    url: prefix +"/merchant/billing/pay",
                    data:{
                        work_id: workid,
                        meta:"this is meta",
                        payment:11,
                        method:"this is method"
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");
                        this.$Message.success('发放成功');
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })

            },
            show (index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
                })
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

</style>
