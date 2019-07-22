<template>
    <div class="content">
        <div class="head">
            <Row >
                <Col span="4">
                    <div v-on:scroll="scrollFunction">
                    <Cascader :data="data" v-model="value1"  :load-data="loadData"  id="myCascader" >

                    </Cascader>
                    </div>
                </Col>
            </Row>
        </div>
        <Row>
            <div >
                <Carousel v-model="value1"   class="content">
                    <li style="list-style:none" v-for="item in CVList">
                        <CarouselItem >
                            <ul id="v-for-object" class="cv">
                                <li v-for="(value, name) in item">
                                    <p  class="cv-item">
                                        {{ name }}: {{ value }}
                                    </p>
                                    <br>
                                </li>
                            </ul>
                            <div class="buttons">
                                <Col span="12">
                                    <Button type="success"  @click="hire(item)">雇佣</Button>
                                </Col>
                                <Col span="12">
                                    <Button class="reject" @click="reject(item)">拒绝</Button>
                                </Col>
                            </div>
                        </CarouselItem>

                    </li>

                </Carousel>
            </div>
        </Row>
    </div>
</template>

<script>

    import {getShops} from '../util/getShops.js'

    import {getJobs, getJobsByShop} from '../util/getJobs.js'
    export default {

        name: "ScreeenCV",
        data () {
            return {
                Cascader:{},
                options:{},
                total_elements_shop:10,
                total_elements_job:10,
                formValidate:{
                    shop:""
                },
                shops: [],
                data: [
                ],
                CVList1:[{
                    name:'elizabeth',
                    age:98,
                    education:'bachlor',
                    gender:'female'
                },{
                    name:'smith',
                    age:38,
                    education:'bachlor',
                    gender:'male'
                },{
                    name:'batman',
                    age:42,
                    education:'bachlor',
                    gender:'male'
                }, {
                    name:'clara',
                    age:23,
                    education:'bachlor',
                    gender:'female'
                },
                ],
                CVList2:[{
                    name:'da vinci',
                    age:98,
                    education:'bachlor',
                    gender:'male'
                },{
                    name:'dali',
                    age:38,
                    education:'bachlor',
                    gender:'male'
                },{
                    name:'botechelli',
                    age:42,
                    education:'bachlor',
                    gender:'male'
                }, {
                    name:'rafell',
                    age:23,
                    education:'bachlor',
                    gender:'male'
                },
                ],
                CVList:[],
                post_chosen: '',
                value1: 0
            }
        },
        created:function()
        {

            if(!this.$root.logged) {
                this.$Message.warning('请登录');
            }
            else{
                //get shops
                getShops().then(res => {
                        this.shops = res.data.content
                        this.total_elements_shop=res.data.total_elements
                        for(var i=0;i<this.shops.length;i++) {
                            var item = {
                                value: this.shops[i].shop_id,
                                label: this.shops[i].shop_name,
                                children: [],
                                loading: false
                            }
                            this.data.push(item)
                        }
                        // window.addEventListener('scroll',function(e)
                        // {console.log(e)
                        // }, true);
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
                //GET CV

            }
        },
        mounted: {
        },
        watch : {
            post_chosen:function(val) {
                console.log(val);
                this.CVList = this.getCVList(val);
            }
        },
        methods:
            {scrollFunction()
                {
                  alert("scroll")
                },
                loadData (item, callback) {
                    item.loading = true;
                    //get jobs by shop
                    getJobsByShop(0,item.value).then(res => {
                        console.log( res.data.content.length)

                        for(var i=0;i<res.data.content.length;i++) {
                            console.log(res.data.content[i])
                            var child = {
                                value: res.data.content[i].job_id,
                                label: res.data.content[i].job_name,
                            }
                            item.children.push(child);
                        }

                            console.log(  item.children)
                            this.total_elements_job=res.data.total_elements

                            item.loading = false;
                            callback()
                        },
                        error => {
                            if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                                this.$Message.error('暂无岗位');
                                console.log(error)
                            }
                            console.log(error)

                            item.loading = false;
                            callback()
                        }
                    )

                },
                getCVList:function(val)
                {
                    if(val === "post 1")
                        return this.CVList1;
                    if(val === "post 2")
                        return this.CVList2;
                },
                hire:function(CV)
                {

                },
                reject:function(CV)
                {

                }
                ,changePage (index) {
                    // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                    this.mockTableData1(index-1);
                },

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
                },

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
    ul li{
        list-style:none;
        font-size :large;
    }
    .navBox .navList{
        list-style: none; /*去掉小圆点*/
        width: 100%;
        height: 100%;
    }
    .reject{
        color:#d63031;
        background-color:  #fff;
        border-color:#d63031;
    }
    .reject:hover
    {
        color:  #fff;
        background-color: #ff7675;
        border-color:#d63031;
    }
    .buttons{
        margin:200px;
    }
    .cv{
        padding-left:150px;
        padding-top:50px;
    }
    .cv-item{
        float:left;
    }
    .head{
        margin :20px;
    }
</style>
