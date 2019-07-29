<template>
    <div class="content">
        <Table border :columns="columns7" :data="shops"></Table>
        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="total_elements" :current="pagenum"  @on-change="changePage"></Page>
            </div>
        </div>
    </div>
</template>
<script>
    import { CodeToText } from 'element-china-area-data'

    import {getShops} from '../util/getShops.js'
    import {getIndustry} from '../util/getIndustry.js'
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
                                }, '修改信息')
                            ]);
                        }
                    }
                ],
                shops:[],
                pagenum:1,
                total_elements: 10,
                total_pages:2,
            }
        },
      created: function() {

            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else {

                //获取第一页表格
                this.mockTableData1 (0);
                //get industry
                getIndustry().then(res => {
                        console.log(res.data)
                        this.industry = res.data
                    },
                    error => {
                        console.log(error)
                    })
            }
        },

        methods: {
            mockTableData1 (pagenum) {
                return new Promise((resolve, reject) => {
                    getShops(pagenum).then(res => {
                            console.log(res.data)
                            this.shops = res.data.content
                            resolve(res);
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
                                reject(error.response.data.status);
                            }
                        }
                    )
                })
            },changePage (index) {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index-1);
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
