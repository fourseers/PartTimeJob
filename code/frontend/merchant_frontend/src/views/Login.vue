<template>
  <Layout>
    <Content class="content" v-if="!this.$root.logged  " >
      <br>
      <Form ref="formInline" :model="formInline" :rules="ruleInline" inline>
        <FormItem   prop="user">
          <Input name="user"  type="text" v-model="formInline.user" placeholder="用户名">
            <Icon type="ios-person-outline" slot="prepend"></Icon>
          </Input>
        </FormItem>
        <br>
        <FormItem prop="password">
          <Input  name="password"  type="password" v-model="formInline.password" placeholder="密码">
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

  export default {
    name: 'Login',
    data () {
      return {
        state:"",
        code:"",
        formInline: {
          user: '',
          password: ''
        },
        ruleInline: {
          user: [
            { required: true, message: 'Please fill in the user name', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'Please fill in the password.', trigger: 'blur' },
            { type: 'string', min: 6, message: '密码最少6位', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      handleSubmit(name) {

        console.log(this.$token.loadToken() )

        console.log( this.$token.loadToken().access_token === "null")
        this.$refs[name].validate((valid) => {
          if (valid) {
            //this.$Message.success('Success!');
            this.login(this.formInline.user,this.formInline.password)

          } else {
            this.$Message.error('Fail!');
          }
        })
      },
      login(username,password){
        var prefix="auth";
        this.axios({
          headers: {
            'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
            'Content-type': 'application/json',
            'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng=='
          },
          method: 'post',
          crossDomain: true,
          url: prefix+ "/merchant/login",
          data:{
            username:username,
            password: password
          }
        }).then(response => {
          console.log(response);
          if(response.data.status === 200)
          {
            this.$token.savetoken(response.data.data);
            this.$Message.success('登录成功');
            console.log(this.$token.loadToken());
            this.$root.logged =true;
            this.$router.push({ name: "postjob"})
          }
        }).catch(error=> {
          if(error.response){
            if(error.response.data.status === 400)
            {
              console.log(error.response);
              this.$Message.error('用户名或者密码错误');
            }
          }

          if(error.response.data.status === 401)
          {
            console.log(error.response);
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

