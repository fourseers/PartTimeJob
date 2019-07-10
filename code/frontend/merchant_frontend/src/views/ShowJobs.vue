<template>
    <p>这是已经发布的岗位页面</p>
</template>

<script>
    export default {
        name: "ShowJobs",
        data () {
            return {
                jobs:[]
            }},
        methods:{
            getJob()
            {
                var prefix="/warehouse"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "*",
                        'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix +"/merchant/job",
                    data:  {
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.status === 200)
                    {

                        this.job = response.data
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

</style>
