<template>
    <Layout >

        <Content class="content">
            <p>公司名称:{{formValidate.company_name }}</p>
        </Content>
    </Layout>
</template>
<script>
    import  axios from"axios";
    export default {
        name: "InfoConfig",
        data () {
            return {
                former_company_name:"",

                formValidate: {
                    company_name: ''
                },
            }
        },
        created:function()
        {
            if(!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            }
            else {
                this.getcompany();
            }
        },

        methods: {
            getcompany()
            {

                var prefix = "/warehouse"
                //测试用的url
                 axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix + "/merchant/company"
                }).then(response => {
                    console.log(response);
                    if (response.data.status === 200) {
                        this.formValidate.company_name = response.data.data.company_name;
                        console.log("success");
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {
                                console.log(error.response);
                                this.$Message.error('暂无公司');
                            }
                        }
                    })
            },
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
