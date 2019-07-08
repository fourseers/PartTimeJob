<template>
    <Layout >

        <Content class="content">
            <Col span="8">
                <img width="180px" height="180px" src="/1.jpg"/>
            </Col>
            <Col span="8">

                <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                    <FormItem label="名称" prop="shop_name">
                        <Input v-model="formValidate.shop_name" placeholder="店铺名称"></Input>
                    </FormItem>

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
            </Col>
        </Content>
    </Layout>
</template>
<script>
    export default {
        data () {
            return {
                longtitude:0.0,
                latitude: 0.0,
                formValidate: {
                    shop_name: this.$route.params.shop_name,
                    province: this.$route.params.province,
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
                        { required: true, message: '请填写地址', trigger: 'change' }
                    ],
                    industry: [
                        { required: true, type: 'array', min: 1, message: '至少选择一个营业领域', trigger: 'change' },
                        { type: 'array', max: 2, message: '最多选择两个营业领域', trigger: 'change' }
                    ],
                    shop_intro:[
                        { required: true, message: '请填写店铺介绍', trigger: 'change' }
                    ]

                }
            }
        },
        mounted:{
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        // this.$Message.success('Success!');
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
                var prefix="https://da074679-0fbc-4e30-8c3a-e760e7f2c378.mock.pstmn.io"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "*",
                        'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
                    },
                    method: 'post',
                    url: prefix +"/merchant/shop",
                    data: this.$qs.stringify({
                        shop_name: this.formInline.shop_name,
                        province: this.formInline.province,
                        city:this.formInline.city,
                        address:this.formInline.address,
                        longitude:this.longitude,
                        latitude:this.latitude,
                        brand:this.formInline.brand,
                        industry:this.formInline.industry,
                        shop_intro:this.formInline. shop_intro
                    })
                }).then(response => {
                    console.log(response.data);
                    if(response.message === 'success')
                    {
                        //
                    }
                })
                    .catch(error => {
                        JSON.stringify(error);
                        console.log(error)
                    })
            }
        }
    }
</script>

<style scoped>
    .content{
        background-color: #fff;
    }
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
