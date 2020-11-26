package com.zhang.json.jsonfilter;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author zhang
 */
@Slf4j
public class JsonFilter {

    private static final JsonFactory JSON_FACTORY = new JsonFactory();

    public void filter(Reader in, StringWriter out, String includes, String excludes) {
        Validate.notNull(in, "输入参数不能为空");
        Validate.notNull(out, "输出参数不能为空");
        try (JsonParser parser = JSON_FACTORY.createParser(in);
             JsonGenerator generator = JSON_FACTORY.createGenerator(out)) {
            FieldFilterTree tree = new FieldFilterParser(includes, excludes).parse();
            JsonFilterParser jsp = new JsonFilterParser(parser, tree);
            while (jsp.nextToken() != null) {
                processToken(generator, jsp);
            }
        } catch (Exception e) {
            log.error("过滤JSON数据错误，includes=[{}], excludes=[{}]", includes, excludes);
        }
    }

    public String filterExclude(String json, String excludes) {
        Validate.notBlank(json, "输入参数json不能为空");
        try (Reader reader = new StringReader(json);
             StringWriter out = new StringWriter()) {
            filter(reader, out, null, excludes);
            return out.toString();
        } catch (Exception e) {
            log.error("过滤JSON数据错误, excludes=[{}], JSON=[{}]", excludes, json);
            return null;
        }
    }

    public String filterInclude(String json, String includes) {
        Validate.notBlank(json, "输入参数json不能为空");
        try (Reader reader = new StringReader(json);
             StringWriter out = new StringWriter()) {
            filter(reader, out, includes, null);
            return out.toString();
        } catch (Exception e) {
            log.error("过滤JSON数据错误，includes=[{}], JSON=[{}]", includes, json);
            return null;
        }
    }

    private void processToken(JsonGenerator generator, JsonFilterParser jfp) throws IOException {
        JsonParser parser = jfp.getJsonParser();
        if (jfp.isTokenExclude()) {
            //log.debug("ignore    {} - attr:{} - val:{}", parser.getCurrentToken().name(), parser.getCurrentName(), parser.getValueAsString());
            jfp.skipValue();
        } else {
            if (jfp.isStructureFullyInclude()) {
                //log.debug("copy full {} - attr:{} - val:{}", parser.getCurrentToken().name(), parser.getCurrentName(), parser.getValueAsString());
                generator.copyCurrentStructure(parser);
            } else {
                //log.debug("copy      {} - attr:{} - val:{}", parser.getCurrentToken().name(), parser.getCurrentName(), parser.getValueAsString());
                generator.copyCurrentEvent(parser);
            }
        }
    }
}
