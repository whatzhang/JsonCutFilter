package com.zhang.json.jsonfilter;

import ch.qos.logback.core.net.AutoFlushingObjectWriter;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StopWatch;

import java.io.File;
import java.io.FileWriter;
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
        String json = "{\n" +
                "  \"MSG\": \"OK\",\n" +
                "  \"CODE\": \"BIZ200\",\n" +
                "  \"ENT_INFO\": {\n" +
                "    \"ENTINV\": [\n" +
                "      {\n" +
                "        \"ENTNAME\": \"天津乖乖数位电子商务有限公司\",\n" +
                "        \"REGNO\": \"120118000032322\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(自然人投资或控股的法人独资)\",\n" +
                "        \"REGCAP\": \"1000.000000\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"市自贸区市场监管局\",\n" +
                "        \"SUBCONAM\": \"1000.000000\",\n" +
                "        \"CONGROCUR\": \"人民币元\",\n" +
                "        \"FUNDEDRATIO\": \"100.00%\",\n" +
                "        \"ESDATE\": \"2015-07-01\",\n" +
                "        \"NAME\": \"张贵富\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120118\",\n" +
                "        \"CREDITCODE\": \"91120118341040772F\",\n" +
                "        \"INDUSTRYCOCODE\": \"5292\",\n" +
                "        \"INDUSTRYCONAME\": \"互联网零售\",\n" +
                "        \"ENTID\": \"edf44df928988a7049f5ebca8c777bb2\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"REGNO\": \"120000400073350\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(中外合资)\",\n" +
                "        \"REGCAP\": \"3895.885400\",\n" +
                "        \"REGCAPCUR\": \"美元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"天津市滨海新区市场监督管理局\",\n" +
                "        \"SUBCONAM\": \"2898.538700\",\n" +
                "        \"CONGROCUR\": \"美元\",\n" +
                "        \"FUNDEDRATIO\": \"74.40%\",\n" +
                "        \"ESDATE\": \"1993-04-26\",\n" +
                "        \"NAME\": \"萧淑蕊\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120116\",\n" +
                "        \"CREDITCODE\": \"91120116600515788K\",\n" +
                "        \"INDUSTRYCOCODE\": \"1499\",\n" +
                "        \"INDUSTRYCONAME\": \"其他未列明食品制造\",\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"天津乖乖粮油有限公司\",\n" +
                "        \"REGNO\": \"120224000036617\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(外商投资企业投资)\",\n" +
                "        \"REGCAP\": \"550.000000\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"天津市宝坻区市场监督管理局\",\n" +
                "        \"SUBCONAM\": \"525.000000\",\n" +
                "        \"CONGROCUR\": \"人民币元\",\n" +
                "        \"FUNDEDRATIO\": \"95.45%\",\n" +
                "        \"ESDATE\": \"2008-11-20\",\n" +
                "        \"NAME\": \"萧淑蕊\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120224\",\n" +
                "        \"CREDITCODE\": \"91120224681868322N\",\n" +
                "        \"INDUSTRYCOCODE\": \"5121\",\n" +
                "        \"INDUSTRYCONAME\": \"米、面制品及食用油批发\",\n" +
                "        \"ENTID\": \"bac5c81cfbbb156cc6c1d6908a6d4318\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"天津嘉年华数位广告传媒有限公司\",\n" +
                "        \"REGNO\": \"120118000032339\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(自然人投资或控股的法人独资)\",\n" +
                "        \"REGCAP\": \"1000.000000\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"市自贸区市场监管局\",\n" +
                "        \"SUBCONAM\": \"1000.000000\",\n" +
                "        \"CONGROCUR\": \"人民币元\",\n" +
                "        \"FUNDEDRATIO\": \"100.00%\",\n" +
                "        \"ESDATE\": \"2015-07-01\",\n" +
                "        \"NAME\": \"张贵富\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120118\",\n" +
                "        \"CREDITCODE\": \"91120118341040756R\",\n" +
                "        \"INDUSTRYCOCODE\": \"7259\",\n" +
                "        \"INDUSTRYCONAME\": \"其他广告服务\",\n" +
                "        \"ENTID\": \"8e3378efb8de4965208b37146fe12989\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"天津嘉年华水处理设施制造有限公司\",\n" +
                "        \"REGNO\": \"120224000035358\",\n" +
                "        \"ENTTYPE\": \"有限责任公司\",\n" +
                "        \"REGCAP\": \"50.000000\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"ENTSTATUS\": \"注销\",\n" +
                "        \"REGORG\": \"天津市宝坻区市场监督管理局\",\n" +
                "        \"SUBCONAM\": \"25.000000\",\n" +
                "        \"CONGROCUR\": \"人民币元\",\n" +
                "        \"FUNDEDRATIO\": \"50.00%\",\n" +
                "        \"ESDATE\": \"2008-10-15\",\n" +
                "        \"NAME\": \"张贵富\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"2011-08-22\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"\",\n" +
                "        \"REGORGCODE\": \"120224\",\n" +
                "        \"CREDITCODE\": \"\",\n" +
                "        \"INDUSTRYCOCODE\": \"3531\",\n" +
                "        \"INDUSTRYCONAME\": \"食品、酒、饮料及茶生产专用设备制造\",\n" +
                "        \"ENTID\": \"b54d48b43acc3b203b847cc381a11a0f\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"乖乖嘉年华(天津)食品有限公司\",\n" +
                "        \"REGNO\": \"120000400038277\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(台港澳与境内合资)\",\n" +
                "        \"REGCAP\": \"2017.647000\",\n" +
                "        \"REGCAPCUR\": \"美元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"天津市宝坻区市场监督管理局\",\n" +
                "        \"SUBCONAM\": \"1008.823500\",\n" +
                "        \"CONGROCUR\": \"美元\",\n" +
                "        \"FUNDEDRATIO\": \"50.00%\",\n" +
                "        \"ESDATE\": \"2007-12-17\",\n" +
                "        \"NAME\": \"萧淑蕊\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120224\",\n" +
                "        \"CREDITCODE\": \"911202246688230745\",\n" +
                "        \"INDUSTRYCOCODE\": \"1441\",\n" +
                "        \"INDUSTRYCONAME\": \"液体乳制造\",\n" +
                "        \"ENTID\": \"a4355de9e42273b8fca16a314b6a70e9\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTNAME\": \"天津嘉年华远洋科技有限公司\",\n" +
                "        \"REGNO\": \"120107000052199\",\n" +
                "        \"ENTTYPE\": \"有限责任公司(自然人投资或控股的法人独资)\",\n" +
                "        \"REGCAP\": \"12000.000000\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"ENTSTATUS\": \"在营（开业）\",\n" +
                "        \"REGORG\": \"天津市滨海新区市场监督管理局\",\n" +
                "        \"SUBCONAM\": \"12000.000000\",\n" +
                "        \"CONGROCUR\": \"人民币元\",\n" +
                "        \"FUNDEDRATIO\": \"100.00%\",\n" +
                "        \"ESDATE\": \"2009-02-25\",\n" +
                "        \"NAME\": \"张凯翔\",\n" +
                "        \"BINVVAMOUNT\": \"7\",\n" +
                "        \"CANDATE\": \"\",\n" +
                "        \"REVDATE\": \"\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"REGORGCODE\": \"120116\",\n" +
                "        \"CREDITCODE\": \"911201166847202195\",\n" +
                "        \"INDUSTRYCOCODE\": \"7511\",\n" +
                "        \"INDUSTRYCONAME\": \"农林牧渔技术推广服务\",\n" +
                "        \"ENTID\": \"3d6c6f58e45b81874095984b69d761c3\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"BREAKLAW\": [],\n" +
                "    \"CASEINFO\": [],\n" +
                "    \"ORGBASIC\": [],\n" +
                "    \"PUNISHED\": {\n" +
                "      \"COURTNAME\": \"天津市南开区人民法院\",\n" +
                "      \"CASESTATE\": \"执行中\",\n" +
                "      \"CASECODE\": \"(2020)津0104执908号\",\n" +
                "      \"EXECMONEY\": \"27552226\",\n" +
                "      \"FOCUSNUMBER\": \"0\",\n" +
                "      \"TYPE\": \"法人或其他组织\",\n" +
                "      \"INAMECLEAN\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"CARDNUMCLEAN\": \"91120116663****4097\",\n" +
                "      \"SEXYCLEAN\": \"\",\n" +
                "      \"AGECLEAN\": \"\",\n" +
                "      \"AREANAMECLEAN\": \"\",\n" +
                "      \"YSFZD\": \"\",\n" +
                "      \"REGDATECLEAN\": \"2020-01-16\"\n" +
                "    },\n" +
                "    \"BASIC\": {\n" +
                "      \"DOM\": \"天津开发区黄海路19号0130\",\n" +
                "      \"TEL\": \"022-66253373\",\n" +
                "      \"EMID\": \"292dad4f1b2cf81ffd40fe86e6d93ab9\",\n" +
                "      \"OPTO\": \"2037-07-16\",\n" +
                "      \"EMAIL\": \"\",\n" +
                "      \"ENTID\": \"8fa07a747a1ada135fd0b57e5dc1cb21\",\n" +
                "      \"OPLOC\": \"天津开发区黄海路19号0130\",\n" +
                "      \"REGNO\": \"120191000002366\",\n" +
                "      \"EMPNUM\": \"0\",\n" +
                "      \"ESDATE\": \"2007-07-17\",\n" +
                "      \"FRNAME\": \"张贵富\",\n" +
                "      \"OPFROM\": \"2007-07-17\",\n" +
                "      \"RECCAP\": \"2800.000000\",\n" +
                "      \"REGCAP\": \"2800.000000\",\n" +
                "      \"REGORG\": \"天津市滨海新区市场监督管理局\",\n" +
                "      \"ABUITEM\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"CANDATE\": \"\",\n" +
                "      \"CBUITEM\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"CERTYPE\": \"\",\n" +
                "      \"COUNTRY\": \"中国\",\n" +
                "      \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"ENTTYPE\": \"有限责任公司\",\n" +
                "      \"OPSCOPE\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"REGCITY\": \"120000\",\n" +
                "      \"REVDATE\": \"\",\n" +
                "      \"APPRDATE\": \"2011-03-14\",\n" +
                "      \"IDENTITY\": \"\",\n" +
                "      \"ORGCODES\": \"663083409\",\n" +
                "      \"ORIREGNO\": \"\",\n" +
                "      \"OTHERTEL\": \"\",\n" +
                "      \"TAX_CODE\": \"911201166630834097\",\n" +
                "      \"ANCHEDATE\": \"\",\n" +
                "      \"ANCHEYEAR\": \"2018\",\n" +
                "      \"ENTSTATUS\": \"在营（开业）\",\n" +
                "      \"REGCAPCUR\": \"人民币元\",\n" +
                "      \"ZSOPSCOPE\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"CHANGEDATE\": \"2011-03-14\",\n" +
                "      \"COORDINATE\": \"117.687365,39.03323\",\n" +
                "      \"CREDITCODE\": \"911201166630834097\",\n" +
                "      \"ENTITYTYPE\": \"1\",\n" +
                "      \"ENTNAMEENG\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"INDUSTRYCO\": \"676\",\n" +
                "      \"POSTALCODE\": \"300457\",\n" +
                "      \"REGORGCITY\": \"天津市\",\n" +
                "      \"REGORGCODE\": \"120116\",\n" +
                "      \"UPDATEDATE\": \"20200219\",\n" +
                "      \"CERTYPECODE\": \"\",\n" +
                "      \"COUNTRYCODE\": \"156\",\n" +
                "      \"DOMDISTRICT\": \"120116\",\n" +
                "      \"ENTNAME_OLD\": \"天津嘉年华投资有限公司\",\n" +
                "      \"ENTTYPECODE\": \"1100\",\n" +
                "      \"INDUSTRYPHY\": \"金融业\",\n" +
                "      \"LOGOIMGNAME\": \"CKiAubNCQfhnWCwDRBTrHsN6VT9y4bHrZMfCXAM3S7B29emdDzqrKxc\",\n" +
                "      \"OPSCOANDFORM\": \"\",\n" +
                "      \"PALGORITHMID\": \"V1!Tw6F42WN+ZJTys2CCSBBMxafaWhfIxn/onWVvymTaTOMKqlRjLV8nrCMcfEDoapU6QCylhEvhedl<n>IugYRVDaUw==\",\n" +
                "      \"ENTSTATUSCODE\": \"1\",\n" +
                "      \"INDUSTRYCOALL\": \"676资本投资服务\",\n" +
                "      \"REGCAPCURCODE\": \"156\",\n" +
                "      \"S_EXT_NODENUM\": \"120000\",\n" +
                "      \"IDENTITYCARDNT\": \"\",\n" +
                "      \"INDUSTRYCOCODE\": \"676\",\n" +
                "      \"INDUSTRYCONAME\": \"资本投资服务\",\n" +
                "      \"INDUSTRYPHYALL\": \"J金融业\",\n" +
                "      \"REGORGDISTRICT\": \"天津市滨海新区\",\n" +
                "      \"REGORGPROVINCE\": \"天津市\",\n" +
                "      \"INDUSTRYPHYCODE\": \"J\",\n" +
                "      \"INDUSTRYPHYNAME\": \"金融业\",\n" +
                "      \"PERSON_ID_BOCOM\": \"\",\n" +
                "      \"BAIDU_COORDINATE\": \"117.694181,39.03903\",\n" +
                "      \"QRCODE_IMAGE_BASE64\": \"iVBORw0KGgoAAAANSUhEUgAAASwAAAEsAQAAAABRBrPYAAADZ0lEQVR42u2aTW7iUBCEC7Hw0kfwTczFLNlSLgY3eUfw0gtET1W1mQGSxWhW72lsKYnBH4tO/1X3A/E314oDO7AD+w+xAuAc22nFgEvch7itc4kH5rLxCZrA+PNVugfOpbv2vNNLfmDBdsmn9WMb+q+IWywD36GRt+DLByZsIz/aDEbT1nN0NzqLd3MA/X1oC6Npev+yMsLGfgFOZNvBHG/biX+YN0oeOeuqNPoelpViyvqyjfTY569vxaFSLC+MwNCFLB0xDbb5hxJdJ0ZLGWAOOvpJoea8oce62zoNLWDhNFfNkpG+o5GswrS+v6MFrEDNjwHm/F/ssWIn+WULWLD5zRlltHlC5/YRcWe+9C9hWTcmg65Ze9UB1T4UeaxeE5rAmPCTStTs5JHbJEHoMciBLWDq34QoBFN4QO0jKwEDES1g2b+dKFSDchFfYpdX96EJjPFmHcLaq3hD5jqNlLJCExh7NSuuWgW9oxwRq/zXv6AJzHOEVVSOFZsIfWCAxEgLWNi+rF7Bh6q9xTUL/hc0gKk+se9JwUp4ZPvIlIl1QhuYButNAkp+GhhlC7IDUpaUNjBdz7ECGorOgbxsbgMYTWPe3FZpciePuqJmVhezJjC/5ZRx6ruOuX9v74K2Xky6NYSx741ePIUbomrvW3GoGNNc55scSDUUOY2AN01eNUbTWHtTk9NZFy2etPnDPqRWj+WWAFm4lEFPTZ5ipAlMRgKpooABfzT5KV57fb1Y5NZVsilH7FtuO7IADy1gFFB8qCYuKWURvuT670MN1ouF1zS5az15Fd5bm7CRjK8jdtWYXCQRnilD39FFD0uQdS5NYJkjsLNIqIn7GEIKEWgBK7Y0K+5umndOPpB42UpVjGnB91uTq/ZqovAGs9ulbf2YrIpuP4bwhsa1F2qD59IKJg3YW5PbRfvY/RQo9WNwjjx3+Gri2QaH7W1vWTGWGa4oG3IyumTeWKDMTWC+lChwvIVLmCPvXZNXjKUa7FzCJMfdv73t2MZXHVIzplPFsLN8wCtWK8v4HGMrxnw8rVl0P9vdl2WLJyO0g+k8ulhPaRObB+5425PXj+07fG/MfLDCDhjr9+8SVIn5hN1TUHfNIznW3lMuLz9SplYMe6Wa9N0MeyzyQMKzdwvY8R2zAzuwA/tX7BfYj8AjV+zgfwAAAABJRU5ErkJggg==\"\n" +
                "    },\n" +
                "    \"FILIATION\": [],\n" +
                "    \"FINALCASE\": [],\n" +
                "    \"MORDETAIL\": [],\n" +
                "    \"ORGDETAIL\": {},\n" +
                "    \"STOCKPAWN\": [],\n" +
                "    \"TRADEMARK\": [],\n" +
                "    \"LISTEDINFO\": [],\n" +
                "    \"MORGUAINFO\": [],\n" +
                "    \"JUDICIALAID\": [],\n" +
                "    \"LIQUIDATION\": [],\n" +
                "    \"MORTGAGEALT\": [],\n" +
                "    \"MORTGAGECAN\": [],\n" +
                "    \"MORTGAGEPER\": [],\n" +
                "    \"MORTGAGEREG\": [],\n" +
                "    \"PUNISHBREAK\": [],\n" +
                "    \"SHARESFROST\": [],\n" +
                "    \"MORTGAGEDEBT\": [],\n" +
                "    \"MORTGAGEPAWN\": [],\n" +
                "    \"SHARESIMPAWN\": [],\n" +
                "    \"STOCKPAWNALT\": [],\n" +
                "    \"STOCKPAWNREV\": [],\n" +
                "    \"EXCEPTIONLIST\": [],\n" +
                "    \"LISTEDMANAGER\": [],\n" +
                "    \"MORTGAGEBASIC\": [],\n" +
                "    \"LISTEDCOMPINFO\": {},\n" +
                "    \"PERSON\": [\n" +
                "      {\n" +
                "        \"SEX\": \"\",\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"292dad4f1b2cf81ffd40fe86e6d93ab9\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"NATDATE\": \"\",\n" +
                "        \"PERNAME\": \"张贵富\",\n" +
                "        \"SEXCODE\": \"\",\n" +
                "        \"POSITION\": \"董事长\",\n" +
                "        \"RECORD_ID\": \"b4f105d11104f5319b336c20fdd5c175\",\n" +
                "        \"PALGORITHMID\": \"V1!Tw6F42WN+ZJTys2CCSBBMxafaWhfIxn/onWVvymTaTOMKqlRjLV8nrCMcfEDoapU6QCylhEvhedl<n>IugYRVDaUw==\",\n" +
                "        \"PERSONAMOUNT\": \"5\",\n" +
                "        \"POSITIONCODE\": \"431A\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"SEX\": \"\",\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"24e8e857992afddeee786b797203bd3a\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"NATDATE\": \"\",\n" +
                "        \"PERNAME\": \"郭美华\",\n" +
                "        \"SEXCODE\": \"\",\n" +
                "        \"POSITION\": \"董事\",\n" +
                "        \"RECORD_ID\": \"bcf1d6aeabd6a22e718a68946af67807\",\n" +
                "        \"PALGORITHMID\": \"V1!N8qKSZD1RKrD/DwKrkZRH7oU7ujtaWp37ezlwK6vCYOKnNfSQLuZmha+buKQAX87rqd9vHBNwTFo<n>ZbxxHNSCNw==\",\n" +
                "        \"PERSONAMOUNT\": \"5\",\n" +
                "        \"POSITIONCODE\": \"432A\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"SEX\": \"\",\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"65cb55ee66b6c0cd71613150c9dcb987\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"NATDATE\": \"\",\n" +
                "        \"PERNAME\": \"萧家聪\",\n" +
                "        \"SEXCODE\": \"\",\n" +
                "        \"POSITION\": \"董事\",\n" +
                "        \"RECORD_ID\": \"68ce07e25ce015d6380fdfdf7c38bb79\",\n" +
                "        \"PALGORITHMID\": \"V1!HlE0SzteH+dVij+1oBX6sMA6nc0KiD8Z4Hc+hnHeYXEer0/63Pc3B8K5cA3OP8xHQfqUnN9HZKw9<n>c2JRSXAZ3w==\",\n" +
                "        \"PERSONAMOUNT\": \"5\",\n" +
                "        \"POSITIONCODE\": \"432A\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"SEX\": \"\",\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"292dad4f1b2cf81ffd40fe86e6d93ab9\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"NATDATE\": \"\",\n" +
                "        \"PERNAME\": \"张贵富\",\n" +
                "        \"SEXCODE\": \"\",\n" +
                "        \"POSITION\": \"经理\",\n" +
                "        \"RECORD_ID\": \"7c42d68a15a8f9bcd957f8f86748fc8f\",\n" +
                "        \"PALGORITHMID\": \"V1!Tw6F42WN+ZJTys2CCSBBMxafaWhfIxn/onWVvymTaTOMKqlRjLV8nrCMcfEDoapU6QCylhEvhedl<n>IugYRVDaUw==\",\n" +
                "        \"PERSONAMOUNT\": \"5\",\n" +
                "        \"POSITIONCODE\": \"436A\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"SEX\": \"\",\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"7883bd109c82405ecd53e1cd66027715\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"NATDATE\": \"\",\n" +
                "        \"PERNAME\": \"张凯翔\",\n" +
                "        \"SEXCODE\": \"\",\n" +
                "        \"POSITION\": \"监事\",\n" +
                "        \"RECORD_ID\": \"411089ab42d257b61c3326d4194c687c\",\n" +
                "        \"PALGORITHMID\": \"V1!PUrA0XuD609NzveORkTO8/FFcah3cNbYh/MJRRzn3K4oT/3LmJZH5CwzugqhdHsMb+qUXk/xCxBm<n>AfZDhYucOA==\",\n" +
                "        \"PERSONAMOUNT\": \"5\",\n" +
                "        \"POSITIONCODE\": \"408A\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ALTER\": [\n" +
                "      {\n" +
                "        \"ALTAF\": \"乖乖\",\n" +
                "        \"ALTBE\": \"嘉年华\",\n" +
                "        \"ALTDATE\": \"2011-03-14\",\n" +
                "        \"ALTITEM\": \"名称变更（字号名称、集团名称等）\",\n" +
                "        \"RECORD_ID\": \"df9fe8e159e8d96c9d3b021cfe555228\",\n" +
                "        \"ALTITEMCODE\": \"69\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ALTAF\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"ALTBE\": \"天津嘉年华投资有限公司\",\n" +
                "        \"ALTDATE\": \"2011-03-14\",\n" +
                "        \"ALTITEM\": \"名称变更（字号名称、集团名称等）\",\n" +
                "        \"RECORD_ID\": \"0b2df2540ebd1704928de651bd9010d8\",\n" +
                "        \"ALTITEMCODE\": \"69\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ENTCASEBASEINFO\": [],\n" +
                "    \"METADATA\": {\n" +
                "      \"SOURCE\": \"1\"\n" +
                "    },\n" +
                "    \"RELATEDPUNISHED\": [\n" +
                "      {\n" +
                "        \"COURTNAME\": \"天津市第一中级人民法院\",\n" +
                "        \"CASESTATE\": \"执行中\",\n" +
                "        \"CASECODE\": \"(2020)津01执161号\",\n" +
                "        \"EXECMONEY\": \"97887728\",\n" +
                "        \"FOCUSNUMBER\": \"0\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"CARDNUMCLEAN\": \"91120116600****788K\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2020-04-01\",\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"COURTNAME\": \"天津市南开区人民法院\",\n" +
                "        \"CASESTATE\": \"执行中\",\n" +
                "        \"CASECODE\": \"(2020)津0104执908号\",\n" +
                "        \"EXECMONEY\": \"27552226\",\n" +
                "        \"FOCUSNUMBER\": \"0\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"CARDNUMCLEAN\": \"91120116600****788K\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2020-01-16\",\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"COURTNAME\": \"天津市南开区人民法院\",\n" +
                "        \"CASESTATE\": \"执行中\",\n" +
                "        \"CASECODE\": \"(2020)津0104执908号\",\n" +
                "        \"EXECMONEY\": \"27552226\",\n" +
                "        \"FOCUSNUMBER\": \"0\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"天津嘉年华数位广告传媒有限公司\",\n" +
                "        \"CARDNUMCLEAN\": \"91120118341****756R\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2020-01-16\",\n" +
                "        \"ENTID\": \"8e3378efb8de4965208b37146fe12989\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"COURTNAME\": \"天津市第二中级人民法院\",\n" +
                "        \"CASESTATE\": \"执行中\",\n" +
                "        \"CASECODE\": \"(2020)津02执57号\",\n" +
                "        \"EXECMONEY\": \"30777360\",\n" +
                "        \"FOCUSNUMBER\": \"0\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"CARDNUMCLEAN\": \"91120116600****788K\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2020-01-13\",\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"COURTNAME\": \"天津市第一中级人民法院\",\n" +
                "        \"CASESTATE\": \"执行中\",\n" +
                "        \"CASECODE\": \"(2019)津01执443号\",\n" +
                "        \"EXECMONEY\": \"14989481\",\n" +
                "        \"FOCUSNUMBER\": \"0\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"CARDNUMCLEAN\": \"91120116600****788K\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2019-04-24\",\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"YEARREPORTALTER\": [\n" +
                "      {\n" +
                "        \"ANCHEID\": \"9ac8d456650cf4579c5db3362b5a8c6e\",\n" +
                "        \"ALITEM\": \"注册号\",\n" +
                "        \"ALTBE\": \"130\",\n" +
                "        \"ALTAF\": \"9113050655608617XF\",\n" +
                "        \"ALTDATE\": \"2018-03-16\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"9ac8d456650cf4579c5db3362b5a8c6e\",\n" +
                "        \"ALITEM\": \"注册号\",\n" +
                "        \"ALTBE\": \"91220\",\n" +
                "        \"ALTAF\": \"912202940597663347\",\n" +
                "        \"ALTDATE\": \"2018-03-16\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"DEALIN\": [\n" +
                "      {\n" +
                "        \"BUSST\": \"开业\",\n" +
                "        \"ASSGRO\": \"39242.88\",\n" +
                "        \"LIAGRO\": \"36686.59\",\n" +
                "        \"NETINC\": \"0.77\",\n" +
                "        \"PROGRO\": \"0.77\",\n" +
                "        \"RATGRO\": \"0\",\n" +
                "        \"DEFICIT\": \"0\",\n" +
                "        \"SERVINC\": \"4.55\",\n" +
                "        \"VENDINC\": \"0\",\n" +
                "        \"CURRENCY\": \"人民币元\",\n" +
                "        \"LTERMINV\": \"32107.89\",\n" +
                "        \"ANCHEYEAR\": \"2012\",\n" +
                "        \"LTERMLIAAM\": \"0\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"BUSST\": \"开业\",\n" +
                "        \"ASSGRO\": \"20378.87\",\n" +
                "        \"LIAGRO\": \"17814.67\",\n" +
                "        \"NETINC\": \"0\",\n" +
                "        \"PROGRO\": \"0.2\",\n" +
                "        \"RATGRO\": \"0\",\n" +
                "        \"DEFICIT\": \"0.14\",\n" +
                "        \"SERVINC\": \"0\",\n" +
                "        \"VENDINC\": \"0\",\n" +
                "        \"CURRENCY\": \"人民币元\",\n" +
                "        \"LTERMINV\": \"18254.09\",\n" +
                "        \"ANCHEYEAR\": \"2009\",\n" +
                "        \"LTERMLIAAM\": \"0\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"JUDICIALAIDALTER\": [],\n" +
                "    \"QUICKCANCELBASIC\": {},\n" +
                "    \"YEARREPORTFORINV\": [\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"天津乖乖粮油有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"91120224681868322N\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"91120116600515788K\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"吉林乖乖食品有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"912202940597663347\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"天津嘉年华远洋科技有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"911201166847202195\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"河北嘉荣房地产开发有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"9113050655608617XF\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"乖乖嘉年华(天津)食品有限公司\",\n" +
                "        \"REGNO\": \"\",\n" +
                "        \"CREDITNO\": \"911202246688230745\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"YEARREPORTSOCSEC\": {\n" +
                "      \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "      \"SO110\": \"0\",\n" +
                "      \"SO210\": \"0\",\n" +
                "      \"SO310\": \"0\",\n" +
                "      \"SO410\": \"0\",\n" +
                "      \"SO510\": \"0\",\n" +
                "      \"TOTALWAGES_SO110\": \"\",\n" +
                "      \"TOTALWAGES_SO210\": \"\",\n" +
                "      \"TOTALWAGES_SO310\": \"\",\n" +
                "      \"TOTALWAGES_SO410\": \"\",\n" +
                "      \"TOTALWAGES_SO510\": \"\",\n" +
                "      \"TOTALPAYMENT_SO110\": \"\",\n" +
                "      \"TOTALPAYMENT_SO210\": \"\",\n" +
                "      \"TOTALPAYMENT_SO310\": \"\",\n" +
                "      \"TOTALPAYMENT_SO410\": \"\",\n" +
                "      \"TOTALPAYMENT_SO510\": \"\",\n" +
                "      \"UNPAIDSOCIALINS_SO110\": \"\",\n" +
                "      \"UNPAIDSOCIALINS_SO210\": \"\",\n" +
                "      \"UNPAIDSOCIALINS_SO310\": \"\",\n" +
                "      \"UNPAIDSOCIALINS_SO410\": \"\",\n" +
                "      \"UNPAIDSOCIALINS_SO510\": \"\"\n" +
                "    },\n" +
                "    \"JUDICIALAIDDETAIL\": [],\n" +
                "    \"LISTEDSHAREHOLDER\": [],\n" +
                "    \"QUICKCANCELDISSENT\": [],\n" +
                "    \"RELATEDPUNISHBREAK\": [\n" +
                "      {\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"CARDNUM\": \"9112011660****788K\",\n" +
                "        \"COURTNAME\": \"天津市滨海新区人民法院\",\n" +
                "        \"GISTID\": \"(2018)津0116民初637号\",\n" +
                "        \"CASECODE\": \"(2019)津0116执199号\",\n" +
                "        \"GISTUNIT\": \"天津市滨海新区人民法院民一庭\",\n" +
                "        \"DUTY\": \"本院认为，原告益平拆迁公司与被告嘉年华公司签订的《天津市滨海新区轨道交通Z4线一期工程建设用地征收补偿协议》系双方当事人的真实意思表示，内容不违反法律和行政法规的强制性规定，合法有效。关于原告主张合同解除问题，依据《中华人民共和国合同法》第九十四条的规定：“有下列情形之一的，当事人可以解除合同：因不可抗力致使不能实现合同目的；在履行期限届满之前，当事人一方明确表示或者以自己的行为明确表示不履行债务；当事人一方迟延履行主要债务，经催告后在合理期限内仍未履行；当事人一方迟延履行债务或者有其他违约行为致使不能实现合同目的；法律规定的其他情形。”本案中，因涉案土地被法院查封，经过原告的催告后仍无法按时履行合同义务，致使合同目不能实现，符合上述法律规定的情形，故对原告的解除合同的诉讼请求应予支持,被告应当返还原告给付的拆迁补偿款2000万元。\\\\\\\\n原、被告在协议中约定如逾期不能完成合同约定内容则应返还原告支付的补偿款，并按每日已实际支付相应款项的万分之一标准支付违约金，该约定不违反法律规定，故对原告主张的违约金予以支持，双方虽约定为违约金实际则是原告主张被告占用补偿款期间的损失，原告主张的违约金期间尚在双方协议履行期间，协议中对被告完成约定任务的时间以及何时计算违约金并没有明确的约定，且双方一直在协商如何解决查封或者变更合同履行内容等事项，从而原告一直未正式向被告提出解除合同，行使其解除权，因此本院认为原告主张的违约金应从被告应当返还补偿款之日即合同解除之日开始计算为宜。本案原告于2018年2月22日起诉，同年3月22日被告到庭参加诉讼，该开庭时间可视为原告通知被告要求解除合同且被告已经知晓的时间，根据《中华人民共和国合同法》第九十六条之规定，双方合同应于2018年3月22日解除，原告主张的违约金应从2018年3月23日开始计算，现原告同意自2018年4月1日开始计算违约金，符合法律规定且未加重被告负担，本院予以支持。最后，被告善岛建设公司自愿向原告出具了担保函，被告善岛建设公司应当对上述被告嘉年华公司的给付义务承担连带给付责任。\\\\\\\\n综上所述，依据《中华人民共和国合同法》第六十条第一款、第九十四条、第九十六条、第九十七条、第一百零七条、《中华人民共和国担保法》第十八条之规定，判决如下：\\\\\\\\n一、确认解除原告天津滨海新区益平拆迁有限公司与被告嘉年华（天津）国际有限公司签订的《天津市滨海新区轨道交通Z4线一期工程建设用地征收补偿协议》；\\\\\\\\n二、被告嘉年华（天津）国际有限公司于本判决生效后十日内返还原告天津滨海新区益平拆迁有限公司拆迁补偿款20，000，000元；\\\\\\\\n三、被告嘉年华（天津）国际有限公司于本判决生效后十日内支付原告天津滨海新区益平拆迁有限公司自2018年4月1日至实际还清补偿款之日止的违约金（以未给付补偿款为基数，按每日万分之一计算）；\\\\\\\\n四、被告善岛建设（天津）有限公司对被告嘉年华（天津）国际有限公司上述给付义务承担连带责任；\\\\\\\\n五、驳回原告天津滨海新区益平拆迁有限公司其他诉讼请求。\",\n" +
                "        \"PERFORMANCE\": \"全部未履行\",\n" +
                "        \"PERFORMEDPART\": \"暂无\",\n" +
                "        \"UNPERFORMPART\": \"暂无\",\n" +
                "        \"DISRUPTTYPENAME\": \"违反财产报告制度\",\n" +
                "        \"FOCUSNUMBER\": \"\",\n" +
                "        \"BUSINESSENTITY\": \"\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"天津\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2019-02-25\",\n" +
                "        \"PUBLISHDATECLEAN\": \"2019-06-14\",\n" +
                "        \"EXITDATE\": \"\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\",\n" +
                "        \"CASESTATE\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"ENTID\": \"067b404a6876bd072b03fe32ee17450a\",\n" +
                "        \"CARDNUM\": \"60051578-8\",\n" +
                "        \"COURTNAME\": \"天津市北辰区人民法院\",\n" +
                "        \"GISTID\": \"(2017)津0113民初315号\",\n" +
                "        \"CASECODE\": \"(2017)津0113执1972号\",\n" +
                "        \"GISTUNIT\": \"天津市北辰区人民法院小淀法庭\",\n" +
                "        \"DUTY\": \"欠款1450万元及利息\",\n" +
                "        \"PERFORMANCE\": \"全部未履行\",\n" +
                "        \"PERFORMEDPART\": \"暂无\",\n" +
                "        \"UNPERFORMPART\": \"暂无\",\n" +
                "        \"DISRUPTTYPENAME\": \"有履行能力而拒不履行生效法律文书确定义务\",\n" +
                "        \"FOCUSNUMBER\": \"\",\n" +
                "        \"BUSINESSENTITY\": \"张贵富\",\n" +
                "        \"TYPE\": \"法人或其他组织\",\n" +
                "        \"INAMECLEAN\": \"嘉年华(天津)国际有限公司\",\n" +
                "        \"SEXYCLEAN\": \"\",\n" +
                "        \"AGECLEAN\": \"\",\n" +
                "        \"AREANAMECLEAN\": \"天津\",\n" +
                "        \"YSFZD\": \"\",\n" +
                "        \"REGDATECLEAN\": \"2017-08-23\",\n" +
                "        \"PUBLISHDATECLEAN\": \"2017-12-07\",\n" +
                "        \"EXITDATE\": \"\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"PALGORITHMID\": \"\",\n" +
                "        \"CASESTATE\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"ENT_ZONGHEINFO\": {\n" +
                "      \"OPLOC\": \"天津开发区黄海路19号0130\",\n" +
                "      \"EMPNUM\": \"0\",\n" +
                "      \"RECCAP\": \"2800.000000\",\n" +
                "      \"ABUITEM\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"CBUITEM\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、装修装饰、证券行业、仓储服务、高新技术产业的投资。（依法须经批准的项目，经相关部门批准后方可开展经营活动）\",\n" +
                "      \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"ANCHEDATE\": \"\",\n" +
                "      \"ANCHEYEAR\": \"2018\",\n" +
                "      \"ENTNAMEENG\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"REGORGCODE\": \"120116\",\n" +
                "      \"DOMDISTRICT\": \"120116\",\n" +
                "      \"INDUSTRYPHY\": \"金融业\",\n" +
                "      \"OPSCOANDFORM\": \"\",\n" +
                "      \"INDUSTRYCOALL\": \"676资本投资服务\",\n" +
                "      \"S_EXT_NODENUM\": \"120000\",\n" +
                "      \"INDUSTRYPHYALL\": \"J金融业\",\n" +
                "      \"REGORGPROVINCE\": \"天津市\",\n" +
                "      \"INDUSTRYPHYCODE\": \"J\",\n" +
                "      \"INDUSTRYPHYNAME\": \"金融业\"\n" +
                "    },\n" +
                "    \"SHAREHOLDER\": [\n" +
                "      {\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"REGNO\": \"120191000016897\",\n" +
                "        \"ACCONAM\": \"1680.000000\",\n" +
                "        \"CONDATE\": \"2007-07-17\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"COUNTRY\": \"中国\",\n" +
                "        \"INVTYPE\": \"法人股东\",\n" +
                "        \"SHANAME\": \"天津开发区永莲物业管理有限公司\",\n" +
                "        \"IDENTITY\": \"\",\n" +
                "        \"SUBCONAM\": \"1680.000000\",\n" +
                "        \"SUMCONAM\": \"2800.000000\",\n" +
                "        \"INVAMOUNT\": \"2\",\n" +
                "        \"RECORD_ID\": \"0c2c15831ad0b2a2e345efc9fdbdf8ba\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"CREDITCODE\": \"911201167413572775\",\n" +
                "        \"CONFORMCODE\": \"1\",\n" +
                "        \"COUNTRYCODE\": \"156\",\n" +
                "        \"FUNDEDRATIO\": \"60.00%\",\n" +
                "        \"INVTYPECODE\": \"10\",\n" +
                "        \"CURRENCYCODE\": \"156\",\n" +
                "        \"PALGORITHMID\": \"\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\",\n" +
                "        \"INVSUMFUNDEDRATIO\": \"100.00%\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"CDID\": \"\",\n" +
                "        \"EMID\": \"\",\n" +
                "        \"REGNO\": \"120191000016821\",\n" +
                "        \"ACCONAM\": \"1120.000000\",\n" +
                "        \"CONDATE\": \"2007-07-17\",\n" +
                "        \"CONFORM\": \"货币\",\n" +
                "        \"COUNTRY\": \"中国\",\n" +
                "        \"INVTYPE\": \"法人股东\",\n" +
                "        \"SHANAME\": \"天津鸿海大荣进出口贸易有限公司\",\n" +
                "        \"IDENTITY\": \"\",\n" +
                "        \"SUBCONAM\": \"1120.000000\",\n" +
                "        \"SUMCONAM\": \"2800.000000\",\n" +
                "        \"INVAMOUNT\": \"2\",\n" +
                "        \"RECORD_ID\": \"6f938bd6f8076438e872ccc693123853\",\n" +
                "        \"REGCAPCUR\": \"人民币元\",\n" +
                "        \"CREDITCODE\": \"9112011675811612XG\",\n" +
                "        \"CONFORMCODE\": \"1\",\n" +
                "        \"COUNTRYCODE\": \"156\",\n" +
                "        \"FUNDEDRATIO\": \"40.00%\",\n" +
                "        \"INVTYPECODE\": \"10\",\n" +
                "        \"CURRENCYCODE\": \"156\",\n" +
                "        \"PALGORITHMID\": \"\",\n" +
                "        \"IDENTITYCARDNT\": \"\",\n" +
                "        \"PERSON_ID_BOCOM\": \"\",\n" +
                "        \"INVSUMFUNDEDRATIO\": \"100.00%\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"YEARREPORTALTERSTOCK\": [],\n" +
                "    \"YEARREPORTWEBSITEINFO\": [],\n" +
                "    \"ENT_HANGYEINFO\": {\n" +
                "      \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"INDUSTRYPHY\": \"金融业\",\n" +
                "      \"INDUSTRYCOCODE\": \"676\",\n" +
                "      \"INDUSTRYCONAME\": \"资本投资服务\",\n" +
                "      \"INDUSTRYPHYNAME\": \"金融业\"\n" +
                "    },\n" +
                "    \"YEARREPORTANASSETSINFO\": [],\n" +
                "    \"YEARREPORTFORGUARANTEE\": [],\n" +
                "    \"ENT_BIAOSHIINFO\": {\n" +
                "      \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "      \"ORGCODES\": \"663083409\"\n" +
                "    },\n" +
                "    \"YEARREPORTBASIC\": [\n" +
                "      {\n" +
                "        \"TEL\": \"022-66253373\",\n" +
                "        \"ADDR\": \"天津开发区黄海路118号\",\n" +
                "        \"BUSST\": \"开业\",\n" +
                "        \"EMAIL\": \"970703880@qq.com\",\n" +
                "        \"REGNO\": \"120191000002366\",\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"CREDITNO\": \"911201166630834097\",\n" +
                "        \"ANCHEDATE\": \"2019-03-06\",\n" +
                "        \"ANCHEYEAR\": \"2018\",\n" +
                "        \"WOMEMPNUM\": \"\",\n" +
                "        \"POSTALCODE\": \"300457\",\n" +
                "        \"MAINBUSIACT\": \"以自有资金对工业、商业、能源建设、交通运输、房地产、证券行业、仓储服务、高新技术产业的投资。\",\n" +
                "        \"HOLDINGSMSG_CN\": \"其他\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"TEL\": \"022-66253373\",\n" +
                "        \"ADDR\": \"天津开发区黄海路118号\",\n" +
                "        \"BUSST\": \"开业\",\n" +
                "        \"EMAIL\": \"zjs@guaiguai.cn\",\n" +
                "        \"REGNO\": \"120191000002366\",\n" +
                "        \"ANCHEID\": \"9ac8d456650cf4579c5db3362b5a8c6e\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"CREDITNO\": \"911201166630834097\",\n" +
                "        \"ANCHEDATE\": \"2018-03-16\",\n" +
                "        \"ANCHEYEAR\": \"2017\",\n" +
                "        \"WOMEMPNUM\": \"\",\n" +
                "        \"POSTALCODE\": \"300457\",\n" +
                "        \"MAINBUSIACT\": \"\",\n" +
                "        \"HOLDINGSMSG_CN\": \"\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"TEL\": \"022-66252677\",\n" +
                "        \"ADDR\": \"天津开发区黄海路118号\",\n" +
                "        \"BUSST\": \"开业\",\n" +
                "        \"EMAIL\": \"zjs@guaiguai.cn\",\n" +
                "        \"REGNO\": \"120191000002366\",\n" +
                "        \"ANCHEID\": \"36bd125903dc5bcb36895d5ba8ff7874\",\n" +
                "        \"ENTNAME\": \"天津乖乖投资控股集团有限公司\",\n" +
                "        \"CREDITNO\": \"911201166630834097\",\n" +
                "        \"ANCHEDATE\": \"2017-05-26\",\n" +
                "        \"ANCHEYEAR\": \"2016\",\n" +
                "        \"WOMEMPNUM\": \"\",\n" +
                "        \"POSTALCODE\": \"300457\",\n" +
                "        \"MAINBUSIACT\": \"\",\n" +
                "        \"HOLDINGSMSG_CN\": \"\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"YEARREPORTSUBCAPITAL\": [\n" +
                "      {\n" +
                "        \"INV\": \"天津鸿海大荣进出口进出口贸易有限公司\",\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"CONDATE\": \"2007-07-02\",\n" +
                "        \"CONFORM\": \"\",\n" +
                "        \"CURRENCY\": \"\",\n" +
                "        \"LISUBCONAM\": \"1120.000000\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"INV\": \"天津开发区永莲物业管理有限公司\",\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"CONDATE\": \"2007-07-02\",\n" +
                "        \"CONFORM\": \"\",\n" +
                "        \"CURRENCY\": \"\",\n" +
                "        \"LISUBCONAM\": \"1680.000000\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"YEARREPORTPAIDUPCAPITAL\": [\n" +
                "      {\n" +
                "        \"INV\": \"天津鸿海大荣进出口进出口贸易有限公司\",\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"CONDATE\": \"2007-07-09\",\n" +
                "        \"CONFORM\": \"\",\n" +
                "        \"CURRENCY\": \"\",\n" +
                "        \"LIACCONAM\": \"1120.000000\"\n" +
                "      },\n" +
                "      {\n" +
                "        \"INV\": \"天津开发区永莲物业管理有限公司\",\n" +
                "        \"ANCHEID\": \"ede95f0658f1f1b70ecbd7ba80a00ed5\",\n" +
                "        \"CONDATE\": \"2007-07-06\",\n" +
                "        \"CONFORM\": \"\",\n" +
                "        \"CURRENCY\": \"\",\n" +
                "        \"LIACCONAM\": \"1680.000000\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}";
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
