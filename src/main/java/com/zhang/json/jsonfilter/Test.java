package com.zhang.json.jsonfilter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhang.json.jsoncut.JsonCutPath;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.StopWatch;

import java.io.IOException;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author zhang
 */
@Slf4j
public class Test {
    private static final JsonFilter jsonFilter = new JsonFilter();

    private static String cutResult1 = null;

    private static String cutResult2 = null;

    private static String cutResult3 = null;

    public static void main(String[] args) throws Exception {
        //自己建大json
        String json = "{\"name\":\"zhang\",\"age\":18,\"address\":{\"province\":\"江苏\",\"city\":\"南京\"}}";
        JSONObject ob = new JSONObject();
        for (int i = 0; i < 450; i++) {
            ob.put(String.valueOf(i), JSONUtil.parseObj(json));
        }
        json = JSONUtil.toJsonStr(ob);
        String rules = JsonCutPath.getRules(json);
        log.info(rules);

        int thread = 2;
        int forNum = 100;
        //过滤包含1
        String rule = "name,age";
        testJsonFilterInclude(json, thread, forNum, rules);
        log.info(cutResult1);
        //过滤不包含2
        String rule1 = "name,age";
        testJsonFilterExclude(json, thread, forNum, rule1);
        //jsonPath裁减删除
        List<String> ruleList = Arrays.asList("$.name", "$.age");
        tesJsonPathCut(json, thread, forNum, ruleList);


        Gson gson = new GsonBuilder().create();
        System.out.println("cutResult1==" + cutResult1);
        System.out.println("cutResult2==" + cutResult2);
        System.out.println(gson.toJsonTree(cutResult1).equals(gson.toJsonTree(cutResult2)));


        System.out.println("cutResult1==" + cutResult1);
        System.out.println("cutResult3==" + cutResult3);
        System.out.println(gson.toJsonTree(cutResult1).equals(gson.toJsonTree(cutResult3)));


        System.out.println("cutResult2==" + cutResult2);
        System.out.println("cutResult3==" + cutResult3);
        System.out.println(gson.toJsonTree(cutResult2).equals(gson.toJsonTree(cutResult3)));


    }

    private static void testJsonFilterInclude(String json, int thread, int forNum, String rule) throws InterruptedException {
        String target = json;
        List<Double> times = new CopyOnWriteArrayList<>();
        final CountDownLatch latch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            int finalI = i;
            new Thread(() -> {
                IntStream.range(0, forNum).forEach(o -> {
                    StopWatch wh = new StopWatch();
                    wh.start();
                    try {
                        String rtn = jsonFilter.filterInclude(target, rule);
                        if (finalI == 0 && o == 0) {
                            cutResult1 = rtn;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    wh.stop();
                    times.add(wh.getTotalTimeSeconds());
                });
                latch.countDown();
            }).start();
        }
        latch.await();
        DoubleSummaryStatistics collect = times.stream().collect(Collectors.summarizingDouble(value -> value));
        log.info("*************统计信息=[{}]", collect);
    }

    private static void testJsonFilterExclude(String json, int thread, int forNum, String rule) throws InterruptedException {
        String target = json + "";
        List<Double> times = new CopyOnWriteArrayList<>();
        final CountDownLatch latch = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            int finalI = i;
            new Thread(() -> {
                IntStream.range(0, forNum).forEach(o -> {
                    StopWatch wh = new StopWatch();
                    wh.start();
                    try {
                        String rtn = jsonFilter.filterExclude(target, rule);
                        if (finalI == 0 && o == 0) {
                            cutResult2 = rtn;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    wh.stop();
                    times.add(wh.getTotalTimeSeconds());
                });
                latch.countDown();
            }).start();
        }
        latch.await();
        DoubleSummaryStatistics collect = times.stream().collect(Collectors.summarizingDouble(value -> value));
        log.info("*************统计信息=[{}]", collect);
    }

    private static void tesJsonPathCut(String json, int thread, int forNum, List<String> rules) throws InterruptedException {
        String target2 = json + "";
        List<Double> times2 = new CopyOnWriteArrayList<>();
        final CountDownLatch latch2 = new CountDownLatch(thread);
        for (int i = 0; i < thread; i++) {
            int finalI = i;
            new Thread(() -> {
                IntStream.range(0, forNum).forEach(o -> {
                    StopWatch wh = new StopWatch();
                    wh.start();
                    try {
                        String rtn = JsonCutPath.cutJsonPath(target2, rules);
                        if (finalI == 0 && o == 0) {
                            cutResult3 = rtn;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    wh.stop();
                    times2.add(wh.getTotalTimeSeconds());
                });
                latch2.countDown();
            }).start();
        }
        latch2.await();
        DoubleSummaryStatistics collect2 = times2.stream().collect(Collectors.summarizingDouble(value -> value));
        log.info("*************统计信息=[{}]", collect2);
    }

}
