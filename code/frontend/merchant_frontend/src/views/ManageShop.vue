<template>
    <div class="content">
        <Table border :columns="columns7" :data="shops"></Table>
    </div>
</template>
<script>
    export default {
        name: "ManageShop",
        data () {
            return {
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
                        key: 'province'
                    },

                    {
                        title: '城市',
                        key: 'city'
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
                            return h('ul', this.shops[params.index].industry.map(item => {
                                    return h('li',{style:{listStyle:"none"}}, item)

                                }
                            ))
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
                data6: [
                    {
                        shop_name:'豆汁汁',
                        province:'北京',
                        city: '北京',
                        address:'大裤衩楼底下',
                        industry:["饮料","零售"],
                        brand:'老豆汁'
                    },
                    {
                        shop_name: '一点点',
                        province:'上海',
                        city: '上海',
                        address:'东川路800号',
                        industry:['饮料',"零售"],
                        brand:'一点点'
                    }
                ],
                shops:[]
            }
        },
        created: function() {
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
                    if(response.data.status ===  200){

                        this.shops = response.data.data.shops;
                        console.log("success");
                    }
                })
                    .catch(error => {
                        if(error.response){
                            if(error.response.data.status === 400)
                            {
                                console.log(error.response);
                                this.$Message.error('暂无店铺');
                            }
                        }
                    })
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
