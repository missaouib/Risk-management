package com.smartosc.fintech.risk.rulemanagement.config;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DBContextHolder {
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    public static void setCurrentDb(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum getCurrentDb() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}
