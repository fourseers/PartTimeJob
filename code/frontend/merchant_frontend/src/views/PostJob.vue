<template>

    <Layout >
        <Content class="content">
            <Col span="18">
                <Form class="form" ref="formValidate" :model="formValidate" :rules="ruleValidate"   >
                    <Row>
                        <FormItem label="名称" prop="job_name">
                            <Input v-model="formValidate.job_name" placeholder="岗位名称"></Input>
                        </FormItem>
                    </Row>
                    <Row>
                        <FormItem class="ivu-form-item ivu-form-item-required" label="店铺" prop="shops">
                            <Select v-model="formValidate.shop" placeholder="选择店铺">
                                <Option v-for="item in shops" :value="item.shop_id" :key="item.shop_id">{{ item.shop_name }}</Option>
                                <Page :total="total_elements_shop" :current="pagenum2"  @on-change="changePage"></Page>
                            </Select>
                        </FormItem>

                    </Row>
                    <Row>
                        <FormItem class="ivu-form-item ivu-form-item-required" label="招聘数量" prop="need_amount">
                            <Input   v-model="formValidate.need_amount" placeholder="招聘数量" number></Input>
                        </FormItem>

                    </Row>
                    <Row>
                        <FormItem class="ivu-form-item ivu-form-item-required" label="日薪" prop="salary">
                            <Input   v-model="formValidate.salary" placeholder="日薪" number></Input>
                        </FormItem>

                    </Row>
                    <Row>
                        <FormItem class="ivu-form-item ivu-form-item-required" label="工作日期">
                            <Row>

                                <Col span="8">
                                    <FormItem prop="begin_date">
                                        <Date-picker
                                                type="date"
                                                format="yyyy-MM-dd"
                                                v-model="formValidate.begin_date"
                                                placeholder="请选择工作开始日期"
                                                :options="startTimeOptions"
                                                @on-change="startTimeChange"
                                                placement="bottom-end">
                                        </Date-picker>
                                    </FormItem>
                                </Col>
                                <Col span="8">
                                    <FormItem prop="end_date">
                                        <Date-picker
                                                type="date"
                                                format="yyyy-MM-dd"
                                                v-model="formValidate.end_date"
                                                placeholder="请选择工作结束日期"
                                                :options="endTimeOptions"
                                                @on-change="endTimeChange"
                                                placement="bottom-end">
                                        </Date-picker>
                                    </FormItem>
                                </Col>
                            </Row>
                        </FormItem>
                        <FormItem class="ivu-form-item ivu-form-item-required" label="工作时间">
                            <Row>
                                <Col span="8">
                                    <FormItem prop="begin_time">
                                        <TimePicker format="HH:mm" placeholder="Select time"

                                                    v-model="formValidate.begin_time"
                                        ></TimePicker>
                                    </FormItem>
                                </Col>
                                <Col span="8">
                                    <FormItem prop="end_time">
                                        <TimePicker format="HH:mm" placeholder="Select time"
                                                    v-model="formValidate.end_time"
                                        ></TimePicker>
                                    </FormItem>

                                </Col>
                            </Row>

                        </FormItem>
                        <FormItem class="ivu-form-item ivu-form-item-required"  label="招聘时间">
                            <Row>
                                <Col span="8">
                                    <FormItem prop="begin_apply_date">
                                        <Date-picker
                                                type="date"
                                                format="yyyy-MM-dd"
                                                v-model="formValidate.begin_apply_date"
                                                placeholder="请选择招聘开始时间"

                                                :options="startTimeOptions2"
                                                @on-change="startTimeChange2"
                                                placement="bottom-end">
                                        </Date-picker>
                                    </FormItem>

                                    <FormItem prop="begin_apply_time">
                                        <TimePicker format="HH:mm" placeholder="Select time"
                                                    v-model="formValidate.begin_apply_time"
                                        ></TimePicker>
                                    </FormItem>
                                </Col>
                                <Col span="8">
                                    <FormItem prop="end_apply_date">
                                        <Date-picker
                                                type="date"
                                                format="yyyy-MM-dd"
                                                v-model="formValidate.end_apply_date"
                                                placeholder="请选择招聘结束时间"

                                                :options="endTimeOptions2"
                                                @on-change="endTimeChange2"
                                                placement="bottom-end">
                                        </Date-picker>
                                    </FormItem>

                                    <FormItem prop="end_apply_time">
                                        <TimePicker format="HH:mm" placeholder="Select time"
                                                    v-model="formValidate.end_apply_time"
                                        ></TimePicker>
                                    </FormItem>
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
                                <Checkbox label="初中毕业及以下"></Checkbox>
                                <Checkbox label="中专毕业"></Checkbox>
                                <Checkbox label="高中毕业"></Checkbox>
                                <Checkbox label="大专毕业"></Checkbox>
                                <Checkbox label="本科毕业"></Checkbox>
                                <Checkbox label="研究生毕业及以上"></Checkbox>
                            </CheckboxGroup>
                        </FormItem>

                    </Row>
                    <Row>

                        <FormItem label="Tag"  prop="job_tag">
                            <CheckboxGroup v-model="formValidate.job_tag" v-for="item in job_tags">
                                <Checkbox :label="item.id "  >{{item.name}}</Checkbox>
                            </CheckboxGroup>

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

            </Col>
        </Content>
    </Layout>
</template>
<script>

    import DateTime from "luxon/src/datetime";

    import {getShops} from '../util/getShops.js'
    export default {

        name: "PostJob",
        data() {
            const NameValidator = (rule, value, callback) =>
            {
                var regex =  /^[A-Za-z0-9\u4e00-\u9fa5]+$/
                if (regex.test(value) != true) {
                    callback(new Error('岗位名称不能为汉字英文和数字以外的字符'));
                }else {
                    callback();
                }

            }
            const validateApplyBeforeEnd = (rule, value, callback) => {
                if (this.formValidate.end_apply_date === "")
                    callback();
                if (value > this.formValidate.end_apply_date) {
                    callback(new Error('招聘开始时间应该晚于招聘结束时间'));
                } else {
                    callback();
                }
            }

            const validateApplyBefore = (rule, value, callback) => {
                if (this.formValidate.begin_apply_date === "")
                    callback();
                if (value < this.formValidate.begin_apply_date) {
                    callback(new Error('招聘开始时间应该晚于招聘结束时间'));
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
                    } else if (value < 0) {
                        callback(new Error('请填入正数'));
                    } else if (value.toFixed(2) != value) {
                        callback(new Error('请填入两位小数'));
                    } else {
                        callback();
                    }
                }, 1000)
            };
            return {
                job_tags:[],
                pagenum2:1,
                total_elements_shop:0,
                startTimeOptions: {}, //开始日期设置
                endTimeOptions: {}, //结束日期设置
                startTimeOptions2: {}, //开始日期设置
                endTimeOptions2: {},
                shops: [],
                formValidate: {
                    begin_time:"00:00",
                    end_time:"23:59",
                    begin_apply_time:"00:00",
                    end_apply_time:"23:59",
                    job_name: '',
                    shop: 0,
                    gender: ['男', '女'],
                    education: [],
                    begin_date: '',
                    end_date: '',
                    begin_apply_date: '',
                    end_apply_date: '',
                    job_detail: '',
                    need_amount: '',
                    job_tag: [],
                    salary: ''
                },
                ruleValidate: {
                    job_name: [
                        {required: true, message: '请填写岗位名称', trigger: 'blur'},
                        {
                            validator: NameValidator,trigger:"change"
                        }
                    ],
                    shop: [
                        {required: true, message: '请选择店铺', trigger: 'change'}
                    ],
                    need_amount: [
                        {validator: validateAmount, trigger: 'blur'}
                    ],
                    salary: [
                        {validator: validateSalary, trigger: 'blur'}
                    ],
                    gender: [
                        {required: true, type: 'array', min: 1, message: '请选择岗位要求的性别', trigger: 'change'}
                    ],
                    education: [
                        {required: true, type: 'array', max: 1, message: '最少选择一个学历', trigger: 'change'}
                    ],
                    begin_apply_date: [
                        {required: true, type: 'date', message: '请选择招聘开始时间', trigger: 'change'},
                        {validator: validateApplyBeforeEnd, trigger: 'change'},

                    ],
                    end_apply_date: [
                        {required: true, type: 'date', message: '请选择招聘结束时间', trigger: 'change'},
                        {validator: validateApplyBefore, trigger: 'change'},
                    ],
                    begin_date: [
                        {required: true, type: 'date', message: '请选择工作开始时间', trigger: 'change'}
                    ],
                    end_date: [
                        {required: true, type: 'date', message: '请选择工作结束时间', trigger: 'change'}

                    ],
                    job_detail: [
                        {required: true, message: '请填写岗位具体要求', trigger: 'blur'}
                    ]
                }
            }
        },
        computed: {
            gender_need: function () {
                if (this.formValidate.gender.length === 1) {
                    if (this.formValidate.gender[0] === '男')
                        return 0;
                    if (this.formValidate.gender[0] === '女')
                        return 1;
                } else if (this.formValidate.gender.length === 2)
                    return 2;
                return 2;
            },

        },
        created: function () {
            if (!this.$root.logged) {
                this.$Message.warning('请登录');
                this.$router.push({name: "login"})
            } else {
                //获取第一页表格
                this.mockTableData1 (0);
                this.get_tags();
            }

        },
        methods: {
            get_tags()
            {
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
                    url: prefix + "/merchant/tags",
                }).then(response => {
                    console.log(response.data);
                    if (response.status === 200) {
                        this.job_tags = response.data.data;

                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {

                            }
                        }
                        JSON.stringify(error);
                        console.log(error)
                    })

            },
            mockTableData1 (pagenum) {
                //get shops
                getShops(pagenum).then(res => {
                        console.log( res.data)
                        this.shops = res.data.content
                        this.total_elements_shop=res.data.total_elements
                    },
                    error => {
                        if (error.response) {
                            if (error.response.data.status === 400 && error.response.data.message === "no shops") {
                                console.log(error.response);
                                this.$Message.error('暂无店铺');
                            } else if (error.response.data.status === 400 && error.response.data.message === "incorrect param") {
                                console.log(error.response);
                                this.$Message.error('参数错误');
                            }
                        }

                    }

                )
            },changePage (index) {
                // The simulated data is changed directly here, and the actual usage scenario should fetch the data from the server

                this.mockTableData1(index-1);
            },
            format_only_date(d,str)
            {
                var a = str.split(':');
                var y=d.getUTCFullYear();
                var rettime=  DateTime.fromObject({year:y, month: d.getMonth()+1, day:d.getDate(), hour:  Number(a[0]), minute:  Number(a[1]), second: 0, zone: "UTC+8"}).toFormat( 'yyyy-MM-dd');

                return rettime;

            },
            format_time(str)
            {
                var a = str.split(':');
                var rettime=  DateTime.fromObject({year:1970, month: 1, day: 1, hour:  Number(a[0]), minute:  Number(a[1]), second: 0, zone: "UTC+8"}).toFormat( 'HH:mm:ss');
                return rettime
            },
            format_date(d,str)
            {
                var a = str.split(':');
                var y=d.getUTCFullYear();
                var rettime=  DateTime.fromObject({year:y, month: d.getMonth()+1, day:d.getDate(), hour:  Number(a[0]), minute:  Number(a[1]), zone: "UTC+8"}).toFormat( 'yyyy-MM-dd HH:mm:ss');

                return rettime;
            },
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        if( !this.formValidate.shop)
                        {
                            console.log( this.formValidate.shop)
                            this.$Message.error("请选择店铺");
                            return;
                        }
                        if( this.formValidate.begin_date >
                            this.formValidate.end_apply_date )
                        {
                            this.postJob()
                        }
                        else {

                            this.formValidate.begin_date=""
                            this.formValidate.end_date=""

                            this.$Message.error("工作开始日期不能比招聘结束时间早");

                            this.startTimeOptions= {};
                            this.endTimeOptions={};
                        }
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            handleReset(name) {
                this.$refs[name].resetFields();
                this.startTimeOptions= {}, //开始日期设置
                    this.endTimeOptions={}, //结束日期设置
                    this.startTimeOptions2={}, //开始日期设置
                    this.endTimeOptions2={}
            },
            handleAdd() {
                if (this.count.length) {
                    if (this.count.length < 5)
                        this.count.push("标签" + (this.count.length + 1).toString());
                } else {
                    this.count.push(0);
                }
            },
            handleClose2(event, name) {
                const index = this.count.indexOf(name);
                this.count.splice(index, 1);
            },
            postJob() {
                var prefix = "/arrangement"
                //测试用的url
                this.axios({
                    headers: {
                        'Access-Control-Allow-Origin': "http://202.120.40.8:30552",
                        'Content-type': 'application/json',
                        'Authorization': 'Basic d2ViQ2xpZW50OjEyMzQ1Ng==',
                        'x-access-token': this.$token.loadToken().access_token,
                    },
                    method: 'post',
                    url: prefix + "/merchant/job",
                    data: {
                        shop_id: this.formValidate.shop,
                        job_name: this.formValidate.job_name,
                        begin_date:  this.format_only_date(this.formValidate.begin_date,this.formValidate.begin_time),
                        end_date: this.format_only_date( this.formValidate.end_date,this.formValidate.end_time),
                        begin_time:  this.format_time(this.formValidate.begin_time),
                        end_time:  this.format_time(this.formValidate.end_time),
                        job_detail: this.formValidate.job_detail,
                        need_gender: this.gender_need,
                        need_amount: this.formValidate.need_amount,
                        begin_apply_time: this.format_date(this.formValidate.begin_apply_date,  this.formValidate.begin_apply_time),
                        end_apply_time:  this.format_date(this.formValidate.end_apply_date,  this.formValidate.end_apply_time),
                        education: this.formValidate.education[0],
                        tag_list: this.formValidate.job_tag,
                        salary: this.formValidate.salary
                    }
                }).then(response => {
                    console.log(response.data);
                    if (response.status === 200) {
                        this.$Message.success('发布岗位成功');
                        this.handleReset('formValidate')
                    }
                })
                    .catch(error => {
                        if (error.response) {
                            if (error.response.data.status === 400) {
                                this.$Message.error("发布岗位失败")
                            } else if (error.response.data.status === 401 && error.response.data.message === "Forbidden, invalid access token.") {
                                this.$Message.success('请登录');
                            }
                        }
                        JSON.stringify(error);
                        console.log(error)
                    })

            },
            startTimeChange: function (e) { //设置工作开始时间
                this.starttime = e;
                this.endTimeOptions = {
                    disabledDate: date => {
                        let startTime = this.starttime ? new Date(this.starttime).valueOf() : '';
                        return date && (date.valueOf() < startTime);
                    }
                }

                if(this.formValidate.end_apply_date > this.starttime)
                {
                    this.formValidate.begin_date=""
                    this.formValidate.end_date=""
                    this.$message.warning("工作开始日期不能比招聘结束时间早")
                }

            },
            endTimeChange: function (e) { //设置工作结束时间
                this.endtime = e;
                let endTime = this.endtime ? new Date(this.endtime).valueOf() -  24 * 60 * 60 * 1000 : '';
                this.startTimeOptions = {
                    disabledDate(date) {
                        return date && date.valueOf() > endTime;
                    }

                }
            },

            startTimeChange2: function (e) { //设置招聘开始时间
                this.starttime = e;
                this.endTimeOptions2 = {
                    disabledDate: date => {
                        let startTime = this.starttime ? new Date(this.starttime).valueOf() : '';
                        return date && (date.valueOf() < startTime);
                    }
                }
            },
            endTimeChange2: function (e) { //设置招聘结束时间
                this.endtime = e;
                let endTime = this.endtime ? new Date(this.endtime).valueOf() -  24 * 60 * 60 * 1000 : '';
                this.startTimeOptions2 = {
                    disabledDate(date) {
                        return date && date.valueOf() > endTime;
                    }

                }
                //检验工作开始时间不能比招聘结束时间早
                if(this.formValidate.begin_date< endTime)
                {
                    this.formValidate.begin_date=""
                    this.formValidate.end_date=""

                    this.startTimeOptions= {}, //开始日期设置
                        this.endTimeOptions={}, //结束日期设置
                        this.$message.warning("工作开始日期不能比招聘结束时间早")
                }

            }
        }
    }

</script>

<style scoped>
    .content{
        padding-top:20px;
        padding-left:200px;
        background-color: #fff;
    }

    .ivu-btn {
        color: #fff;
        background-color: #82ccd2;
        border-color: #c8d6e5;
    }


</style>
