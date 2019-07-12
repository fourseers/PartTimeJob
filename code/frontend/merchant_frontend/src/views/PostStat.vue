<template>
    <div class="chart">
        <div>
            <div style="width: 45%;height:500px ;border:1px solid rgb(180,180,180)" id="post_chart"></div>
        </div>
    </div>
</template>

<script>

    import echarts from 'echarts'

    export default {

        name: "PostStat",
        data() {
            return {
                echarts1_option:{
                    color: ['#003366', '#006699', '#4cabce', '#e5323e'],
                    title: {
                        text: '一天用电量分布',
                        subtext: '纯属虚构'
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
                    type: 'category',
                    boundaryGap: false,
                    data: ['00:00', '01:15', '02:30', '03:45', '05:00', '06:15', '07:30', '08:45', '10:00', '11:15', '12:30', '13:45', '15:00', '16:15', '17:30', '18:45', '20:00', '21:15', '22:30', '23:45']
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value} W'
                    },
                    axisPointer: {
                        snap: true
                    }
                },
                    series: [
                        {
                            name: 'Forest',
                            type: 'bar',
                            barGap: 0,
                            label: this.labelOption,
                            data: [320, 332, 301, 334, 390]
                        },
                        {
                            name: 'Steppe',
                            type: 'bar',
                            label: this.labelOption,
                            data: [220, 182, 191, 234, 290]
                        },
                        {
                            name: 'Desert',
                            type: 'bar',
                            label: this.labelOption,
                            data: [150, 232, 201, 154, 190]
                        },
                        {
                            name: 'Wetland',
                            type: 'bar',
                            label:  this.labelOption,
                            data: [98, 77, 101, 99, 40]
                        }
                    ]

                }
            }
        },
        created:function()
        {

            if(!this.$root.logged) {
                this.$Message.warning('请登录');
            }
            else{
                //get stat
            }
        },
//挂载前初始化echarts实例
        mounted: function () {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('post_chart'))
            // 绘制图表，this.echarts1_option是数据
            myChart.setOption(this.echarts1_option)
        }
    }

</script>


<style scoped>
    .chart{
        margin-top:50px;
        margin-left:200px;
    }
</style>

