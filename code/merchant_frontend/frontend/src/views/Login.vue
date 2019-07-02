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
        <FormItem>
          <Button class="ivu-btn" @click="handleSubmit('formInline')" >登录</Button>
        </FormItem>
      </Form>
    </Content>
  </Layout>
</template>
<script>

    import token from '../util/token.js'
  export default {
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
        this.$refs[name].validate((valid) => {
          if (valid) {
            this.$Message.success('Success!');
          this.login()

          } else {
            this.$Message.error('Fail!');
          }
        })
      },
        login(){
        var prefix="https://da074679-0fbc-4e30-8c3a-e760e7f2c378.mock.pstmn.io"
        this.axios({
            headers: {
                'Access-Control-Allow-Origin': "*",
                'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            method: 'post',
            url: prefix +"/merchant/login",
            data: this.$qs.stringify({
                username: this.formInline.user,
                password: this.formInline.password
            })
        }).then(response => {
            console.log(response.data);
            if(response.data.message === 'success')
            {
               this.$token.savetoken(response.data.data);
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

