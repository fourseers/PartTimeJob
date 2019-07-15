<template>
    <div class="content">
        <Table border :columns="columns7" :data="shops"></Table>
    </div>
</template>
<script>
    import { CodeToText } from 'element-china-area-data'
    export default {
        name: "ManageShop",
        data () {
            return {
                industry:[],
                columns7: [
                    {
                        title: '店铺名称',
                        key: 'shop_name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.shop_name)
                            ]);
                        }
                    },
                    {
                        title: '店铺照片',
                        key: 'shop_pic',
                        render: (h, params) => {
                            return h('img',{
                                domProps:{
                                    src:"/1.jpg"
                                },
                                style: {
                                    width: "100px",
                                    height: "100px",
                                }
                            })
                        }
                    },
                    {
                        title: '省份',
                        key: 'province',
                        render: (h, params) => {
                            return h('div',CodeToText[ params.row.province])
                        }
                    },

                    {
                        title: '城市',
                        key: 'city',
                        render: (h, params) => {
                            return h('div',CodeToText[ params.row.city])
                        }
                    },
                    {
                        title: '地址',
                        key: 'address'
                    },
                    {
                        title: '品牌',
                        key: 'brand'
                    },
                    {
                        title: '营业领域',
                        key: 'industry',
                        render: (h, params) => {
                            return h('div', this.industry[params.row.industry-1].industry_name)
                        }

                        // render: (h, params) => {
                        //     return h('ul', this.shops[params.index].industry.map(item => {
                        //             return h('li',{style:{listStyle:"none"}}, item)
                        //
                        //         }
                        //     ))
                        // }
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
                                            this.$router.push({ name: "shopdetail",params:this.shops[params.index]})
                                        }
                                    }
                                }, '修改信息'),
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
                                }, '删除店铺')
                            ]);
                        }
                    }
                ],
                shops:[]
            }
        },
        created: function() {

            if(!this.$root.logged) {
                this.$Message.warning('请登录');
            }
            else {
                var prefix = "/warehouse"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix + "/merchant/shop"
                }).then(response => {
                    console.log(response);
                    if (response.data.status === 200) {

                        this.shops = response.data.data.shops;
                        console.log("success");
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {
                                console.log(error.response);
                                this.$Message.error('暂无店铺');
                            }
                        }
                    })


                //get industry
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/industry",
                }).then(response => {
                    this.industry = response.data.data;
                    console.log( this.industry);
                    if(response.status ===  200)
                    {
                        console.log("success");
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })
            }
        },
        methods: {
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
</style>
