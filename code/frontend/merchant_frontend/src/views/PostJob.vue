<template>

    <Layout >
        <Content class="content">
            <Form class="form" ref="formValidate" :model="formValidate" :rules="ruleValidate"   >
                <Row>
                    <FormItem label="名称" prop="job_name">
                        <Input v-model="formValidate.job_name" placeholder="岗位名称"></Input>
                    </FormItem>
                </Row>
                <Row>
                    <FormItem label="店铺" prop="shop">
                        <Select v-model="formValidate.shop" placeholder="选择店铺">
                            <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id">{{ item.shop_name }}</Option>
                        </Select>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="招聘数量" prop="need_amount">
                        <Input   v-model="formValidate.need_amount" placeholder="招聘数量" number></Input>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="日薪" prop="salary">
                        <Input   v-model="formValidate.salary" placeholder="日薪" number></Input>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="工作时间">
                        <Row>
                            <Col span="11">
                                <FormItem prop="begin_date">
                                    <Date-picker
                                            type="date"
                                            format="yyyy-MM-dd"
                                            v-model="formValidate.begin_date"
                                            placeholder="请选择工作开始时间"
                                            placement="bottom-end">
                                    </Date-picker>
                                </FormItem>
                            </Col>
                            <Col span="11">
                                <FormItem prop="end_date">
                                    <Date-picker
                                            type="date"
                                            format="yyyy-MM-dd"
                                            v-model="formValidate.end_date"
                                            placeholder="请选择工作结束时间"
                                            placement="bottom-end">
                                    </Date-picker>  </FormItem>
                            </Col>
                        </Row>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="招聘时间">
                        <Row>
                            <Col span="11">
                                <FormItem prop="begin_apply_date">
                                    <Date-picker
                                            type="date"
                                            format="yyyy-MM-dd"
                                            v-model="formValidate.begin_apply_date"
                                            placeholder="请选择招聘开始时间"
                                            placement="bottom-end">
                                    </Date-picker>
                                </FormItem>
                            </Col>
                            <Col span="11">
                                <FormItem prop="end_apply_date">
                                    <Date-picker
                                            type="date"
                                            format="yyyy-MM-dd"
                                            v-model="formValidate.end_apply_date"
                                            placeholder="请选择招聘结束时间"
                                            placement="bottom-end">
                                    </Date-picker>  </FormItem>
                            </Col>
                        </Row>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="性别要求" prop="gender">
                        <CheckboxGroup v-model="formValidate.gender">
                            <Checkbox label="女"></Checkbox>
                            <Checkbox label="男"></Checkbox>
                        </CheckboxGroup>
                    </FormItem>
                    <FormItem label="学历要求" prop="education">
                        <CheckboxGroup v-model="formValidate.education">
                            <Checkbox label="小学"></Checkbox>
                            <Checkbox label="初中"></Checkbox>
                            <Checkbox label="中专"></Checkbox>
                            <Checkbox label="高中"></Checkbox>
                            <Checkbox label="专科"></Checkbox>
                            <Checkbox label="本科"></Checkbox>
                        </CheckboxGroup>
                    </FormItem>

                </Row>
                <Row>

                    <FormItem label="Tag" prop="job_tag">
                        <Tag  v-model="formValidate.tag_list" v-for="item in count" :key="item" :name="item" closable @on-close="handleClose2">{{ item }}</Tag>
                        <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签</Button>

                    </FormItem>

                </Row>
                <Row>
                    <FormItem label="岗位要求" prop="job_detail">
                        <Input v-model="formValidate.job_detail" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
                    </FormItem>

                </Row>
                <Row>
                    <FormItem>
                        <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
                        <Button @click="handleReset('formValidate')" style="margin-left: 8px">重置</Button>
                    </FormItem>

                </Row>
            </Form>
        </Content>
    </Layout>
</template>
<script>

    export default {

        name: "PostJob",
        data () {
            const validateApplyBeforeEnd= (rule, value, callback) => {
                if( this.formValidate.end_apply_date === "")
                    callback();
                if (value > this.formValidate.end_apply_date) {
                    callback(new Error('招聘开始时间应该晚于招聘结束时间'));
                } else {
                    callback();
                }
            }

            const validateApplyBeforeWork= (rule, value, callback) => {
                if( this.formValidate.end_apply_date === "")
                    callback();
                if (value < this.formValidate.end_apply_date) {
                    callback(new Error('工作开始时间应该晚于招聘结束时间'));
                } else {
                    callback();
                }
            }

            const validateApplyBeforeWork2= (rule, value, callback) => {
                if( this.formValidate.begin_date === "")
                    callback();
                if (value > this.formValidate.begin_date) {
                    callback(new Error('工作开始时间应该晚于招聘结束时间'));
                } else {
                    callback();
                }
            }
            const validateApplyBefore= (rule, value, callback) => {
                if( this.formValidate.begin_apply_date === "")
                    callback();
                if (value < this.formValidate.begin_apply_date) {
                    callback(new Error('招聘开始时间应该晚于招聘结束时间'));
                } else {
                    callback();
                }
            }

            const validateWorkBefore= (rule, value, callback) => {
                if( this.formValidate.begin_date === "")
                    callback();
                if (value < this.formValidate.begin_date) {
                    callback(new Error('工作结束时间应该晚于工作开始时间'));
                } else {
                    callback();
                }
            }

            const validateWorkBefore2= (rule, value, callback) => {
                if( this.formValidate.end_date === "")
                    callback();
                if (value > this.formValidate.end_date) {
                    callback(new Error('工作结束时间应该晚于工作开始时间'));
                } else {
                    callback();
                }
            }
            const validateAmount = (rule, value, callback) => {
                if (value === 0) {
                    callback(new Error('请填入正整数'));
                }
                if (!value) {
                    return callback(new Error('招聘数量不能为空'));
                }
                // 模拟异步验证效果
                setTimeout(() => {
                    if (!Number.isInteger(value)) {
                        callback(new Error('请填入整数'));
                    } else {
                        callback();
                    }
                }, 1000)
            };
            const validateSalary = (rule, value, callback) => {
                if (value === 0) {
                    callback(new Error('请填入正数'));
                }
                if (!value) {
                    return callback(new Error('日薪不能为空'));
                }
                // 模拟异步验证效果
                setTimeout(() => {
                    if (isNaN(value)) {
                        callback(new Error('请填入数字'));
                    } else  if(value<0)
                    {
                        callback(new Error('请填入正数'));
                    }
                    else if(value.toFixed(2) !== value)
                    {
                        callback(new Error('请填入两位小数'));
                    }
                    else{ callback();}
                }, 1000)
            };
            return {

                shops:[],
                formValidate: {
                    job_name: '',
                    shop: '',
                    gender:['男','女'],
                    education:[],
                    begin_date: '',
                    end_date:'',
                    begin_apply_date:'',
                    end_apply_date:'',
                    job_detail:'',
                    need_amount: '',
                    job_tag:[],
                    salary:''
                },
                count: ["标签1","标签2"],
                ruleValidate: {
                    job_name: [
                        { required: true, message: '请填写岗位名称', trigger: 'blur' }
                    ],
                    shop: [
                        { required: true, message: '请选择店铺', trigger: 'change' }
                    ],
                    need_amount: [
                        { required: true, message: '请填写招聘人数', trigger: 'change' },
                        { validator: validateAmount, trigger: 'blur' }
                    ],
                    salary:[
                        { required: true, message: '请填写日薪', trigger: 'change' },
                        { validator: validateSalary, trigger: 'blur' }
                    ],
                    gender: [
                        { required: true,type: 'array', min: 1,  message: '请选择岗位要求的性别', trigger: 'change' }
                    ],
                    education: [
                        { required: true, type: 'array', max: 1, message: '最多选择一个学历', trigger: 'change' }
                    ],
                    begin_apply_date: [
                        { required: true, type: 'date', message: '请选择招聘开始时间', trigger: 'change' },
                        { validator:validateApplyBeforeEnd, trigger: 'change'},

                    ],
                    end_apply_date: [
                        { required: true, type: 'date', message: '请选择招聘结束时间', trigger: 'change' },
                        { validator:validateApplyBefore, trigger: 'change'},
                        { validator:validateApplyBeforeWork2, trigger: 'blur'}
                    ],
                    begin_date: [
                        { required: true, type: 'date', message: '请选择工作开始时间', trigger: 'change' },
                        { validator:validateApplyBeforeWork, trigger: 'blur'},
                        { validator:validateWorkBefore2,trigger: 'change'},
                    ],
                    end_date: [
                        { required: true, type: 'date', message: '请选择工作结束时间', trigger: 'change' },
                        { validator:validateWorkBefore, trigger: 'change'},
                    ],
                    job_detail: [
                        { required: true, message: '请填写岗位具体要求', trigger: 'blur' }
                    ]
                }
            }
        },
        watch:{
            formValidate: {
                handler(val, oldVal){
                    console.log(val.end_apply_date.toUTCString(), oldVal.end_apply_date.toUTCString());
                    // to be done

                },
                deep:true

            }
        },
        computed:{
            gender_need: function () {
                if( this.formValidate.gender.length === 1)
                {
                    if(this.formValidate.gender[0] === '男')
                        return 0;
                    if(this.formValidate.gender[0] === '女')
                        return 1;
                }
                else if(this.formValidate.gender.length === 2)
                    return 2;
                return 2;
            }
        },
        created: function()  {
            if(!this.$root.logged) {
                this.$Message.warning('请登录');
            }
            else{
                var prefix = "/warehouse"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'get',
                    url: prefix + "/merchant/shop"
                }).then(response => {
                    console.log(response);
                    console.log(response.data.data.shops);
                    if (response.data.status === 200) {

                        this.shops = response.data.data.shops;
                        console.log(this.shops);
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {
                                console.log(error.response);
                            } else if (error.response.data.status === 401 && error.response.data.message === "Forbidden, invalid access token.") {
                                this.$Message.warning('请登录');
                            }
                        }
                    })
            }

        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        //this.$Message.success('Success!');
                        this.postJob()

                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset (name) {
                this.$refs[name].resetFields();
            },
            handleAdd () {
                if (this.count.length ) {
                    if(this.count.length < 5)
                        this.count.push("标签"+(this.count.length+1).toString());
                } else {
                    this.count.push(0);
                }
            },
            handleClose2 (event, name) {
                const index = this.count.indexOf(name);
                this.count.splice(index, 1);
            },
            postJob()
            {
                console.log(this.formValidate.shop)
                console.log(this.formValidate.job_tag)
                var prefix="/arrangement"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix +"/merchant/job",
                    data:  {
                        shop_id:this.formValidate.shop,
                        job_name:this.formValidate.job_name,
                        begin_date:this.formValidate.begin_date.toUTCString(),
                        end_date:this.formValidate.end_date.toUTCString(),
                        job_detail:this.formValidate.job_detail,
                        need_gender:this.gender_need,
                        need_amount:this.formValidate.need_amount,
                        begin_apply_date:this.formValidate.begin_apply_date.toUTCString(),
                        end_apply_date:this.formValidate.end_apply_date.toUTCString(),
                        education:this.formValidate.education[0],
                        tag_list:this.formValidate.job_tag,
                        salary:this.formValidate.salary
                    }
                }).then(response => {
                    console.log(response.data);
                    if(response.status === 200)
                    {
                        this.$Message.success('发布岗位成功');
                        this.handleReset('formValidate')
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {
                                this.$Message.error("发布岗位失败")
                            }

                            else if(error.response.data.status === 401 && error.response.data.message ==="Forbidden, invalid access token." )
                            {
                                this.$Message.success('请登录');
                            }
                        }
                        JSON.stringify(error);
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
