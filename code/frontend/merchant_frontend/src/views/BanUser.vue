<template>
    <Content class="content">
        <Table border :columns="columns7" :data="users"></Table>
    </Content>
</template>

<script>
    export default {
        name: "BanUser",
        data () {
            return {
                columns7: [
                    {
                        title: '用户名称',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.name)
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
                                    value: !params.row.isbanned
                                },
                                style: {
                                    color: params.row.isbanned?"#68B2FE":'#d63031',
                                    borderColor: params.row.isbanned?'#d63031':"#68B2FE",
                                    backgroundColor: (params.row.isbanned)?'#d63031':"#68B2FE",
                                },
                                on: {
                                    'on-change': (value) =>
                                    {
                                        console.log(value);
                                        params.row.isbanned = !value;
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
                users:[
                    {
                        name:"www",
                        isbanned:false
                    }
                ]
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
