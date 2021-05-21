package com.smartosc.fintech.risk.rulemanagement.enumeration;

import lombok.Getter;

@Getter
public enum DataTypeEnum {
    INTEGER("Integer", "Integer.class"),
    STRING("String", "String.class"),
    BYTE("Byte", "Byte.class"),
    LONG("Long", "Long.class"),
    FLOAT("Float", "Float.class"),
    DATE("Date", "java.sql.Timestamp.class"),
    BIG_DECIMAL("BigDecimal", "BigDecimal.class"),
    DOUBLE("Double", "Double.class"),
    SHORT("Short", "Short.class"),
    BOOLEAN("Boolean", "Boolean.class");
    private final String code;
    private final String className;

    DataTypeEnum(String code, String className) {
        this.code = code;
        this.className = className;
    }

    public static String getValueByString(String stringInput) {
        for (DataTypeEnum s : DataTypeEnum.values()) {
            if (s.getClassName().equals(stringInput) || s.getCode().equals(stringInput)) {
                return s.getClassName();
            }
        }
        return null;
    }

    public static boolean checkExistedValue(String stringInput) {
        for (DataTypeEnum s : DataTypeEnum.values()) {
            if (s.getClassName().equals(stringInput) || s.getCode().equals(stringInput)) {
                return true;
            }
        }
        return false;
    }

}
