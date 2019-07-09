<template>

    <Layout >
        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model="formValidate.company_name" placeholder="公司名称"></Input>
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
                        this.addCompany();
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            addCompany()
            {
                    console.log(this.formValidate);
                    var prefix="/warehouse"
                    //测试用的url
                    this.axios({
                        headers: {
                            'Access-Control-Allow-Origin': "*",
                            'Content-type': 'application/json'
                        },
                        method: 'post',
                        url: prefix +"/merchant/company",
                        data: this.$qs.stringify({
                            company_name:this.formValidate.company_name
                        })
                    }).then(response => {
                        console.log(response);
                        if(response.message === 'success')
                        {

                            console.log("success");
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
        padding:100px 400px;
        background-color: #fff;
    }
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
