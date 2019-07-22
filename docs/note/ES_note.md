+ [2.0.X中文文档](https://www.elastic.co/guide/cn/elasticsearch/guide/current/index.html)
+ min_score
+ Suggester
+ 一些中文术语[link](https://blog.csdn.net/lilongsy/article/details/70058979)
+ 一个更通俗中文版的链接[link](https://cloud.tencent.com/developer/article/1436464)
+ 中文ik分词器[link](https://github.com/medcl/elasticsearch-analysis-ik)

[TOC]

+ API中可以设置多个索引
  + 支持使用简单`test1,test2,test3`表示法（或`_all`所有索引）
  + 它还支持通配符，例如：`test*`或`*test`或`te*t`或`*test*`
  + 以及“排除”（`-`）的能力，例如：`test*,-test3`



Date format

A date math index name takes the following form:

```txt
<static_name{date_math_expr{date_format|time_zone}}>
```

Where:

| `static_name`    | is the static text part of the name                          |
| ---------------- | ------------------------------------------------------------ |
| `date_math_expr` | is a dynamic date math expression that computes the date dynamically |
| `date_format`    | is the optional format in which the computed date should be rendered. Defaults to `yyyy.MM.dd`. Format should be compatible with java-time <https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html> |
| `time_zone`      | is the optional time zone. Defaults to `utc`.                |

**您必须将日期数学索引名称表达式括在尖括号内，并且所有特殊字符都应进行URI编码**

## ES with docker

<https://www.elastic.co/guide/en/elasticsearch/reference/current/docker.html>


## index API
The following example inserts the JSON document into the "twitter" index with an id of 1:

```console
PUT twitter/_doc/1
{
    "user" : "kimchy",
    "post_date" : "2009-11-15T14:12:12",
    "message" : "trying out Elasticsearch"
}
```

The result of the above index operation is:

```js
{
    "_shards" : {
        "total" : 2,
        "failed" : 0,
        "successful" : 2
    },
    "_index" : "twitter",
    "_type" : "_doc",
    "_id" : "1",
    "_version" : 1,
    "_seq_no" : 0,
    "_primary_term" : 1,
    "result" : "created"
}
```

### Hints

+ The index operation also accepts an `op_type` that can be used to force a `create` operation, allowing for "put-if-absent" behavior. When `create` is used, the index operation will fail if a document by that id already exists in the index.Like `PUT twitter/_doc/1?op_type=create`
+ Use `POST twitter / _doc / ` for **Automatic ID Generation**

## GET API

The get API allows to get a JSON document from the index based on its id. The following example gets a JSON document from an index called twitter with id valued 0:

```console
GET twitter/_doc/0
```

The result of the above get operation is:

```js
{
    "_index" : "twitter",
    "_type" : "_doc",
    "_id" : "0",
    "_version" : 1,
    "_seq_no" : 10,
    "_primary_term" : 1,
    "found": true,
    "_source" : {
        "user" : "kimchy",
        "date" : "2009-11-15T14:12:12",
        "likes": 0,
        "message" : "trying out Elasticsearch"
    }
}
```

The API also allows to check for the existence of a document using `HEAD`, for example:

```console
HEAD twitter/_doc/0
```

### Hints
+ you can use the `_source_includes`and `_source_excludes` parameters to include or filter out the parts you need

## DELETE API

The delete API allows to delete a JSON document from a specific index based on its id. The following example deletes the JSON document from an index called `twitter` with ID `1`:

```console
DELETE /twitter/_doc/1
```

The result of the above delete operation is:

```js
{
    "_shards" : {
        "total" : 2,
        "failed" : 0,
        "successful" : 2
    },
    "_index" : "twitter",
    "_type" : "_doc",
    "_id" : "1",
    "_version" : 2,
    "_primary_term": 1,
    "_seq_no": 5,
    "result": "deleted"
}
```

### DELETE BY QUERY

The simplest usage of `_delete_by_query` just performs a deletion on every document that matches a query. Here is the API:

```console
POST twitter/_delete_by_query
{
  "query": { 
    "match": {
      "message": "some message"
    }
  }
}
```

## UPDATE API

Update API can be done like `Index API`:

```console
PUT test/_doc/1
{
    "counter" : 1,
    "tags" : ["red"]
}
```

The following partial update adds a new field to the existing document:

```console
POST test/_update/1
{
    "doc" : {
        "name" : "new_name"
    }
}
```

### Scrpited updates

Now, we can execute a script that would increment the counter:

```console
POST test/_update/1
{
    "script" : {
        "source": "ctx._source.counter += params.count",
        "lang": "painless",
        "params" : {
            "count" : 4
        }
    }
}
```

We can add a tag to the list of tags (if the tag exists, it still gets added, since this is a list):

```console
POST test/_update/1
{
    "script" : {
        "source": "ctx._source.tags.add(params.tag)",
        "lang": "painless",
        "params" : {
            "tag" : "blue"
        }
    }
}
```

### Update By Query

[click](https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html)

## Search

The query can either be provided using a simple [query string as a parameter](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-uri-request.html), or using a [request body](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-body.html).

### URI search

  ```console
  GET twitter/_search?q=user:kimchy
  ```

### Request Body Search

  ```console
  GET /twitter/_search
  {
      "query" : {
          "term" : { "user" : "kimchy" }
      }
  }
  ```

#### Doc value Fields

Allows to return the [doc value](https://www.elastic.co/guide/en/elasticsearch/reference/current/doc-values.html) representation of a field for each hit, for example:

```console
GET /_search
{
    "query" : {
        "match_all": {}
    },
    "docvalue_fields" : [
        "my_ip_field", 
        {
            "field": "my_keyword_field" 
        },
        {
            "field": "my_date_field",
            "format": "epoch_millis" 
        }
    ]
}
```


#### Explain

Enables explanation for each hit on how its score was computed.

```console
GET /_search
{
    "explain": true,
    "query" : {
        "term" : { "user" : "kimchy" }
    }
}
```

#### Field Collapsing

Allows to collapse search results based on field values. The collapsing is done by selecting only the top sorted document per collapse key. 

[click](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-request-collapse.html)

#### Pagination

Though `from` and `size` can be set as request parameters, they can also be set within the search body. `from` defaults to `0`, and `size` defaults to `10`.

```console
GET /_search
{
    "from" : 0, "size" : 10,
    "query" : {
        "term" : { "user" : "kimchy" }
    }
}
```

#### min_score

Exclude documents which have a `_score` less than the minimum specified in `min_score`:

```console
GET /_search
{
    "min_score": 0.5,
    "query" : {
        "term" : { "user" : "kimchy" }
    }
}
```

### Suggesters

In the example below two suggestions are requested. Both `my-suggest-1` and `my-suggest-2` suggestions use the `term` suggester, but have a different `text`.

```console
POST _search
{
  "suggest": {
    "my-suggest-1" : {
      "text" : "tring out Elasticsearch",
      "term" : {
        "field" : "message"
      }
    },
    "my-suggest-2" : {
      "text" : "kmichy",
      "term" : {
        "field" : "user"
      }
    }
  }
}
```

The below suggest response example includes the suggestion response for `my-suggest-1` and `my-suggest-2`. Each suggestion part contains entries. Each entry is effectively a token from the suggest text and contains the suggestion entry text, the original start offset and length in the suggest text and if found an arbitrary number of options.

```json
{
  "_shards": ...
  "hits": ...
  "took": 2,
  "timed_out": false,
  "suggest": {
    "my-suggest-1": [ {
      "text": "tring",
      "offset": 0,
      "length": 5,
      "options": [ {"text": "trying", "score": 0.8, "freq": 1 } ]
    }, {
      "text": "out",
      "offset": 6,
      "length": 3,
      "options": []
    }, {
      "text": "elasticsearch",
      "offset": 10,
      "length": 13,
      "options": []
    } ],
    "my-suggest-2": ...
  }
}
```

#### Suggester选项

常见建议选项：

| `text`         | 建议文字。建议文本是必需的选项，需要全局或按建议设置。       |
| -------------- | ------------------------------------------------------------ |
| `field`        | 从中获取候选建议的字段。这是一个必需的选项，需要全局设置或根据建议设置。 |
| `analyzer`     | 用于分析建议文本的分析器。默认为建议字段的搜索分析器。       |
| `size`         | 每个建议文本标记返回的最大更正。                             |
| `sort`         | 定义如何根据建议文本术语对建议进行排序。两个可能的值：`score`：先按分数排序，然后按文档频率排序，再按术语本身排序。`frequency`：首先按文档频率排序，然后按相似性分数排序，然后按术语本身排序。 |
| `suggest_mode` | 建议模式控制包含哪些建议或控制建议的文本术语，建议应该提出建议。可以指定三个可能的值：`missing`：仅提供不在索引中的建议文本术语的建议。这是默认值。`popular`：仅建议在比原始建议文本术语更多的文档中出现的建议。`always`：根据建议文本中的条款建议任何匹配的建议。 |

#### Phrase Suggester

an example: [click](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters-phrase.html)

#### Context Suggester

an example: [click](https://www.elastic.co/guide/en/elasticsearch/reference/current/suggester-context.html)

## 一些过滤器

+ term过滤器——精确值查找
+ bool过滤器——组合值查找
  + 可以嵌套
+ range过滤器——范围查找
+ exists过滤器/missing过滤器
+ match过滤器——匹配查询
  + 全文搜索的核心
+ dis_max查询——分离最大化查询（Disjunction Max Query）
  + 指的是： *将任何与任一查询匹配的文档作为结果返回，但只将最佳匹配的评分作为查询的评分结果返回*
  + 指定 `tie_breaker` 这个参数将其他匹配语句的评分也考虑其中
+ match_phrase过滤器——短语匹配查询
  + 也可以通过在match中设置type字段来实现相同功能
  + `slop` 参数告诉 `match_phrase` 查询词条相隔多远时仍然能将文档视为匹配
+ 部分匹配
  + 用占位符*和%来实现
  + query中添加`prefix`字段来实现前缀查询
  + `wildcard`实现正则表达式查询

### match查询

```js
GET /my_index/my_type/_search
{
    "query": {
        "match": {
            "title": "QUICK!"
        }
    }
}
```

Elasticsearch 执行上面这个 `match` 查询的步骤是：

1. *检查字段类型* 。

   标题 `title` 字段是一个 `string` 类型（ `analyzed` ）已分析的全文字段，这意味着查询字符串本身也应该被分析。

2. *分析查询字符串* 。

   将查询的字符串 `QUICK!` 传入标准分析器中，输出的结果是单个项 `quick` 。因为只有一个单词项，所以 `match` 查询执行的是单个底层 `term` 查询。

3. *查找匹配文档* 。

   用 `term` 查询在倒排索引中查找 `quick` 然后获取一组包含该项的文档，本例的结果是文档：1、2 和 3 。

4. *为每个文档评分* 。

   用 `term` 查询计算每个文档相关度评分 `_score` ，这是种将 词频（term frequency，即词 `quick`在相关文档的 `title` 字段中出现的频率）和反向文档频率（inverse document frequency，即词 `quick` 在所有文档的 `title` 字段中出现的频率），以及字段的长度（即字段越短相关度越高）相结合的计算方式。参见 [相关性的介绍](https://www.elastic.co/guide/cn/elasticsearch/guide/current/relevance-intro.html) 。

#### 一些可能有用的点

+ 多词查询（同样通过`match`实现），各个词之间是or的关系，通过加字段`"operator": "and"`来改为and关系

+ 字段`"minimum_should_match"`用于控制精度

+ 字段`boost`默认值为1，调整这个字段的值可以调整权重

+ `PUT /my_index/_mapping`来使用ik分析器，从而支持解析中文

  + json内容示例

    ```
    POST /{index_name}/_mapping
    {
      "properties": {
          "{content_name}": {
              "type": "text",
              "analyzer": "ik_max_word",
              "search_analyzer": "ik_smart"
          }
      }
    }
    ```

    

