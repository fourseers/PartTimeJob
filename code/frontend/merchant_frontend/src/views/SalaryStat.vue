<template>
    <div class="chart">
        <div>
            <div style="width: 45%;height:500px ;border:1px solid rgb(180,180,180)" id="salary_chart"></div>
        </div>
    </div>
</template>

<script>

    import echarts from 'echarts'

    export default {

        name: "SalaryStat",
        data() {
            return {
                echarts1_option:{
//主体代码块 backgroundColor: '#45515a',
                    //标题
                    title: {
                        text: '店铺支出',
                        subtext:'',
                        left: 'center',
                        top: 20,
                        textStyle: {
                            color: '#4b4b4b'//标题字体
                        }
                    },
                    //弹窗，响应鼠标指向，显示具体细节
                    tooltip : {
                        trigger: 'item',//以具体项目触发弹窗
                        /*
                        内容格式器，一共有abcd四个代号，例如在饼图中，{a}指系列，即流量来源，{b}指数据项目，如搜索引擎，{c}指数值，如
                        value，{d}百分比。{x}本身代表了相应字符，可以和其他字符拼凑，在弹窗中显示
                        */
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    //图例，选择要显示的项目
                    legend:{
                        orient:'vertical',
                        left:'left',
                        textStyle:{
                            color:'#c8c8d0'
                        },
                        data:['店铺A','店铺B','店铺C','店铺D','店铺E']  //注意要和数据的name相对应
                    },
                    //工具箱
                    toolbox:{
                        show:true,//显示工具箱
                        feature:{
                            dataView:{show:true}, //以文字形式显示数据
                            restore:{show:true},   //还原
                            //dataZoom:{show:true}, //区域缩放
                            saveAsImage:{show:true},  //保存图片
                            //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
                        }
                    },
                    //视觉映射组件，将数据映射到视觉元素上
                    visualMap: {
                        show: false,
                        min: 10,
                        max: 650,
                        dimension: 0, //选取数据的维度，如人数据：[身高，体重]，则1代表将体重进行映射，默认值为数组的最后一位
                        // seriesIndex: 4, //选取数据集合中的哪个数组，如{一班}，{二班}，默认选取data中的所有数据集
                        inRange: {
                            //选定了要映射的对象，用inRange详细写要渲染的具体细节，[x，y]中x指最小值对应的量（亮度，饱和度等），y指最大值对应的量，其余的按各自value线性渲染
                            // colorLightness: [0.5, 1]
                        }
                    },
//数据
                    series : [
                        {
                            name:'支出',
                            type:'pie',
                            radius : '55%',
                            center: ['50%', '50%'],
                            data:[
                                {value:335, name:'店铺A',  itemStyle: {
                                        color: '#f3a683'
                                    }},
                                {value:310, name:'店铺B',itemStyle: {
                                        color: '#ffeaa7'
                                    }},
                                {value:274, name:'店铺C',itemStyle: {
                                        color: '#f8a5c2'
                                    }},
                                {value:235, name:'店铺D',itemStyle: {
                                        color: '#63cdda'}},
                                {value:400, name:'店铺E',itemStyle: {
                                        color: '#ffb8b8'}}
                            ].sort(function (a, b) { return a.value - b.value; }),
                            roseType: 'radius',//角度和半径展现百分比，'area'只用半径展现
                            label: { //饼图图形的文本标签
                                normal: {  //下同，normal指在普通情况下样式，而非高亮时样式
                                    textStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    }
                                }
                            },
                            labelLine: {  //引导线样式
                                normal: {
                                    lineStyle: {
                                        color: 'rgba(255, 255, 255, 0.3)'
                                    },
                                    smooth: 0.5, //0-1，越大越平滑弯曲
                                    length: 10,  //从块到文字的第一段长
                                    length2: 20,  //拐弯到文字的段长
                                }
                            },
                            itemStyle: { //图例样式
                                normal: {
                                    shadowBlur: 5,//阴影模糊程度
                                    shadowColor: 'rgba(0, 0, 0, 0.2)'//阴影颜色，一般黑
                                }
                            },

                            animationType: 'scale', //初始动画效果，scale是缩放，expansion是展开
                            animationEasing: 'elasticOut', //初始动画缓动效果
                            animationDelay: function (idx) {  //数据更新动画时长，idx限定了每个数据块从无到有的速度
                                return Math.random() * 200;
                            }
                        }
                    ]

                }
            }
        },
        created:function()
        {

            if(!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            }
            else{
                //get stat
            }
        },
//挂载前初始化echarts实例
        mounted: function () {
            // 基于准备好的dom，初始化echarts实例
            let myChart = echarts.init(document.getElementById('salary_chart'))
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

