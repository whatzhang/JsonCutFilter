package com.zhang.json.jsonfilter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhang
 */
@Slf4j
public class FieldFilterParser {

    private FieldFilterTokenizer incFields;

    private FieldFilterTokenizer excFields;

    private FieldFilterTokenizer curFieldsFilter = null;

    private FieldFilterTree curTree = null;

    private FieldFilterToken curToken = null;

    private FieldFilterToken prevToken = null;

    private boolean includeFieldFlag;

    private int skipCounterRef;

    private int parenthesisCount = 0;

    private boolean skip;

    public FieldFilterParser(String incFields, String excFields) {
        this.incFields = new FieldFilterTokenizer(StringUtils.trimToEmpty(incFields));
        this.excFields = new FieldFilterTokenizer(StringUtils.trimToEmpty(excFields));
    }

    public FieldFilterTree parse() {
        FieldFilterTree res = FieldFilterTree.createRoot();
        try {
            if (StringUtils.isNotBlank(incFields.getFieldFilter())) {
                curTree = res;
                includeFieldFlag = true;
                curFieldsFilter = incFields;
                parseFieldFilter();
            }
            if (StringUtils.isNotBlank(excFields.getFieldFilter())) {
                curTree = res;
                includeFieldFlag = false;
                curFieldsFilter = excFields;
                parseFieldFilter();
            }
        } catch (Exception e) {
            throw new RuntimeException("Syntax error in parameter '" + curFieldsFilter.getFieldFilter() + "' at pos '" + curFieldsFilter.getCurPos() + "' - current token = " + curToken + " - previous token = " + prevToken);
        }
        return res;
    }

    private void parseFieldFilter() {
        curToken = curFieldsFilter.nextToken();
        while (curToken != null) {
            processToken();
            prevToken = curToken;
            curToken = curFieldsFilter.nextToken();
        }
        if (prevToken != null && (FieldFilterTokenType.OPEN_PARENTHESIS.equals(prevToken.getType())
                || FieldFilterTokenType.COMMA.equals(prevToken.getType()))) {
            throw new IllegalArgumentException("Filter cannot and with a ',' or a '('.");
        }
        if (parenthesisCount != 0) {
            throw new IllegalArgumentException("No matching between '(' and ')' - diff=" + parenthesisCount);
        }
    }

    private void processToken() {
        switch (curToken.getType()) {
            case FIELD_NAME:
                processParamToken();
                break;
            case COMMA:
                processCommaToken();
                break;
            case CLOSE_PARENTHESIS:
                processCloseParenthesisToken();
                break;
            case OPEN_PARENTHESIS:
                processOpenParenthesisToken();
                break;
            default:
                throw new IllegalArgumentException("unknown token type : " + curToken.getType());
        }
    }

    private void processParamToken() {
        if (!skip) {
            if (!includeFieldFlag) {
                if (curTree.getIncludeFieldChildren().isEmpty()) {
                    curTree.addChild(curToken, includeFieldFlag);
                } else if (curTree.getIncludeFieldChild(curToken) != null) {
                } else {
                    skipFieldAndChild();
                    //log.debug("ignoring '{}' field exclusion and all child field because other field is already include", curToken.getValue());
                }
            } else {
                curTree.addChild(curToken, true);
            }
        }
    }

    private void processCommaToken() {
        if (prevToken == null || (FieldFilterTokenType.COMMA.equals(prevToken.getType())
                || FieldFilterTokenType.OPEN_PARENTHESIS.equals(prevToken.getType()))) {
            throw new IllegalArgumentException("',' can't be after ',' , '(' or be at first position");
        }
    }

    private void processOpenParenthesisToken() {
        if (prevToken == null || !FieldFilterTokenType.FIELD_NAME.equals(prevToken.getType())) {
            throw new IllegalArgumentException("'(' can't be after ',', '(', ')' or ba at first position.");
        }
        if (!skip) {
            curTree = curTree.getChild(prevToken);
            if (curTree == null) {
                throw new IllegalStateException("no child node with name '" + prevToken.getValue() + "'");
            }
        }
        parenthesisCount++;
    }

    private void processCloseParenthesisToken() {
        if (prevToken == null
                || FieldFilterTokenType.COMMA.equals(prevToken.getType())
                || FieldFilterTokenType.OPEN_PARENTHESIS.equals(prevToken.getType())) {
            throw new IllegalArgumentException("')' can't be after ',', '(' or be at first pos");
        }
        if (!skip) {
            FieldFilterTree parentTree = curTree.getParent();
            if (parentTree == null) {
                throw new IllegalArgumentException("no '(' matching ')'");
            }
            curTree = parentTree;
        }
        if (skip && parenthesisCount <= skipCounterRef) {
            skip = false;
            //log.debug("Leave the skip mode (parenthesis count = {}, skip counter ref = {})", parenthesisCount, skipCounterRef);
        }
        parenthesisCount--;
    }

    private void skipFieldAndChild() {
        if (!FieldFilterTokenType.FIELD_NAME.equals(curToken.getType())) {
            throw new IllegalStateException("Current token is not a field name (curToken=" + curToken + ")");
        }
        FieldFilterToken nextT = curFieldsFilter.prefetchToken();
        if (nextT != null && FieldFilterTokenType.OPEN_PARENTHESIS == nextT.getType()) {
            skip = true;
            skipCounterRef = parenthesisCount + 1;
            //log.debug("Enter skip mode (ref counter = {})", skipCounterRef);
        }
    }
}
