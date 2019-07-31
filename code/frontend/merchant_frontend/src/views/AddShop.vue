<template>

    <Layout >
        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <Col span="10">
                    <div>
                        <div class="amap-page-container">
                            <el-amap vid="amapDemo" :zoom="zoom" :center="center" class="amap-demo" :plugin="plugin">
                                <el-amap-info-window  :position="mywindow.position" :content="mywindow.content" :visible="mywindow.visible" :events="mywindow.events"></el-amap-info-window>
                                <el-amap-marker :position="marker.position" :events="marker.events" :visible="marker.visible" :draggable="marker.draggable"></el-amap-marker>
                                <el-amap-circle :center="circle.center" :radius="circle.radius" :fillOpacity="circle.fillOpacity" :events="circle.events" :strokeColor="circle.strokeColor" :strokeStyle="circle.strokeStyle" :fillColor="circle.fillColor"></el-amap-circle>

                            </el-amap>
                        </div>
                    </div>
                    <div>
                        <Upload
                                multiple
                                type="drag"
                                action="//jsonplaceholder.typicode.com/posts/">
                            <div style="padding-left:20px;  height: 70px;">
                                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                                <p>点击或者拖拽上传图片</p>
                            </div>
                        </Upload>
                    </div>
                </Col>
                <Col span="12">
                    <FormItem label="名称" prop="shop_name">
                        <Input v-model="formValidate.shop_name" placeholder="店铺名称"></Input>
                    </FormItem>
                    <FormItem label="地址" prop="address"  >
                        <AutoComplete
                                v-model="formValidate.address"
                                :data="addressCandidate"
                                placeholder="店铺地址"
                                @on-select="locate"
                        >
                            <Option v-for="option in addressCandidate" :value="option.name" :key="option.name">
                                <span class="demo-auto-complete-title">{{ option.name }}</span>
                            </Option>

                        </AutoComplete>
                    </FormItem>
                    <FormItem label="品牌" prop="brand">
                        <Input v-model="formValidate.brand" placeholder="品牌"></Input>
                    </FormItem>

                    <FormItem class="ivu-form-item ivu-form-item-required" label="省市地区" prop="province_city">
                        <el-cascader
                                size="small"
                                :options="options"
                                v-model="formValidate.province_city"
                        >
                        </el-cascader>
                    </FormItem>
                    <FormItem label="营业领域" prop="industry">
                        <CheckboxGroup v-model="formValidate.industry" v-for="item in industry">
                            <Checkbox :label="item.industry_id"  >{{item.industry_name}}</Checkbox>
                        </CheckboxGroup>
                    </FormItem>
                    <FormItem label="店铺介绍" prop="shop_intro">
                        <Input v-model="formValidate.shop_intro" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="这里写店铺介绍"></Input>
                    </FormItem>

                    <FormItem>
                        <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
                        <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
                    </FormItem>
                </Col>
            </Form>

        </Content>

    </Layout>
</template>
<script>

    import {getIndustry} from '../util/getIndustry.js'
    import AMap from 'vue-amap';

    var _ = require('lodash');
    AMap.initAMapApiLoader({
        key: '54c30f89c2a3166f4d0dd9eeab9b5196',
        plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor']
    })
    import { provinceAndCityData } from 'element-china-area-data';
    export default {
        name: "AddShop",
        data () {
            const NameValidator = (rule, value, callback) =>
            {
                var regex =  /^[A-Za-z0-9\u4e00-\u9fa5]+$/
                if (regex.test(value) != true) {
                    callback(new Error('店铺名称不能为汉字英文和数字以外的字符'));
                }else {
                    callback();
                }

            };
            return {
                addressCandidate:[],
                industry:[],
                options: provinceAndCityData,
                longitude:0.2,
                latitude: 0.2,
                formValidate: {
                    shop_name: "",
                    province_city:[],
                    address:'',
                    industry:[],
                    shop_intro: '',
                    brand:''
                },
                ruleValidate: {
                    shop_name: [
                        { required: true, message: '店铺名字不能为空', trigger: 'blur' },
                        { validator: NameValidator,trigger:"change"}
                    ],
                    province_city: [

                        { required: true, type: 'array', min: 2, message: '请选择城市', trigger: 'change' },
                        { type: 'array', max: 2, message: '请选择城市', trigger: 'change' }
                    ],
                    address: [
                        { required: true, message: '请填写品牌', trigger: 'change' }
                    ],
                    brand: [
                        { required: true, message: '请填写地址', trigger: 'change' }
                    ],
                    industry: [
                        { required: true, type: 'array', min: 1, message: '至少选择一个营业领域', trigger: 'change' },
                        { type: 'array', max: 1, message: '最多选择一个营业领域', trigger: 'change' }
                    ],
                    shop_intro:[
                        { required: true, message: '请填写店铺介绍', trigger: 'change' }
                    ]

                },

                zoom: 15,
                center: [121.5273285,30.21515044],
                circle: {
                    clickable: true,
                    center: [121.5273285, 30.21515044],
                    radius: 200,
                    fillOpacity: 0.3,
                    strokeStyle: 'dashed',
                    fillColor: '#FFFF00',
                    strokeColor: '#00BFFF'
                },
                marker: {
                    position: [121.5273285, 30.21515044],
                    events: {
                        click: () => {
                            if (this.mywindow.visible === true) {
                                this.mywindow.visible = false
                            } else {
                                this.mywindow.visible = true
                            }
                        },
                        dragend: (e) => {
                            this.markers[0].position = [e.lnglat.lng, e.lnglat.lat]
                        }
                    },
                    visible: true,
                    draggable: false
                },
                mywindow: {
                    position: [121.5273285, 30.21515044],
                    content: "<text>" +" </text> ",
                    visible: true,
                    events: {
                        close () {
                            this.mywindow.visible = false
                        }
                    }
                },
                plugin: {
                    pName: 'Scale',
                    events: {
                        init (instance) {
                            console.log(instance)
                        }
                    }
                }
            }
        },
        created:function(){
            if(!this.$root.logged)
            {this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            }
            else {
                //get industry
                this.get_industry()

            }
            this.debouncedGetLocation = _.debounce(this.findAddress, 500)
        },
        watch: {
            'formValidate.address':
                {
                    handler(newVal, oldVal) {
                        if (newVal !== oldVal) {
                            this.debouncedGetLocation()
                        }
                    }
                },
            'formValidate.shop_name':
                {
                    handler(newVal, oldVal) {

                        this. mywindow.content="<text>"+ newVal+" </text> ";
                    }
                }
        },
        computed:{
        },
        methods: {
            get_industry()
            {
                return new Promise((resolve, reject) => {
                    getIndustry().then(res => {
                            console.log(res.data)
                            this.industry = res.data
                            resolve(res);
                        },
                        error => {
                            console.log(error)
                            reject(error.response.data.status);
                        })
                })
            },
            locate(val)
            {
                for(var i = 0 ;i < this. addressCandidate.length;i++)
                {
                    if(this.addressCandidate[i].name === val)
                    {
                        console.log(this.addressCandidate[i])
                        this.latitude = this.addressCandidate[i].location.split(",")[0];
                        this.longitude=  this.addressCandidate[i].location.split(",")[1];
                        var location = [this.latitude ,this.longitude];
                        this.center=location;
                        this.circle.center=location;
                        this.marker.position=location;
                        this.mywindow.position=location;
                    }
                }
            }
            ,
            addCandidate(pois){
                var res=[]
                for(var i = 0;i < pois.length;i++)
                {
                    var ob = {name:pois[i].name,location:pois[i].location}
                    res.push(ob);
                }
                return res;
            },
            findAddress()
            {
                this.axios({
                    method: 'get',
                    url: "https://restapi.amap.com/v3/place/text",
                    params:  {
                        key:"aadbbb26ac7ee34f4e15ef71c5d90dc2",
                        city:this.formValidate.province_city[1]==="市辖区"?this.formValidate.province_city[0]:this.formValidate.province_city[1],
                        keywords:this.formValidate.address,
                        offset:5
                    }
                }).then(response => {
                    this.addressCandidate=[];
                    this.addressCandidate = this.addCandidate(response.data.pois);
                })
                    .catch(error => {})
            },
            handleChange (value) {
                console.log(value)
            },
            handleSubmit (name) {
                if(!this.$root.logged) {
                    this.$Message.warning('请登录');
                    return;
                }

                this.$refs[name].validate((valid) => {
                    if (valid) {

                        this.addShop()
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            addShop()
            {
                var prefix="/warehouse"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix +"/merchant/shop",
                    data:  {
                        shop_name:this.formValidate.shop_name,
                        province: this.formValidate.province_city[0],
                        city:this.formValidate.province_city[1],
                        address:this.formValidate.address,
                        longitude:this.longitude,
                        latitude:this.latitude,
                        brand:this.formValidate.brand,
                        industry:this.formValidate.industry[0],
                        introduction:this.formValidate.shop_intro
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");
                        this.$Message.success('添加店铺成功');
                        this.handleReset('formValidate')
                    }
                })
                    .catch(error => {

                        if (error.response) {
                            if (error.response.data.status === 400  && error.response.data.message === "user not belong to any company") {
                                this.$Message.error('请添加公司');
                            }
                            else if(error.response.data.status === 400  &&  error.response.data.message === "shop name exists")
                            {
                                this.$Message.error('店铺名已存在');
                            }
                            else if(error.response.data.status === 401 && error.response.data.message ==="Forbidden, invalid access token." )
                            {
                                this.$Message.warning('请登录');
                            }
                            else{
                                this.$Message.error('添加店铺失败')
                            }

                            JSON.stringify(error);
                            console.log(error)
                        }
                    })
            }
        }
    }
</script>

<style scoped>

    .content{
        padding-top:20px;
        padding-left:100px;
        background-color: #fff;
    }
    .
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
    .el-cascader {
        display: inline-block;
        position: relative;
        font-size: 14px;
        line-height: 40px;
    }
    .el-cascader__label {
        position: absolute;
        left: 0;
        top: 0;
        height: 100%;
        padding: 0 25px 0 15px;
        color: #606266;
        width: 100%;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        box-sizing: border-box;
        cursor: pointer;
        text-align: left;
        font-size: inherit;
    }
    .amap-page-container {
        height: 400px;
    }
</style>
