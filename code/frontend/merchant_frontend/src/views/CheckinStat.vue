<template>


    <Content class="content">
        <Row>
            <Col span="3">
                <p>选择店铺</p>
            </Col>
            <Col span="3">
                <Select v-model="formValidate.shop" placeholder="选择店铺"  style="width:200px;  ">
                    <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id"> {{ item.shop_name }}</Option>
                    <Page :total="total_elements_shop" :current="pagenum2"  @on-change="changePage"></Page>
                </Select>
            </Col>
        </Row>
        <Row>
            <Col span="3">
                <p>开始年份月份</p>
            </Col>
            <Col span="4">
                <Date-picker type="month" placeholder="Select month"    v-model="startmonth"></Date-picker>
            </Col>
        </Row>
        <Row>
            <Col span="3">
                <p>结束年份月份</p>
            </Col>
            <Col span="4">
                <Date-picker type="month" placeholder="Select month"   v-model="endmonth" ></Date-picker>
            </Col>
        </Row>
        <Row>
            <Col span="3">
                <Button type="primary" @click="handleSubmit()">提交</Button>
            </Col>
        </Row>
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
        data() {
            return {
                total_elements_shop: 10,
                pagenum2: 1,

                shops: [],
                formValidate: {
                    shop: ""
                },
                startmonth: "",
                endmonth: "",
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
                data6: [],
                echarts1_option: {
                    color: ['#003366', '#006699', '#4cabce', '#e5323e'],
                    title: {
                        text: '员工打卡情况',
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
                    xAxis: {
                        show: true,  // 是否显示
                        type: 'category',
                        data: [ "某店铺"],

                        axisTick: {
                            alignWithLabel: true
                        },
                        axisLabel: {
                            interval: 0
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
                    ]

                }
            }
        },

//挂载前初始化echarts实例
        mounted: function () {
        },
        created: function () {
            if (!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            } else {
                //get shops
                getShops(0).then(res => {
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
        methods: {
            handleSubmit() {


                var prefix = "/arrangement"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix + "/merchant/work/status",
                    params: {
                        from_year: Number(this.startmonth.getFullYear()),
                        from_month: Number(this.startmonth.getMonth() + 1),
                        to_year: Number(this.endmonth.getFullYear()),
                        to_month: Number(this.endmonth.getMonth() + 1),
                        shop_id:this.formValidate.shop
                    }
                }).then(response => {
                    if (response.status === 200) {


                        console.log( response.data.data  )
                        var ob= {
                                name: ' ',
                                type: 'bar',
                                barGap: 0,
                                label: this.labelOption ,
                                data: [ ],
                                itemStyle: {
                                    normal: {
                                        label: {
                                            show: true, //开启显示
                                            position: 'top', //在上方显示
                                            formatter:'{c}%',
                                            textStyle: { //数值样式
                                                color: 'black',
                                                fontSize: 16
                                            }
                                        }
                                    }
                                },
                            }
                        ob.name = "签到率"
                        ob.data.push(response.data.data.attend_rate*100 )
                        this.echarts1_option.series.push(ob);
                        var ob2= {
                            name: ' ',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [ ],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示
                                        formatter:'{c}%',
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },
                        }
                        ob2.name = "迟到率"

                        ob2.data.push(response.data.data.late_rate*100 )
                        this.echarts1_option.series.push(ob2);
                        var ob3= {
                            name: ' ',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [ ],
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: true, //开启显示
                                        position: 'top', //在上方显示
                                        formatter:'{c}%',
                                        textStyle: { //数值样式
                                            color: 'black',
                                            fontSize: 16
                                        }
                                    }
                                }
                            },
                        }
                        ob3.name = "早退率"
                        ob3.data.push(response.data.data.leave_early_rate*100)
                        this.echarts1_option.series.push(ob3);


                        let myChart = echarts.init(document.getElementById('post_chart'))
                        // 绘制图表，this.echarts1_option是数据
                        myChart.setOption(this.echarts1_option)
                    }
                })
                    .catch(error => {

                    })
            },
            mockTableData1(pagenum) {
                //get shops
                getShops(pagenum).then(res => {
                        console.log(res.data)
                        this.shops = res.data.content
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
            }, changePage(index) {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index - 1);
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

    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
