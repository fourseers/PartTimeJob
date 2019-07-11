# 验证与鉴权接口定义

> * 测试环境地址：http://GATEWAY_DOMAIN:30553
> * 接口统一前缀：`/auth`
> * 状态码统一规定：
>
> | 状态码 | 描述     | 可能原因                                  |
> | ------ | -------- | ----------------------------------------- |
> | 200    | 成功     | /                                         |
> | 400    | 请求无效 | 用户名或密码错误                          |
> | 401    | 未授权   | 客户端鉴权字段（Basic Authorization）错误 |
> | 500    | 服务器   | 服务器内部错误，请联系管理员              |

[TOC]

## 商家注册

- 请求格式：POST

- 返回格式：JSON

- 接口名称：`/merchant/register`

- 请求参数：

  | 参数名     | 必须 | 类型     | 描述   |
  | ---------- | ---- | -------- | ------ |
  | `username` | 是   | `String` | 用户名 |
  | `password` | 是   | `String` | 密码   |

- 返回结果：

  ```json
  {
     "status":200,
     "data":{
        "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
        "token_type":"bearer",
        "expires_in":3600,
        "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
         "scope": "server"
     },
     "message":"success"
  }
  ```

- 字段说明：

  | 字段            | 类型     | 描述                     |
  | --------------- | -------- | ------------------------ |
  | `status`    | `int`    | 状态码 |
  | `access_token`  | `String` | OAuth令牌                |
  | `token_type`    | `String` | Token类型，总是为Bearer   |
  | `expire_in`     | `int`    | 令牌过期时间             |
  | `refresh_token` | `String` | 更新令牌                 |
  | `message`       | `String` | 注册信息                 |
  | `scope`         | `String` | 待定                     |


## 商家登录

- 请求格式：POST

- 返回格式：JSON

- 接口名称：`/merchant/login`

- 请求参数：

  | 参数名     | 必须 | 类型     | 描述   |
  | ---------- | ---- | -------- | ------ |
  | `username` | 是   | `String` | 用户名 |
  | `password` | 是   | `String` | 密码   |

- 返回结果：

  ```json
  {
     "status":200,
     "data":{
        "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
        "token_type":"bearer",
        "expires_in":3600,
        "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
         "scope": "server"
     },
     "message":"success"
  }
  
  ```

- 字段说明：
  
  | 字段            | 类型     | 描述                     |
  | --------------- | -------- | ------------------------ |
  | `status`    | `int`    | 状态码 |
  | `access_token`  | `String` | OAuth令牌                |
  | `token_type`    | `String` | Token类型，总是为Bearer   |
  | `expire_in`     | `int`    | 令牌过期时间             |
  | `refresh_token` | `String` | 更新令牌                 |
  | `message`       | `String` | 登录信息                 |
  | `scope`         | `String` | 待定                     |

## 用户注册

- 请求格式：POST

- 返回格式：JSON

- 接口名称：`/wechat/register`

- 请求参数：

  | 参数名      | 必须 | 类型      | 描述                      |
  | ----------- | ---- | --------- | ------------------------- |
  | `token`     | 是   | `String`  | 前端从微信获取的临时口令     |
  | `name`      | 是   | `String`  | 姓名                      |
  | `gender`    | 是   | `Boolean` | 性别，0为女，1为男        |
  | `identity`  | 是   | `String`  | 身份证号，末位为X的用大写 |
  | `phone`     | 是   | `String`  | 手机号                    |
  | `country`   | 是   | `String`  | 国家                      |
  | `city`      | 是   | `String`  | 城市                      |
  | `education` | 是   | `String`  | 文化水平                  |
  | `tags` | 是 | `ArrayList<String>` | 用户选择的所有技术tag.属性包括id和名称等 |

- 返回结果：
  
    ```json
    {
       "status":200,
       "data":{
          "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
          "token_type":"bearer",
          "expires_in":3600,
          "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
           "scope": "server"
       },
       "message":"success"
  }
  ```
  
- 字段说明：
  
    | 字段            | 类型     | 描述                     |
    | --------------- | -------- | ------------------------ |
    | `status`        | `int`    | 状态码，成功200，失败400 |
    | `access_token`  | `String` | OAuth令牌                |
    | `token_type`    | `String` | Token类型，总是为Bearer  |
    | `expire_in`     | `int`    | 令牌过期时间             |
    | `refresh_token` | `String` | 更新令牌                 |
    | `message`       | `String` | 注册信息                 |
    | `scope`         | `String` | 待定                     |

## 用户登录

- 请求格式：POST

- 返回格式：JSON

- 接口名称：`/wechat/login`

- 请求参数：

  | 参数名  | 必须 | 类型     | 描述                     |
  | ------- | ---- | -------- | ------------------------ |
  | `token` | 是   | `String` | 前端从微信获取的临时口令 |

- 返回结果：

  ```json
  {
     "status":200,
     "data":{
        "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
        "token_type":"bearer",
        "expires_in":3600,
      	"refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk",
         "scope": "server"
     },
     "message":"success"
  }
  ```

- 字段说明：

  | 字段            | 类型     | 描述                    |
  | --------------- | -------- | ----------------------- |
  | `status`        | `int`    | 状态码                  |
  | `access_token`  | `String` | OAuth令牌               |
  | `token_type`    | `String` | Token类型，总是为Bearer |
  | `expire_in`     | `int`    | 令牌过期时间            |
  | `refresh_token` | `String` | 更新令牌                |
  | `message`       | `String` | 注册信息                |
  | `scope`         | `String` | 待定                    |
