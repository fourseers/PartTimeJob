<template>

    <Layout >
        <Content class="content">
            <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
                <FormItem label="名称" prop="name">
                    <Input v-model="formValidate.name" placeholder="岗位名称"></Input>
                </FormItem>
                <FormItem label="店铺" prop="shop">
                    <Select v-model="formValidate.shop" placeholder="选择店铺">
                        <Option value="beijing">店铺1</Option>
                        <Option value="shanghai">店铺2</Option>
                        <Option value="shenzhen">店铺3</Option>
                    </Select>
                </FormItem>

                <FormItem label="Date">
                    <Row>
                        <Col span="11">
                            <FormItem prop="date">
                                <Date-picker
                                        @on-change="handleChange"
                                        :options="DatePicker"
                                        type="date"
                                        format="yyyy-MM-dd"
                                        v-model="formValidate.begin_date"
                                        placeholder="请选择开始时间"
                                        placement="bottom-end">
                                </Date-picker>
                            </FormItem>
                        </Col>
                        <Col span="11">
                        <FormItem prop="date">
                            <Date-picker
                                    @on-change="handleChange"
                                    :options="DatePicker"
                                    type="date"
                                    format="yyyy-MM-dd"
                                    v-model="formValidate.end_date"
                                    placeholder="请选择结束时间"
                                    placement="bottom-end">
                            </Date-picker>  </FormItem>
                    </Col>
                    </Row>
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


                <FormItem label="Tag" prop="job_tag">
                        <Tag v-for="item in count" :key="item" :name="item" closable @on-close="handleClose2">标签{{ item + 1 }}</Tag>
                        <Button icon="ios-add" type="dashed" size="small" @click="handleAdd">添加标签</Button>


                </FormItem>
                <FormItem label="岗位要求" prop="job_detail">
                    <Input v-model="formValidate.job_detail" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="Enter something..."></Input>
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
                    name: '',
                    shop: '',
                    gender: '',
                    education:'',
                    begin_date: '',
                    end_date:'',
                    job_detail:''
                },

                count: [0, 1, 2],
                ruleValidate: {
                    name: [
                        { required: true, message: '请填写岗位名称', trigger: 'blur' }
                    ],
                    shop: [
                        { required: true, message: '请选择店铺', trigger: 'change' }
                    ],
                    gender: [
                        { required: true, message: '请选择岗位要求的性别', trigger: 'change' }
                    ],
                    education: [
                        { required: true, type: 'array', min: 1, message: '最少选择一个学历', trigger: 'change' },
                        { type: 'array', max: 1, message: '最多选择一个学历', trigger: 'change' }
                    ],
                    begin_date: [
                        { required: true, type: 'date', message: '请选择开始招聘时间', trigger: 'change' }
                    ],
                    end_date: [
                        { required: true, type: 'date', message: '请选择开始结束时间', trigger: 'change' }
                    ],
                    job_detail: [
                        { required: true, message: '请填写岗位具体要求', trigger: 'blur' }
                    ]
                }
            }
        },
        methods: {
            handleSubmit (name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$Message.success('Success!');
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
                    this.count.push(this.count[this.count.length - 1] + 1);
                } else {
                    this.count.push(0);
                }
            },
            handleClose2 (event, name) {
                const index = this.count.indexOf(name);
                this.count.splice(index, 1);
            }
        }
    }


</script>

<style scoped>
    .content{
        padding:50px 400px;
        background-color: #fff;
    }
    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }
</style>
