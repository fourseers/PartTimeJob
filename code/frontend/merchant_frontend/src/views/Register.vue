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

        name: "Register",
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
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng=='

                    },
                    method: 'post',
                    url: prefix +"/merchant/register",
                    data: {
                        username: this.formInline.user,
                        password: this.formInline.password
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.data.status === 200 )
                    {

                        this.$Message.success('注册成功');
                        this.$token.savetoken(response.data.data);
                        console.log(this.$token.loadToken());
                    }

                    this.$router.push({ name: "postjob"});
                }).catch(error=> {
                    if(error.response){
                        if(error.response.data.message === "User exists.")
                        {
                            this.$Message.error('用户名已存在');
                        }

                        if (error.response.data.status === 401) {
                            this.$Message.error('auth错误');
                        }
                        if (error.response.data.status === 500) {
                            this.$Message.error('服务器错误');
                        }
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
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>

