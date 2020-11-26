# JsonCutFilter
json数据剪切过滤
# 	
```json
{
    "aa": {
        "aaa": "aaa",
        "bbb": "bbb",
        "ccc": "ccc"
    },
    "bb": {
        "aaa": "aaa",
        "bbb": "bbb",
        "ccc": "ccc"
    }
}
```
裁减过滤后
```json
{
    "aa": {
        "aaa": "aaa"
    },
    "bb": {
        "aaa": "aaa",
        "bbb": "bbb"
    }
}
```
代码示例
```java
 String rule = "aa(aaa),bb(aaa,bbb)";
 String rtn = jsonFilter.filterInclude(target, rule);
 String rule1 = "aa(bbb,ccc),bb(ccc)";
 String rtn = jsonFilter.filterExclude(target, rule1);
```
jsonFilter原地址： https://github.com/json-path/JsonPath
我的博客：https://blog.csdn.net/whatzhang007/article/details/110178015
