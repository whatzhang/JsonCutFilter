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
        String rule = "MSG,CODE,ENT_INFO(ORGDETAIL,YEARREPORTBASIC(BUSST,EMAIL,WOMEMPNUM,ANCHEID,ADDR,HOLDINGSMSG_CN,REGNO,CREDITNO,MAINBUSIACT,TEL,ENTNAME),MORTGAGECAN,QUICKCANCELDISSENT,YEARREPORTWEBSITEINFO,ENT_HANGYEINFO(INDUSTRYPHY,INDUSTRYCOCODE,INDUSTRYPHYNAME,ENTNAME,INDUSTRYCONAME),YEARREPORTALTERSTOCK,MORDETAIL,YEARREPORTALTER(ALITEM,ALTDATE,ALTAF,ALTBE,ANCHEID),TRADEMARK,LISTEDMANAGER,ENTINV(CONGROCUR,ENTSTATUS,REGCAP,SUBCONAM,REGCAPCUR,FUNDEDRATIO,REVDATE,CREDITCODE,CANDATE,ENTTYPE,REGORG,NAME,REGORGCODE,INDUSTRYCOCODE,ESDATE,BINVVAMOUNT,REGNO,ENTID,CONFORM,INDUSTRYCONAME),LISTEDINFO,RELATEDPUNISHED(EMID,REGDATECLEAN,SEXYCLEAN,CASECODE,CASESTATE,EXECMONEY,FOCUSNUMBER,AGECLEAN,PALGORITHMID,ENTID,CARDNUMCLEAN,YSFZD,INAMECLEAN,AREANAMECLEAN,TYPE,COURTNAME),SHAREHOLDER(SUBCONAM,FUNDEDRATIO,INVTYPECODE,COUNTRY,CONFORMCODE,REGNO,RECORD_ID,CONFORM,IDENTITYCARDNT,CDID,EMID,REGCAPCUR,CREDITCODE,CONDATE,SHANAME,INVAMOUNT,SUMCONAM,INVTYPE,CURRENCYCODE,IDENTITY,ACCONAM,INVSUMFUNDEDRATIO,COUNTRYCODE,PALGORITHMID,PERSON_ID_BOCOM),PUNISHBREAK,PUNISHED(REGDATECLEAN,SEXYCLEAN,CASECODE,CASESTATE,EXECMONEY,FOCUSNUMBER,AGECLEAN,CARDNUMCLEAN,YSFZD,INAMECLEAN,AREANAMECLEAN,TYPE,COURTNAME),MORGUAINFO,PERSON(IDENTITYCARDNT,CDID,EMID,PERSONAMOUNT,SEX,SEXCODE,PERNAME,POSITION,PALGORITHMID,PERSON_ID_BOCOM,RECORD_ID,ENTNAME,POSITIONCODE,NATDATE),YEARREPORTFORINV(REGNO,CREDITNO,ENTNAME,ANCHEID),CASEINFO,STOCKPAWNALT,LIQUIDATION,JUDICIALAID,QUICKCANCELBASIC,YEARREPORTANASSETSINFO,RELATEDPUNISHBREAK(GISTUNIT,UNPERFORMPART,CARDNUM,PERFORMANCE,BUSINESSENTITY,GISTID,SEXYCLEAN,ENTID,PERFORMEDPART,YSFZD,DISRUPTTYPENAME,INAMECLEAN,AREANAMECLEAN,PUBLISHDATECLEAN,TYPE,EMID,REGDATECLEAN,EXITDATE,CASECODE,CASESTATE,FOCUSNUMBER,AGECLEAN,DUTY,PALGORITHMID,COURTNAME),STOCKPAWNREV,LISTEDCOMPINFO,ENT_ZONGHEINFO(ANCHEYEAR,OPLOC,ABUITEM,INDUSTRYPHYALL,INDUSTRYPHYCODE,ANCHEDATE,DOMDISTRICT,REGORGCODE,OPSCOANDFORM,INDUSTRYPHY,INDUSTRYCOALL,INDUSTRYPHYNAME,CBUITEM,S_EXT_NODENUM,ENTNAME,RECCAP,ENTNAMEENG,REGORGPROVINCE,EMPNUM),ALTER(ALTDATE,ALTITEMCODE,ALTAF,RECORD_ID,ALTBE,ALTITEM),FILIATION,STOCKPAWN,MORTGAGEREG,FINALCASE,BASIC(CHANGEDATE,ABUITEM,INDUSTRYPHYALL,ENTSTATUS,INDUSTRYPHYCODE,REGORG,EMAIL,ENTITYTYPE,COUNTRY,REGNO,RECCAP,EMPNUM,REGCAPCURCODE,IDENTITYCARDNT,DOM,ENTTYPECODE,REGCAPCUR,CREDITCODE,IDENTITY,INDUSTRYCOCODE,OPSCOPE,ESDATE,COUNTRYCODE,PALGORITHMID,OPTO,CERTYPECODE,ENTNAME,UPDATEDATE,LOGOIMGNAME,COORDINATE,QRCODE_IMAGE_BASE64,INDUSTRYCO,CERTYPE,BAIDU_COORDINATE,CANDATE,ZSOPSCOPE,FRNAME,REGORGDISTRICT,REGORGCODE,OPSCOANDFORM,INDUSTRYCOALL,OTHERTEL,REGORGCITY,ENTID,CBUITEM,S_EXT_NODENUM,APPRDATE,REGCITY,OPFROM,ORIREGNO,ENTNAMEENG,ENTSTATUSCODE,INDUSTRYCONAME,ANCHEYEAR,POSTALCODE,OPLOC,EMID,REGCAP,ANCHEDATE,REVDATE,ENTTYPE,DOMDISTRICT,INDUSTRYPHY,INDUSTRYPHYNAME,PERSON_ID_BOCOM,ENTNAME_OLD,TEL,ORGCODES,TAX_CODE,REGORGPROVINCE),SHARESFROST,YEARREPORTFORGUARANTEE,MORTGAGEDEBT,MORTGAGEPAWN,YEARREPORTSOCSEC(SO310,TOTALWAGES_SO110,SO410,TOTALWAGES_SO210,SO110,TOTALWAGES_SO310,SO210,TOTALWAGES_SO410,ANCHEID,SO510,TOTALPAYMENT_SO110,UNPAIDSOCIALINS_SO410,UNPAIDSOCIALINS_SO510,TOTALWAGES_SO510,TOTALPAYMENT_SO410,UNPAIDSOCIALINS_SO210,TOTALPAYMENT_SO510,UNPAIDSOCIALINS_SO310,TOTALPAYMENT_SO210,TOTALPAYMENT_SO310,UNPAIDSOCIALINS_SO110),ENTCASEBASEINFO,MORTGAGEBASIC,DEALIN(ASSGRO,CURRENCY,ANCHEYEAR,SERVINC,BUSST,DEFICIT,NETINC,LTERMLIAAM,LIAGRO,LTERMINV,RATGRO,VENDINC,PROGRO),YEARREPORTSUBCAPITAL(INV,CURRENCY,LISUBCONAM,CONFORM,CONDATE,ANCHEID),SHARESIMPAWN,YEARREPORTPAIDUPCAPITAL(INV,CURRENCY,CONFORM,LIACCONAM,CONDATE,ANCHEID),LISTEDSHAREHOLDER,ORGBASIC,JUDICIALAIDALTER,JUDICIALAIDDETAIL,MORTGAGEPER,ENT_BIAOSHIINFO(ENTNAME,ORGCODES),EXCEPTIONLIST,METADATA(SOURCE),MORTGAGEALT)";
        testJsonFilterInclude(json, thread, forNum, rules);
        log.info(cutResult1);
        //过滤不包含2
        String rule1 = "ENT_INFO(ENTINV(ENTNAME),BREAKLAW,YEARREPORTBASIC(ANCHEYEAR,POSTALCODE,ANCHEDATE))";
        testJsonFilterExclude(json, thread, forNum, rule1);
        //jsonPath裁减删除
        List<String> ruleList = Arrays.asList("$.ENT_INFO.ENTINV[*].ENTNAME", "$.ENT_INFO.BREAKLAW", "$.ENT_INFO.YEARREPORTBASIC.[*].['ANCHEYEAR','POSTALCODE','ANCHEDATE']");
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
