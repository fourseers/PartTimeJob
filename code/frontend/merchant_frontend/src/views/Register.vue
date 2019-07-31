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
    import { Form } from 'iview';
    export default {
        name: "Register",
        components: {
            'Form': Form
        },
        data () {
            const validatePass = (rule, value, callback) => {
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
                        this.register(this.formInline.user,this.formInline.password);
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            register(user,password)
            {
                var prefix="auth";
                return new Promise((resolve, reject) => {
                    this.axios({
                        headers: {
                            'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                            'Content-type': 'application/json',
                            'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng=='
                        },
                        method: 'post',
                        url: prefix +"/merchant/register",
                        data: {
                            username: user,
                            password:  password
                        }
                    }).then(({ status, data }) => {
                        console.log( data);
                        if( status === 200 )
                        {
                            this.$Message.success('注册成功');
                            this.$token.savetoken( data.data);
                            console.log(this.$token.loadToken());
                            this.$root.logged = true;
                            this.$router.push({ name: "postjob"});
                        }
                        resolve(data);

                    }).catch(error=> {
                        if(error.response){
                            if(error.response.data.message === "User exists.")
                            {
                                this.$Message.error('用户名已存在');
                            }

                        }
                        reject( error.response.data.status);
                    })
                });
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

