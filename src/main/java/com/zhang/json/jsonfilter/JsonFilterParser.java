package com.zhang.json.jsonfilter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

import static com.fasterxml.jackson.core.JsonToken.*;

/**
 * @author zhang
 */
@Slf4j
public class JsonFilterParser {

    private JsonParser jsonParser;

    private FieldFilterTree curNode;

    private FieldFilterTree parentNode;

    private String currentFieldName = null;

    private boolean structureFullyInclude = false;

    private boolean tokenExclude = false;

    public JsonFilterParser(JsonParser parser, FieldFilterTree tree) {
        this.jsonParser = parser;
        this.curNode = tree;
        this.parentNode = null;
    }

    public JsonToken nextToken() throws IOException {
        JsonToken token = jsonParser.nextToken();
        structureFullyInclude = false;
        tokenExclude = false;
        if (FIELD_NAME == token) {
            processFieldToken();
        } else if (START_ARRAY.equals(token)
                || (START_OBJECT.equals(token) && !jsonParser.getParsingContext().getParent().inArray())) {
            processStartObject();
        } else if (END_ARRAY.equals(token)
                || END_OBJECT.equals(token) && !jsonParser.getParsingContext().inArray()) {
            processEndObject();
        }
        //log.debug("processed {} '{}' - curNode {}", token, currentFieldName, curNode);
        return token;
    }

    private boolean isFieldSkipped() {
        boolean skip = false;
        FieldFilterTree fieldNode = curNode.getChild(currentFieldName);
        if ((Objects.nonNull(fieldNode) && fieldNode.isExcludeLeafField())
                || (Objects.isNull(fieldNode) && !curNode.getIncludeFieldChildren().isEmpty())) {
            skip = true;
        }
        return skip;
    }

    public void skipValue() throws IOException {
        JsonToken token = jsonParser.nextToken();
        if (JsonToken.START_ARRAY.equals(token) || JsonToken.START_OBJECT.equals(token)) {
            jsonParser.skipChildren();
        }
    }

    private void processFieldToken() throws IOException {
        currentFieldName = jsonParser.getCurrentName();
        if (isFieldSkipped()) {
            tokenExclude = true;
        } else {
            FieldFilterTree fieldNode = curNode.getChild(currentFieldName);
            if (Objects.isNull(fieldNode) || fieldNode.getChildren().isEmpty()) {
                structureFullyInclude = true;
            }
        }
    }

    private void processStartObject() {
        if (Objects.nonNull(currentFieldName)) {
            FieldFilterTree tmpIncludes = curNode.getChild(currentFieldName);
            parentNode = curNode;
            curNode = tmpIncludes;
        }
    }

    private void processEndObject() {
        if (Objects.nonNull(parentNode)) {
            FieldFilterTree tmpIncludes = parentNode.getParent();
            curNode = parentNode;
            parentNode = tmpIncludes;
            currentFieldName = curNode.getData().getValue();
        }
    }

    public JsonParser getJsonParser() {
        return jsonParser;
    }

    public boolean isStructureFullyInclude() {
        return structureFullyInclude;
    }

    public boolean isTokenExclude() {
        return tokenExclude;
    }
}
