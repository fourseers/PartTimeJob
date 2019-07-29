<template>


    <Content class="content">
        <Select v-model="formValidate.shop" placeholder="选择店铺"  style="width:200px;  margin:10px">
            <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id"> {{ item.shop_name }}</Option>
            <Page :total="total_elements_shop" :current="pagenum2"  @on-change="changePage"></Page>
        </Select>

        <Table border :columns="columns7" :data="data6"></Table>

        <div class="chart">
            <div>
                <div style="width: 45%;height:500px ;border:1px solid rgb(180,180,180); margin:10px" id="post_chart"></div>
            </div>
        </div>
    </Content>
</template>

<script>

    import echarts from 'echarts'

    import {getShops} from '../util/getShops.js'
    export default {
        name: "CheckinStat",
        data () {
            return {
                total_elements_shop:10,
                pagenum2:1,

                shops:[],
                formValidate: {
                    shop:""
                },
                columns7: [
                    {
                        title: 'Name',
                        key: 'name',
                        render: (h, params) => {
                            return h('div', [
                                h('strong', params.row.name)
                            ]);
                        }
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
                    }],
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
                ],
                echarts1_option:{
                    color: ['#003366', '#006699', '#4cabce', '#e5323e'],
                    title: {
                        text: '员工打卡情况',
                        subtext: '某个店铺'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross'
                        }
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis:  {
                        show: true,  // 是否显示
                        type: 'category',
                        data: ["员工1","员工2","员工3","员工4"],

                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLabel: {
                            interval:0
                        }
                    },
                    yAxis: {
                        type: 'value',
                        axisLabel: {
                            formatter: '{value} %'
                        },
                        axisPointer: {
                            snap: true
                        }
                    },
                    series: [
                        {
                            name: '迟到率',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [2, 35, 50,22],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示

                                        formatter: '{b}\n{c}%',
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },

                        },
                        {
                            name: '早退率',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [2, 32, 31,22],

                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示,

                                        formatter: '{b}\n{c}%',
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },
                        },

                        {
                            name: '出勤率',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [2, 92, 70,22],

                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示,

                                        formatter: '{b}\n{c}%',
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },
                        },

                    ]

                }
            }
        },

//挂载前初始化echarts实例
        mounted: function () {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('post_chart'))
            // 绘制图表，this.echarts1_option是数据
            myChart.setOption(this.echarts1_option)
        },
        created: function () {
            if (!this.$root.logged) {
                this.$Message.warning('请登录');
            } else {

                //get shops
                getShops().then(res => {
                        console.log(res.data.content)
                        this.shops = res.data.content
                        this.total_elements_shop = res.data.total_elements
                        //   this.total_pages= res.total_pages
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

        },
        methods:{

            mockTableData1 (pagenum) {
                //get shops
                getShops(pagenum).then(res => {
                        console.log( res.data)
                        this.shops  = res.data.content
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
            },changePage (index) {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index-1);
            },
        }
    }
</script>

<style scoped>
    .content{
        margin:10px 50px 50px;
        background-color: #fff;
        height:500px;
    }
</style>
