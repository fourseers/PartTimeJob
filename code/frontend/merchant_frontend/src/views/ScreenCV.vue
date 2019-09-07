<template>
    <div class="content">
        <div class="head">
            <Row >
                <Col span="4">
                    <div >
                        <Cascader :data="data" v-model="value1"  :load-data="loadData"  id="myCascader" >

                        </Cascader>
                    </div>
                </Col>
            </Row>
        </div>
        <Row>
            <div >
                <Carousel   class="content" v-model="carousel_index">
                    <li style="list-style:none" v-for="item in CVList">
                        <CarouselItem >

                            <div class="cv">
                                <Row>
                                    <p  class="cv-item2">
                                        工作开始日期： {{item.applied_begin_date}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        工作结束日期：{{item.applied_end_date}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        名称： {{item.cv.name}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        性别： {{item.cv.gender?"女":"男"}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        教育： {{education_convert(item.cv.education)}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        身高： {{item.cv.height}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        体重： {{item.cv.weight}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        身份证号： {{item.cv.identity}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        手机号： {{item.cv.phone}}
                                    </p>
                                </Row>
                                <Row>
                                    <p  class="cv-item2">
                                        自我陈述： {{item.cv.statement}}
                                    </p>
                                </Row>
                            </div>
                            <div class="buttons">
                                <Col span="12">
                                    <Button type="success"  @click="hire(item.application_id)">雇佣</Button>
                                </Col>
                                <Col span="12">
                                    <Button class="reject" @click="reject(item.application_id)">拒绝</Button>
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
                CVList:[],
                value1:  [],
                carousel_index:0,
                page_index:0,
                total_pages:0,
            }
        },
        created:function()
        {

            if(!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            }
            else{
                //get shops
                getShops(0).then(res => {
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
                    },
                    error => {
                        console.log(error.response)
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
        watch : {
            carousel_index:function(val) {
                console.log(val);
                if(val+1 ===  this.CVList.length && this.total_pages >this.page_index )
                {
                    console.log("next page");
                    this.getCV(this.page_index) .then(
                        res=> {
                            this.total_pages=  res.data.total_pages
                            this.CVList = this.CVList.concat(res.data.content);
                        },
                        error => {

                            console.log(error.response)
                        })

                    this.page_index++;
                }
            },
            value1:function(val) {
                this.getCV(0) .then(
                    res=> {
                        this.total_pages=  res.data.total_pages
                        this.CVList = res.data.content;
                        this.page_index+=1;
                    },
                    error => {

                        console.log(error.response)
                    })
            }
        },
        methods:
            { getCV(index)
                {
                    var prefix="/arrangement"
                    return new Promise((resolve, reject) => {
                        this.axios({
                            headers: {
                                'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                                'Content-type': 'application/json',
                                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                                'x-access-token': this.$token.loadToken().access_token,
                            },
                            method: 'get',
                            url: prefix + "/merchant/applications",
                            params: {
                                job_id: this.value1[1],
                                page_count: index
                            }
                        }).then(({ status, data }) => {
                            console.log(data)
                            resolve(data);
                        })
                            .catch(error => {
                                reject(error );
                            })
                    })

                },
                loadData (item, callback) {
                    item.loading = true;

                    //get jobs by shop
                    getJobsByShop(0,item.value).then(res => {

                            for(var i=0;i<res.data.content.length;i++) {
                                var child = {
                                    value: res.data.content[i].job_id,
                                    label: res.data.content[i].job_name,
                                }
                                item.children.push(child);
                            }

                            this.total_elements_job=res.data.total_elements

                            item.loading = false;
                            callback()
                        },
                        error => {
                            if (error.response.data.status === 400 && error.response.data.message === "job not exist") {
                                this.$Message.error('暂无岗位');
                            }
                            console.log(error)

                            item.loading = false;
                            callback()
                        }
                    )

                },
                hire:function(application_id)
                {
                    var prefix="/arrangement"
                    return new Promise((resolve, reject) => {
                        this.axios({
                            headers: {
                                'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                                'Content-type': 'application/json',
                                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                                'x-access-token': this.$token.loadToken().access_token,
                            },
                            method: 'post',
                            url: prefix + "/merchant/application/accept",
                            params: {
                                application_id: application_id
                            }
                        }).then(response => {
                            console.log(response);
                            if (response.status === 200) {
                                this.$Message.success('雇佣成功');
                                resolve(response.data.data.content);
                            }
                        })
                            .catch(error => {
                                if (error.response.data.status === 400 && error.response.data.message === "application already processed") {
                                    this.$Message.error('已经处理过这个申请');
                                }
                                reject(error );
                            })
                    })
                },
                reject:function(application_id)
                {
                    var prefix="/arrangement"
                    return new Promise((resolve, reject) => {
                        this.axios({
                            headers: {
                                'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                                'Content-type': 'application/json',
                                'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                                'x-access-token': this.$token.loadToken().access_token,
                            },
                            method: 'post',
                            url: prefix + "/merchant/application/reject",
                            params: {
                                application_id: application_id
                            }
                        }).then(response => {
                            console.log(response);
                            if (response.status === 200) {
                                this.$Message.error('拒绝雇佣');
                                resolve(response.data.data.content);
                            }
                        })
                            .catch(error => {
                                if (error.response.data.status === 400 && error.response.data.message === "application already processed") {
                                    this.$Message.error('已经处理过这个申请');
                                }
                                reject(error );
                            })
                    })
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
                education_convert(education)
                {
                    if( education =="BELOW_SENIOR")
                    {
                        return "初中毕业及以下"
                    }else if( education =="TECHNICAL_JUNIOR")
                    {

                        return "中专毕业"
                    }
                    else if(
                        education =="SENIOR_HIGH")
                    {
                        return "高中毕业"
                    }
                    else if(
                        education =="JUNIOR_COLLEGE")
                    {
                        return "大专毕业"
                    }
                    else if(
                        education =="BACHELOR")
                    {
                        return "本科毕业"
                    }
                    else if(
                        education =="ABOVE_BACHELOR")
                    {
                        return "研究生毕业及以上"
                    }


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
        margin-bottom:100px;
    }
    .cv{
        padding-left:150px;
        padding-top:50px;
    }
    .cv-item{
        float:left;
    }
    .cv-item2{
        float:left;
        font-size: large;
    }
    .head{
        margin :20px;
    }
</style>
