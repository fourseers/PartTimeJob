<template>

    <Layout >
        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <FormItem label="名称" prop="shop_name">
                    <Input v-model="formValidate.shop_name" placeholder="店铺名称"></Input>
                </FormItem>
                <Upload
                        multiple
                        type="drag"
                        action="//jsonplaceholder.typicode.com/posts/">
                    <div style="padding: 2px 0; width:100px; height: 70px;">
                        <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                        <p>点击或者拖拽上传图片</p>
                    </div>
                </Upload>
                <FormItem label="省份" prop="province">
                    <Select v-model="formValidate.province" placeholder="选择省份">
                        <Option value="beijing">北京</Option>
                        <Option value="shanghai">上海</Option>
                        <Option value="shenzhen">深圳</Option>
                    </Select>
                </FormItem>
                <FormItem label="城市" prop="city">
                    <Select v-model="formValidate.city" placeholder="选择城市">
                        <Option value="beijing">北京</Option>
                        <Option value="shanghai">上海</Option>
                        <Option value="shenzhen">深圳</Option>
                    </Select>
                </FormItem>
                <FormItem label="地址" prop="address">
                    <Input v-model="formValidate.address" placeholder="店铺地址"></Input>
                </FormItem>
                <FormItem label="品牌" prop="brand">
                    <Input v-model="formValidate.brand" placeholder="品牌"></Input>
                </FormItem>

                <FormItem label="营业领域" prop="industry">
                    <CheckboxGroup v-model="formValidate.industry">
                        <Checkbox label="餐饮"></Checkbox>
                        <Checkbox label="衣服"></Checkbox>
                        <Checkbox label="教育"></Checkbox>
                        <Checkbox label="休闲"></Checkbox>
                    </CheckboxGroup>
                </FormItem>
                <FormItem label="店铺介绍" prop="shop_intro">
                    <Input v-model="formValidate.shop_intro" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="这里写店铺介绍"></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
                    <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
                </FormItem>
            </Form>
        </Content>
    </Layout>
</template>
<script>
    export default {
        name: "AddShop",
        data () {
            return {
                longitude:0.2,
                latitude: 0.2,
                formValidate: {
                    shop_name: '',
                    province:'',
                    city: '',
                    address:'',
                    industry:[],
                    shop_intro: '',
                    brand:''
                },
                ruleValidate: {
                    shop_name: [
                        { required: true, message: '店铺名字不能为空', trigger: 'blur' }
                    ],
                    province: [
                        { required: true, message: '请选择省份', trigger: 'change' }
                    ],
                    city: [
                        { required: true, message: '请选择城市', trigger: 'change' }
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

                }
            }
        },
        methods: {
            handleSubmit (name) {
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
                console.log(this.formValidate);
                console.log(this.longitude);
                console.log(this.latitude);
                console.log(this.formValidate.industry[0]);
                var prefix="/warehouse"
                //测试用的url
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
                        province: this.formValidate.province,
                        city:this.formValidate.city,
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
        padding:100px;
        background-color: #fff;
    }
    .
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
