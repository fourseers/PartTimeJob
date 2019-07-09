<template>
    <Layout>
        <Content class="content">
            <br>
            <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
                <FormItem prop="user">
                    <Input type="text" v-model="formInline.user" placeholder="用户名">
                        <Icon type="ios-person-outline" slot="prepend"></Icon>
                    </Input>
                </FormItem>
                <br>
                <FormItem prop="password">
                    <Input type="password" v-model="formInline.password" placeholder="密码">
                        <Icon type="ios-lock-outline" slot="prepend"></Icon>
                    </Input>
                </FormItem>
                <br>
                <FormItem prop="password2">
                    <Input type="password" v-model="formInline.password2" placeholder="密码">
                        <Icon type="ios-lock-outline" slot="prepend"></Icon>
                    </Input>
                </FormItem>
                <br>
                <FormItem>
                    <Button class="ivu-btn" @click="handleSubmit('formInline')" >注册</Button>
                </FormItem>
            </Form>
        </Content>
    </Layout>
</template>
<script>
    export default {
        data () {
            const validatePass = (rule, value, callback) => {
                // console.log(rule)
                // console.log(value)
                if (value === '' || value !== this.formInline.password) {
                    callback(new Error('两次输入不一致'));
                } else {
                    callback();
                }
            }
            return {
                formInline: {
                    user: '',
                    password: '',
                    password2:''
                },

                ruleInline: {
                    user: [
                        { required: true, message: '请输入用户名', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码最少6位', trigger: 'blur' }
                    ],
                    password2: [
                        { required: true,message: 'Please fill in the password.', trigger: 'blur' },
                        { type: 'string', min: 6, message: '密码最少6位', trigger: 'blur' },
                        {validator:validatePass, trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            handleSubmit: function (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.register();
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
           register()
            {
                var prefix="auth";
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30553",
                        'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng=='

                    },
                    dataType: 'jsonp',
                    crossDomain: true,
                    method: 'post',
                    url: prefix +"/merchant/register",
                    data: this.$qs.stringify({
                        username: this.formInline.user,
                        password: this.formInline.password
                    })
                }).then(response => {
                    console.log(response.data);
                    if(response.message === 'success')
                    {
                        this.$token.savetoken(response.data);
                        console.log(this.$token.loadToken());
                    }

                })
                    .catch(error => {
                        JSON.stringify(error)
                        console.log(error)
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
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>

