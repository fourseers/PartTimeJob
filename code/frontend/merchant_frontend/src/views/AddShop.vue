<template>

    <Layout >
        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model="formValidate.name" placeholder="店铺名称"></Input>
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
        </Content>
    </Layout>
</template>
<script>
    export default {
        data () {
            return {
                formValidate: {
                    name: '',
                    province:'',
                    city: '',
                    address:'',
                    industry:[],
                    shop_intro: '',
                    brand:''
                },
                ruleValidate: {
                    name: [
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
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$Message.success('Success!');
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            }
        }
    }
</script>

<style scoped>
    .content{
        padding:50px 400px;
        background-color: #fff;
    }
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
