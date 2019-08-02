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
                        title: '薪水',
                        key: 'salary'
                    },
                    {
                        title: '招聘开始时间',
                        key: 'begin_apply_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div', params.row.begin_apply_time.replace('T',' ').substr(0,19))
                            ])}
                    },
                    {
                        title: '招聘结束时间',
                        key: 'end_apply_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',params.row.end_apply_time.replace('T',' ').substr(0,19))
                            ])}
                    },
                    {
                        title: '工作开始日期',
                        key: 'begin_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',new Date(params.row.begin_date).toLocaleDateString())
                            ])}

                    },
                    {
                        title: '工作结束日期',
                        key: 'end_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',new Date(params.row.end_date).toLocaleDateString())
                            ])}

                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div',params.row.begin_time  )
                            ])}

                    },
                    {
                        title: '工作结束时间',
                        key: 'end_date',
                        render: (h, params) => {
                            return  h('div',[
                                h('div', params.row.end_time  )
                            ])}

                    },
                    {
                        title: '岗位介绍',
                        key: 'job_detail',
                        render: (h, params) => {
                            return  h('Tooltip',{
                                props:{
                                    content:params.row.job_detail,
                                    maxWidth:200
                        }},
                            [
                                h('div', params.row.job_detail.substr(0,20) )
                            ])}
                    },
                    {
                        title: '教育',
                        key: 'education',
                        render: (h, params) => {
                            return  h('div',this.education_convert(params.row.education)
                            )}

                    },
                    {
                        title: '招聘状态',
                        key: 'action',
                        render: (h,params) => {
                            return h('i-switch',{
                                props: {
                                    size: 'large',
                                    value: !params.row.manual_stop
                                },
                                style: {
                                    color: params.row.manual_stop?"#68B2FE":'#d63031',
                                    borderColor: params.row.manual_stop?'#d63031':"#68B2FE",
                                    backgroundColor: (params.row.manual_stop)?'#d63031':"#68B2FE",
                                },
                                on: {
                                    'on-change': (value) =>
                                    {
                                        params.row.manual_stop= !value;
                                        this.stopHire(params.row.job_id, params.row.manual_stop).then(res => {
                                                console.log(res)
                                                if(res.status===200 &&  params.row.manual_stop)
                                                {
                                                    this.$Message.success('停止招聘成功');
                                                }
                                                else if(res.status===200 &&  !params.row.manual_stop)
                                                {
                                                    this.$Message.success('继续招聘成功');
                                                }

                                            },
                                            error => {
                                                console.log(error.response);
                                                if (error.response) {
                                                    params.row.manual_stop= !value;

                                                    if (error.response.data.status === 400  &&  params.manual_stop) {
                                                        this.$Message.error('停止招聘失败');
                                                    }
                                                    else if(error.response.data.status === 400  &&!params.row.manual_stop)
                                                    {
                                                        this.$Message.success('继续招聘失败');
                                                    }
                                                }}
                                        )
                                    }
                                }
                            }, [
                                h('span', {
                                    slot: 'open'
                                }, '继续'),
                                h('span', {
                                    slot: 'close'
                                }, '停止')
                            ])
                        }
                    }
                ],
                jobs:[],
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

                            this.get_job_by_shop(0,val);
                        }
                    }
                }
            },
        created:function()
        {
            if(!this.$root.logged)
            {this.$Message.warning('请登录');
                this.$router.push({name: "login"})}
            else {
                //get jobs
                this.mockTableData1(0)
                //get shops
                this.mockTableData2(0)
            }
        },
        methods: {
            get_job_by_shop(index,val)
            {
                return new Promise((resolve, reject) => {
                    getJobsByShop(index, val).then(res => {
                            console.log(res)
                            this.jobs = res.data.content
                            this.total_elements = res.data.total_elements
                            this.total_pages = res.total_pages
                            resolve(res);
                        },
                        error => {
                            if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                                this.$Message.error('暂无岗位');
                                console.log(error)
                            }
                            console.log(error)
                            reject(error.response.data.status);
                        }
                    )
                })

            },
            stopHire(id,stop)
            {
                var prefix="arrangement";
                return new Promise((resolve, reject) => {
                    this.axios({
                        headers: {
                            'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                            'Content-type': 'application/json',
                            'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                            'x-access-token': this.$token.loadToken().access_token,
                        },
                        method:'put',
                        url: prefix +"/merchant/job/stop",
                        params:{
                            job_id:id,
                            stop:stop
                        }
                    }).then(({ status, data }) => {
                        if (status === 200) {
                            resolve(data);
                        } else {
                            reject( data);
                        }
                    }).catch(error => {
                        reject( error);
                    });
                });
            },
            mockTableData1 (pagenum) {
                var prefix = "/arrangement"

                //get jobs
                return new Promise((resolve, reject) => {
                    getJobs(pagenum).then(res => {
                            console.log(res)
                            this.jobs = res.data.content
                            this.total_pages = res.total_pages
                            this.total_elements = res.data.total_elements
                            resolve(res);
                        },
                        error => {
                            if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                                this.$Message.error('暂无岗位');
                                console.log(error)
                            }
                            reject(error.response.data.status);
                        }
                    )
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
            changePage (index) {
                if(this.shop_chosen === "")
                {this.mockTableData1(index-1);}
                else
                {this.get_job_by_shop(index-1,this.shop_chosen);}

            },
            changeselectPage(index)
            {
                this.mockTableData2(index-1);
            },
            showAll()
            {
                this.shop_chosen = ""
                this.mockTableData1(0);
            },
            education_convert(education)
            {
                if( education =="BELOW_SENIOR")
                {
                    return "初中毕业及以下"
                }else if( education =="TECHNICAL_JUNIOR")
                {

                    return "中专毕业"
                }
                else if(
                    education =="SENIOR_HIGH")
                {
                    return "高中毕业"
                }
                else if(
                    education =="JUNIOR_COLLEGE")
                {
                    return "大专毕业"
                }
                else if(
                    education =="BACHELOR")
                {
                    return "本科毕业"
                }
                else if(
                    education =="ABOVE_BACHELOR")
                {
                    return "研究生毕业及以上"
                }


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
