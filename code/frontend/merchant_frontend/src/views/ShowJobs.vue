<template>
    <div class="content">
        <div class="head">
            <Row >
                <Col span="4">
                <Select v-model="shop_chosen" placeholder="显示单个店铺全部岗位">
                    <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id">{{ item.shop_name }}</Option>

                    <Page :total="total_elements_shop" :current="pagenum2"  @on-change="changeselectPage"></Page>
                </Select>
                </Col>

                <Col span="4">
                <Button class="ivu-btn" @click="showAll" >显示全部岗位</Button>
                </Col>
            </Row>

        </div>
        <Row>
        <Table border :columns="columns7" :data="jobs"></Table>
        </Row>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
            </div>
        </div>
    </div>
</template>

<script>

    import {getJobs, getJobsByShop} from '../util/getJobs.js'
    import {getShops} from '../util/getShops.js'
    export default {
        name: "ShowJobs",
        data () {
            return {
                columns7: [
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
                        title: '招聘数量',
                        key: 'need_amount'
                    },
                    {
                        title: '性别要求',
                        key: 'need_gender',
                        render: (h, params) => {
                            let gender;
                            if(params.row.need_gender === 2 )
                            { gender = "男女皆可"}
                            else if (params.row.need_gender === 1 )
                            { gender = "女"}
                            else if (params.row.need_gender === 0 )
                            { gender = "男"}
                            return h('div', gender);
                        }

                    },
                    {
                        title: '日薪',
                        key: 'salary'
                    },
                    {
                        title: '招聘开始时间',
                        key: 'begin_apply_date',
                        render: (h, params) => {
                            return  h('div',new Date(params.row.begin_apply_date).toLocaleString())
                        }
                    },
                    {
                        title: '招聘结束时间',
                        key: 'end_apply_date',
                        render: (h, params) => {
                            return  h('div',new Date(params.row.end_apply_date).toLocaleString())
                        }
                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_date',
                        render: (h, params) => {
                            return  h('div',new Date(params.row.begin_date).toLocaleString())
                        }
                    },
                    {
                        title: '工作结束时间',
                        key: 'end_date',
                        render: (h, params) => {
                            return  h('div',new Date(params.row.end_date).toLocaleString())
                        }
                    },
                    {
                        title: '岗位介绍',
                        key: 'job_detail'
                    },
                    {
                        title: '教育',
                        key: 'education',
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
                                            //删除店铺
                                        }
                                    }
                                }, '停止招聘')
                            ]);
                        }
                    }
                ],
                jobs:this.mockTableData1(0),
                shop_chosen:"",
                shops:[],
                pagenum:1,
                pagenum2:1,
                total_elements: 10,
                total_pages:2,
                total_elements_shop:0,
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
                            //get jobs by shop
                            getJobsByShop(0,val).then(res => {
                                    console.log(res)
                                    this.jobs = res.data.content
                                    this.total_elements=res.data.total_elements
                                    this.total_pages= res.total_pages
                                },
                                error => {
                                    if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                                        this.$Message.error('暂无岗位');
                                        console.log(error)
                                    }
                                    console.log(error)
                                }
                            )


                        }
                    }
                }
            },
        created:function()
        {
            if(!this.$root.logged)
            {this.$Message.warning('请登录');}
            else {
                //get jobs
                getJobs(0).then(res => {
                        console.log(res)
                        this.jobs = res.data.content
                        this.total_elements=res.data.total_elements
                        this.total_pages= res.total_pages
                    },
                    error => {
                        if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                            this.$Message.error('暂无岗位');
                            console.log(error)
                        }
                        console.log(error)
                    }
                )


                //get shops
                getShops().then(res => {
                        console.log(res)
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
                ) }

        },
        methods: {
            mockTableData1 (pagenum,shop_id) {
                var prefix = "/arrangement"

                //get jobs
                getJobs(pagenum).then(res => {
                        console.log(res)
                        this.jobs = res.data.content
                    },
                    error => {
                        if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                            this.$Message.error('暂无岗位');
                            console.log(error)
                        }
                        console.log(error)
                    }
                )
            },
            mockTableData2 (pagenum) {
                //get shops
                getShops(pagenum).then(res => {
                        console.log(res.data)
                        this.shops = res.data.content
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
            showAll()
            {
                this.shop_chosen = ""
                this.mockTableData1(0);
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
    .head{
        margin :20px;
    }

    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
