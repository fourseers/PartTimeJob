<template>

    <Layout >

        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <Col span="4">
                    <Upload
                            multiple
                            type="drag"
                            action="//jsonplaceholder.typicode.com/posts/">
                        <div style="padding-left:20px;  height: 70px;">
                            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                            <p>点击或者拖拽上传图片</p>
                        </div>
                    </Upload>
                </Col>
                <Col span="12">
                    <FormItem label="名称" prop="shop_name">
                        <Input v-model="formValidate.shop_name" placeholder="店铺名称"></Input>
                    </FormItem>
                    <FormItem label="地址" prop="address">
                        <Input v-model="formValidate.address" placeholder="店铺地址"></Input>
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
                    <FormItem label="店铺介绍" prop="introduction">
                        <Input v-model="formValidate.introduction" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="这里写店铺介绍"></Input>
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

    import { provinceAndCityData } from 'element-china-area-data'
    export default {
        name: "AddShop",
        data () {
            return {
                industry:[],
                options: provinceAndCityData,
                longitude:0.2,
                latitude: 0.2,
                formValidate: {
                    shop_name: this.$route.params.shop_name,
                    province_city:[this.$route.params.province,this.$route.params.city],
                    address: this.$route.params.address,
                    industry: [this.$route.params.industry],
                    shop_intro: this.$route.params.shop_intro,
                    brand:this.$route.params.brand,
                    introduction:this.$route.params.introduction
                },
                ruleValidate: {
                    shop_name: [
                        { required: true, message: '店铺名字不能为空', trigger: 'blur' }
                    ],
                    province_city: [
                        { required: true, type: 'array', min: 2, message: '请选择城市', trigger: 'change' },
                        { type: 'array', max: 2, message: '请选择城市', trigger: 'change' }
                    ],
                    address: [
                        { required: true, message: '请填写地址', trigger: 'change' }
                    ],
                    brand: [
                        { required: true, message: '请填写品牌', trigger: 'change' }
                    ],
                    industry: [
                        { required: true, type: 'array', min: 1, message: '至少选择一个营业领域', trigger: 'change' },
                        { type: 'array', max: 1, message: '最多选择一个营业领域', trigger: 'change' }
                    ],
                    introduction:[
                        { required: true, message: '请填写店铺介绍', trigger: 'change' }
                    ]

                }
            }
        },
        created:function(){
            if(!this.$root.logged)
            {this.$Message.warning('请登录');}
            else {

                console.log(this.$route.params );
                //get industry
                var prefix="/warehouse"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix +"/merchant/industry",
                }).then(response => {
                    this.industry = response.data.data;
                    console.log( this.industry);
                    if(response.status ===  200)
                    {
                        console.log("success");
                    }
                })
                    .catch(error => {
                        console.log(error)
                    })
            }

        },
        computed:
            {
            },
        methods: {
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

                        this.updateShop()
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            updateShop()
            { //修改店铺
                var prefix="/warehouse"
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://47.103.112.85:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'put',
                    url: prefix +"/merchant/shop",
                    data:  {
                        shop_id:this.$route.params.shop_id,
                        shop_name:this.formValidate.shop_name,
                        province: this.formValidate.province_city[0],
                        city:this.formValidate.province_city[1],
                        address:this.formValidate.address,
                        longitude:this.$route.params.longitude,
                        latitude:this.$route.params.latitude,
                        brand:this.formValidate.brand,
                        industry:this.formValidate.industry[0],
                        introduction:this.formValidate.introduction
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");
                        this.$Message.success('修改店铺成功');
                        this.$router.push({ name: "manageshop"})
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if(error.response.data.status === 401 && error.response.data.message ==="Forbidden, invalid access token." )
                            {
                                this.$Message.warning('请登录');
                                this.$root.logged= false;
                            }
                            else if (error.response.data.status === 400 && error.response.data.message ==="shop name exists")
                            {
                                this.$Message.error('店铺名已存在')
                            }
                            else{
                                this.$Message.error('修改店铺失败')
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
</style>
