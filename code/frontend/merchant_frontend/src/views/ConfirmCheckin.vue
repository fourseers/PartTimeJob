<template>
    <div class="content">
    <Table border :columns="columns7" :data="data6"></Table>

    </div>
</template>
<script>
    export default {
        data () {
            return {
                columns7: [
                    {
                        title: 'Name',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('Icon', {
                                    props: {
                                        type: 'person'
                                    }
                                }),
                                h('strong', params.row.name)
                            ]);
                        }
                    },
                    {
                        title: 'begin_time',
                        key: 'begin_time'
                    },
                    {
                        title: 'checkin_time',
                        key: 'checkin_time'
                    },
                    {
                        title: 'end_time',
                        key: 'end_time'
                    },
                    {
                        title: 'checkout_time',
                        key: 'checkout_time'
                    },
                    {
                        title: 'Rate',
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
                        title: 'Action',
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
        methods: {
            show (index) {
                this.$Modal.info({
                    title: 'User Info',
                    content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
                })
            },
            remove (index) {
                this.data6.splice(index, 1);
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

</style>
