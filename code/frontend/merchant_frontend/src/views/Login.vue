<template>
  <Layout>
    <Content class="content" v-if="!this.$root.logged  " >
      <br>
      <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
        <FormItem   prop="user">
          <Input  id="name"  type="text" v-model="formInline.user" placeholder="用户名">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <br>
        <FormItem prop="password">
          <Input   id="password" type="password" v-model="formInline.password" placeholder="密码">
            <Icon type="ios-lock-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <br>
        <FormItem>
          <Button class="ivu-btn" @click="handleSubmit('formInline')" >登录</Button>
        </FormItem>
      </Form>
    </Content>

    <Content class="content" v-if="this.$root.logged" >
      <p>您已经登录</p>
    </Content>
  </Layout>
</template>

<script>

  import axios from 'axios/index';

  export default {
    name: 'Login',
    data() {
      return {
        state: "",
        code: "",
        formInline: {
          user: '',
          password: ''
        },
        ruleInline: {
          user: [
            {required: true, message: 'Please fill in the user name', trigger: 'blur'}
          ],
          password: [
            {required: true, message: 'Please fill in the password.', trigger: 'blur'},
            {type: 'string', min: 6, message: '密码最少6位', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      handleSubmit(name) {
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.login_process(this.formInline.user, this.formInline.password)

          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      //this.$Message.success('Success!');
      login_process( user,  password){
        this.login( user,  password).then(res => {
                  console.log(res);
                  this.$token.savetoken(res.data);
                  this.$Message.success('登录成功');
                  console.log(this.$token.loadToken());
                  this.$root.logged =true;
                  this.$router.push({ name: "postjob"})
                },
                error => {
                  console.log(error.response);
                  if (error.response) {
                    if (error.response.data.status === 400) {
                      this.$Message.error('用户名或者密码错误');
                    }

                    if (error.response.data.status === 401) {
                      this.$Message.error('auth错误');
                    }
                    if (error.response.data.status === 500) {
                      this.$Message.error('服务器错误');
                    }
                  }
                  else
                  {
                    this.$Message.error('登录失败');
                  }

                });
      },
      login(username, password) {
        const url = 'http://202.120.40.8:30552/auth/merchant/login';
        return new Promise((resolve, reject) => {
          axios({
            method: 'POST',
            url,
            headers: {
              'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
              'Content-type': 'application/json',
              'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng=='
            },
            data:{
              username:username,
              password:password
            }
          }).then(({ status, data }) => {
            if (status === 200) {
              resolve(data);
            } else {
              reject( data);
            }
          }).catch(error => {
            reject( error);
          });
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


