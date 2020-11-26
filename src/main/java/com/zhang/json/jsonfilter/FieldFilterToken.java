package com.zhang.json.jsonfilter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author zhang
 */
public class FieldFilterToken {

    private FieldFilterTokenType type;

    private String value;

    public FieldFilterToken(FieldFilterTokenType type, String value) {
        this.type = type;
        this.value = value;
        if (type == FieldFilterTokenType.FIELD_NAME && StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("token '" + FieldFilterTokenType.FIELD_NAME.name() + "' must have a value.");
        }
    }

    public FieldFilterToken(String value) {
        this(FieldFilterTokenType.fromValue(value), value);
    }

    public FieldFilterTokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
