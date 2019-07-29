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
                        title: '岗位名称',
                        key: 'job_name'
                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_time'
                    },
                    {
                        title: '工作结束时间',
                        key: 'end_time'
                    },
                    {
                        title: '请假理由',
                        key: 'score',
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

                                        }
                                    }
                                }, '同意请假'),
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
                                }, '拒绝请假')
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
//

            }
        },
        watch:
            {

            },
        methods: {
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
