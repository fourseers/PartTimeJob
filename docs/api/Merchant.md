###### 商家添加店铺接口

请求格式：POST

返回格式：JSON

接口名称：/merchant/shop

请求参数：

| 参数名     | 必须 | 类型   | 描述         |
| ---------- | ---- | ------ | ------------ |
| shop_name  | 是   | String | 店铺名称     |
| province   | 是   | String | 店铺所在省份 |
| city       | 是   | String | 店铺所在城市 |
| address    | 是   | String | 店铺地址     |
| longitude  | 是   | Float  | 经度         |
| latitude   | 是   | Float  | 纬度         |
| brand      | 是   | String | 品牌         |
| industry   | 是   | String | 营业领域     |
| shop_intro | 是   | String | 店铺介绍     |

返回结果：

{
   "state": 200,
   "data":{
   },
   "message":"success"
} 

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |



###### 商家获取全部店铺接口

请求格式：GET

返回格式：JSON

接口名称：/merchant/shop

请求参数：

无 

返回结果：

```json
{
   "state": 200,
   "data":{
   "shops":[
         {
                "shop_id": 1,
​               "shop_name": "yidiandian",
​               "shop_introduction": "really nice tea",
​               "shop_province": "Shanghai",
​				"shop_city": "Shanghai",
​			    "latitude": 121.48,
​				"longitude":31.22,
​               "brand": "yidiandian",
​               "industry": "Drinks",
​               "address": "Dongchuanlu 20hao"
 			}
 	]

   },
   "message":"success"
}
```

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |



###### 商家获取单个店铺接口

请求格式：GET

返回格式：JSON

接口名称：/merchant/shop/shop_id

请求参数：

| 参数名  | 必须 | 类型     | 描述   |
| ------- | ---- | -------- | ------ |
| shop_id | 是   | Interger | 店铺ID |

返回结果：

```json
{
   "state": 200,
   "data":{
   			"shop":
         		{
                "shop_id": 1,
​               "shop_name": "yidiandian",
​               "shop_introduction": "really nice tea",
​               "shop_province": "Shanghai",
​				"shop_city": "Shanghai",
​			    "latitude": 121.48,
​				"longitude":31.22,
​               "brand": "yidiandian",
​               "industry": "Drinks",
​               "address": "Dongchuanlu 20hao"
 					}
   },
   "message":"success"
}
```

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |



###### 商家发布岗位接口

请求格式：POST

返回格式：JSON

接口名称：/merchant/job

请求参数：

| 参数名           | 必须 | 类型     | 描述                                                         |
| ---------------- | ---- | -------- | ------------------------------------------------------------ |
| shop_id          | 是   | Interger | 店铺ID                                                       |
| job_name         | 是   | String   | 岗位名称                                                     |
| begin_date       | 是   | String   | 上班开始日期 格式  Wed Jul 17 2019 00:00:00 GMT+0800 (中国标准时间) |
| end_date         | 是   | String   | 上班结束日期 格式 Wed Jul 17 2019 00:00:00 GMT+0800 (中国标准时间) |
| job_detail       | 是   | String   | 岗位描述                                                     |
| need_gender      | 是   | Interger | 0表示男 1 表示女 2表示男女皆可                               |
| need_amount      | 是   | Interger | 招聘数量                                                     |
| begin_apply_date | 是   | String   | 招聘开始日期 格式  Wed Jul 17 2019 00:00:00 GMT+0800 (中国标准时间) |
| end_apply_date   | 是   | String   | 招聘结束日期 格式 Wed Jul 17 2019 00:00:00 GMT+0800 (中国标准时间) |
| Education        | 是   | String   | 要求的学历                                                   |
| tag              | 是   | String[] | tag列表                                                      |
| salary           | 是   | Double   | 日薪                                                         |

返回结果：

{
   "state": 200,
   "data":{
   },
   "message":"success"
} 

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |



###### 商家获取全部岗位接口

请求格式：GET

返回格式：JSON

接口名称：/merchant/job

请求参数：

无


返回结果：

~~~json
{
   "state":200,
   "data":{
      jobs:[
         { 
            "job_id" :1,
            "job_name" :"milktea maker",
            "job_detail" : "make milk tea",
            "begin_date": "2019-07-02",
            "end_date" :"2019-08-02",
            "need_amount" : 10,
            "begin_apply_date": "2019-06-02",
            "end_apply_date": "2019-07-01",
            "salary" : 100.00,
            "create_time": "2019-04-02",
            "need_gender": 2,
            "Education": "初中",
     		} 
 		 ]
   },
   "message":"success"
}
~~~

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |

###### 商家获取单个岗位接口

请求格式：GET

返回格式：JSON

接口名称：/merchant/job/job_id

请求参数：

| 参数名  | 必须 | 类型     | 描述   |
| ------- | ---- | -------- | ------ |
| shop_id | 是   | Interger | 店铺ID |
| job_id  | 是   | Interger | 岗位ID |

返回结果：

```json
{
   "state":200,
   "data":{
      jobs:[
         { 
            "job_id" :1,
            "job_name" :"milktea maker",
            "job_detail" : "make milk tea",
            "begin_date": "2019-07-02",
            "end_date" :"2019-08-02",
            "need_amount" : 10,
            "begin_apply_date": "2019-06-02",
            "end_apply_date": "2019-07-01",
            "salary" : 100.00,
            "create_time": "2019-04-02",
            "need_gender": 2,
            "Education": "初中",
     		} 
 		 ]
   },
   "message":"success"
}
```

字段说明：

| 字段  | 类型 | 描述                     |
| ----- | ---- | ------------------------ |
| state | int  | 状态码，成功200，失败400 |