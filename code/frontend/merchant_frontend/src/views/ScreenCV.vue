<template>
    <div>
        <Layout>
            <Row >
            <Select v-model="post_chosen" style="width:200px;  margin:10px">
                <OptionGroup label="店铺1">
                    <Option v-for="item in postList1" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </OptionGroup>
                <OptionGroup label="店铺2">
                    <Option v-for="item in postList2" :value="item.value" :key="item.value">{{ item.label }}</Option>
                </OptionGroup>
            </Select>
                </Row>
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
        </Layout>

    </div>
</template>

<script>
    export default {
        data () {
            return {
                postList1: [
                    {
                        value: 'post 1',
                        label: 'post 1'
                    },
                    {
                        value:  'post 2',
                        label: 'post 2'
                    },
                    {
                        value:'post 3',
                        label:'post 3'
                    }
                ],
                postList2: [
                    {
                        value: 'post 4',
                        label: 'post 4'
                    }
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
        watch : {
            post_chosen:function(val) {
                console.log(val);
                this.CVList = this.getCVList(val);
            }
        },
        methods:
            {
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

            }

    }
</script>

<style scoped>
    .content{
        margin:10px 50px 50px;
        background-color: #fff;
        height:500px;
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
</style>
