- 接口统一地址： <http://localhost:8079/api>（暂定）

- 商家注册接口
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
       "code":200,
       "data":{
          "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
        "token_type":"bearer",
          "expires_in":3600,
        "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk"
       },
       "message":"success"
    }
    ```
  
  - 字段说明：
  
    | 字段            | 类型     | 描述                     |
  | --------------- | -------- | ------------------------ |
    | `code`          | `int`    | 状态码，成功200，失败400 |
  | `access_token`  | `String` | OAuth令牌                |
    | `token_type`    | `String` | Token类型，总是为Bearer   |
    | `expire_in`     | `int`    | 令牌过期时间             |
    | `refresh_token` | `String` | 更新令牌                 |
    | `message`       | `String` | 注册信息                 |
  
    
  
- 商家登录接口

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
       "code":200,
       "data":{
          "access_token":"MTQ0NjJkZmQ5OTM2NDE1ZTZjNGZmZjI3",
          "token_type":"bearer",
          "expires_in":3600,
          "refresh_token":"IwOGYzYTlmM2YxOTQ5MGE3YmNmMDFkNTVk"
       },
       "message":"success"
    }
    
    ```

  - 字段说明：
    
    | 字段            | 类型     | 描述                     |
    | --------------- | -------- | ------------------------ |
    | `code`          | `int`    | 状态码，成功200，失败400 |
    | `access_token`  | `String` | OAuth令牌                |
    | `token_type`    | `String` | Token类型，总是为Bearer   |
    | `expire_in`     | `int`    | 令牌过期时间             |
    | `refresh_token` | `String` | 更新令牌                 |
    | `message`       | `String` | 登录信息                 |
