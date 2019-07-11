<template>
    <Layout >

        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model="formValidate.company_name" placeholder="公司名称"></Input>
                </FormItem>
                <FormItem>
                    <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
                    <Button @click="handleReset()" style="margin-left: 8px">初始</Button>
                </FormItem>
            </Form>
        </Content>
    </Layout>
</template>
<script>
    export default {
        name: "InfoConfig",
        data () {
            return {
                former_company_name:"",

                formValidate: {
                    company_name: ''
                },
                ruleValidate: {
                    company_name: [
                        { required: true, message: '公司名字不能为空', trigger: 'blur' }
                    ]

                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.ChangeCompany();
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.formValidate.company_name = this.former_company_name;
            },
            ChangeCompany()
            {
                console.log(this.$token.loadToken().access_token)
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
                    url: prefix +"/merchant/company",
                    data:  {
                        company_name:this.formValidate.company_name
                    }
                }).then(response => {
                    console.log(response);
                    if(response.status ===  200)
                    {
                        console.log("success");
                        this.$Message.success('成功');
                    }

                })
                    .catch(error => {
                        //JSON.stringify(error);
                        this.$Message.error('失败');
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
    .
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
