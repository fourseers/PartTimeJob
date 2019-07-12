<template>
    <div class="content">
        <div class="selector">
            <Select v-model="shops" placeholder="选择店铺">
                <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id">{{ item.shop_name }}</Option>
            </Select>

        </div>
        <Table border :columns="columns7" :data="jobs"></Table>

    </div>
</template>

<script>
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
                            var dateee = new Date(params.row.begin_apply_date).toJSON();
                            return  h('div',new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, ''))
                        }
                    },
                    {
                        title: '招聘结束时间',
                        key: 'end_apply_date',
                        render: (h, params) => {
                            var dateee = new Date(params.row.end_apply_date).toJSON();
                            return  h('div',new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, ''))
                        }
                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_date',
                        render: (h, params) => {
                            var dateee = new Date(params.row.begin_date).toJSON();
                            return  h('div',new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, ''))
                        }
                    },
                    {
                        title: '工作结束时间',
                        key: 'end_date',
                        render: (h, params) => {
                            var dateee = new Date(params.row.end_date).toJSON();
                            return  h('div',new Date(+new Date(dateee) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, ''))
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
                jobs:[],
                shops:[]
            }
        },
        created:function(){
            {
                if(!this.$root.logged)
                {this.$Message.warning('请登录');}
                else {
                    var prefix = "/arrangement"
                    //测试用的url
                    this.axios({
                        headers: {
                            'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                            'Content-type': 'application/json',
                            'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                            'x-access-token': this.$token.loadToken().access_token,
                        },
                        method: 'get',
                        url: prefix + "/merchant/job"
                    }).then(response => {
                        console.log(response.data.data.jobs);
                        if (response.data.status === 200) {
                            this.jobs = response.data.data.jobs
                        }
                    })
                        .catch(error => {
                            JSON.stringify(error);
                            console.log(error)
                        })
                }

            }
            {
                var prefix2 = "/warehouse"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix2 + "/merchant/shop"
                }).then(response => {
                    console.log(response);
                    console.log(response.data.data.shops);
                    if(response.data.status ===  200){

                        this.shops = response.data.data.shops;
                        console.log(   this.shops );
                    }
                })
                    .catch(error => {
                        if(error.response){
                            if(error.response.data.status === 400)
                            {
                                console.log(error.response);
                            }
                        }
                    })
            }

        }
    }
</script>

<style scoped>

    .content{
        margin-top:100px;
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
