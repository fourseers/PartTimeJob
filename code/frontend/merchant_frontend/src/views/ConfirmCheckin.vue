<template>
    <Content class="content">
        <Table border :columns="columns7" :data="data6"></Table>

    </Content>
</template>
<script>
    export default {
        name: "ConfirmCheckin",
        data () {
            return {
                columns7: [
                    {
                        title: '员工名字',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.name)
                            ]);
                        }
                    },
                    {
                        title: '工作开始时间',
                        key: 'begin_time'
                    },
                    {
                        title: '签到时间',
                        key: 'checkin_time'
                    },
                    {
                        title: '工作结束时间',
                        key: 'end_time'
                    },
                    {
                        title: '签离时间',
                        key: 'checkout_time'
                    },
                    {
                        title: '打分',
                        key: 'action',
                        width: 190,
                        align: 'center',
                        render: (h, params) => {
                            return h('div', [
                                h('Rate', {
                                    style: {
                                        marginRight: '5px'
                                    },
                                    on: {
                                        click: () => {
                                            this.show(params.index)
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
                    {
                        name: 'John Brown',
                        begin_time:"2020-2-2",
                        checkin_time:"2026-2-2",
                        end_time:"2020-2-3",
                        checkout_time:"2020-2-2"
                    },
                    {
                        name: 'John Brown',
                        begin_time:"2020-2-2",
                        checkin_time:"2026-2-2",
                        end_time:"2020-2-3",
                        checkout_time:"2020-2-2"
                    }
                ]
            }
        },
        created:function() {
            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else {
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
        margin-top:20px;
        margin-left: 100px;
        margin-right:50px;
        background-color: #fff;
    }

</style>
