<template>
    <Content class="content">
        <Table border :columns="columns7" :data="users"></Table>

        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
            </div>
        </div>
    </Content>
</template>

<script>
    export default {
        name: "BanUser",
        data () {
            return {
                columns7: [
                    {
                        title: '公司名称',
                        key: 'company_name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.company_name)
                            ]);
                        }
                    },
                    {
                        title: '用户名称',
                        key: 'username',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.username)
                            ]);
                        }
                    },
                    {
                        title: '用户状态',
                        key: 'isbanned',
                        render: (h,params) => {
                            return h('i-switch',{
                                props: {
                                    size: 'large',
                                    value: !params.row.banned
                                },
                                style: {
                                    color: params.row.banned?"#68B2FE":'#d63031',
                                    borderColor: params.row.banned?'#d63031':"#68B2FE",
                                    backgroundColor: (params.row.banned)?'#d63031':"#68B2FE",
                                },
                                on: {
                                    'on-change': (value) =>
                                    {
                                        params.row.banned = !value;
                                        this.banUser(params.row.user_id, params.row.banned).then(res => {
                                                if(res.status===200 &&  params.row.banned)
                                                {
                                                    this.$Message.success('禁用成功');
                                                }
                                                else if(res.status===200 &&  !params.row.banned)
                                                {
                                                    this.$Message.success('解禁成功');
                                                }

                                            },
                                            error => {
                                                console.log(error.response);
                                                if (error.response) {
                                                    params.row.banned = !value;

                                                    if (error.response.data.status === 400  &&  params.row.banned) {
                                                        this.$Message.error('禁用失败');
                                                    }
                                                    else if(error.response.data.status === 400  &&!params.row.banned)
                                                    {
                                                        this.$Message.success('解禁失败');
                                                    }
                                                }}
                                        )
                                    }
                                }
                            }, [
                                h('span', {
                                    slot: 'open'
                                }, '启用'),
                                h('span', {
                                    slot: 'close'
                                }, '禁用')
                            ])
                        }
                    }
                ],
                users: [],
                pagenum:1,
                total_elements: 10,
                total_pages:2,
            }
        },
        created: function() {

            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else {
                this.mockTableData1 (0)
            }

        },
        methods:{
            getUserById(uid)
            {
                var prefix="warehouse";
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/admin/merchant-user",
                    params:{
                        user_id:uid
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.data.status === 200 )
                    {

                        this.users = response.data.data.content
                        this.total_elements=response.data.data.total_elements
                        console.log(    this.total_elements)
                        this.total_pages=response.data.data.total_elements
                    }
                }).catch(error=> {

                })
            },
            mockTableData1 (pagenum) {

                var prefix="warehouse";
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/admin/merchant-users",
                    params:{page_count:pagenum
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.data.status === 200 )
                    {
                        this.users = response.data.data.content
                        this.total_elements=response.data.data.total_elements
                        this.total_pages=response.data.data.total_elements
                    }
                }).catch(error=> {
                })
            },
            changePage(index)
            {

                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index-1);
            },
            banUser(userid,banned)
            {
                var prefix="warehouse";
                return new Promise((resolve, reject) => {
                    this.axios({
                        headers: {
                            'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                            'Content-type': 'application/json',
                            'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                            'x-access-token': this.$token.loadToken().access_token,
                        },
                        method:'put',
                        url: prefix +"/admin/merchant-user/ban",
                        params:{
                            user_id:userid,
                            ban:banned
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
