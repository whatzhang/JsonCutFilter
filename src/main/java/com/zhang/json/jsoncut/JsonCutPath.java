package com.zhang.json.jsoncut;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhang
 */
@Slf4j
public class JsonCutPath {

    /**
     * 根据jsonpath表达式删除json分支
     *
     * @param target 目标json
     * @param rules  要删除的jsonpath表达式
     * @return 最终数据
     * @throws IOException IOException
     */
    public static String cutJsonPath(String target, List<String> rules) throws IOException {
        if (Objects.isNull(rules) || rules.isEmpty()) {
            return target;
        }
        //根据删除json的jsonpath剪切json
        DocumentContext doc = JsonPath.parse(JSONUtil.parseObj(target));
        for (String rule : rules) {
            doc.delete(rule, new Predicate[0]);
            log.debug("jsonPath delete path=[{}]", rule);
        }
        return doc.jsonString();
    }

    /**
     * 根据json获取自定义裁减规则
     *
     * @param json json数据
     * @return 裁减规则
     */
    public static String getRules(String json) {
        JSONObject jsonObj = JSONUtil.parseObj(json);
        StringBuffer sb = new StringBuffer();
        jsonLoop(sb, jsonObj);
        if (sb.length() > 0) {
            return sb.toString().replace(",)", ")").replace("(,", "(").replaceAll("^\\(|\\)$", "");
        }
        return null;
    }

    private static void jsonLoop(StringBuffer sb, Object object) {
        if (Objects.isNull(object)) {
            return;
        }
        if (object instanceof JSONObject) {
            JSONObject jo = (JSONObject) object;
            if (Objects.nonNull(jo) && jo.entrySet().size() > 0) {
                sb.append("(");
                for (Map.Entry<String, Object> entry : jo.entrySet()) {
                    String k = entry.getKey();
                    Object v = entry.getValue();
                    sb.append(",").append(k);
                    if (v instanceof JSONArray) {
                        jsonLoop(sb, v);
                    } else if (v instanceof JSONObject) {
                        jsonLoop(sb, v);
                    } else {
                        log.debug("[" + k + "]:" + v);
                    }
                }
                sb.append(")");
            }
        } else if (object instanceof JSONArray) {
            JSONArray ja = (JSONArray) object;
            if (ja.size() > 0) {
                jsonLoop(sb, ja.get(0));
            }
        } else {
            log.warn("get filter json rules illegal, object=[{}]", object);
        }
    }

}
