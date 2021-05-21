package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import java.sql.Timestamp;

public class DateTimeMapper {

    private DateTimeMapper() throws IllegalAccessException {
        throw new IllegalAccessException("DateTimeMapper");
    }

    public static Long timestampToLong(Timestamp timestamp) {
        Long time = 0L;
        if (timestamp != null) {
            return timestamp.getTime();
        } else return time;
    }

    public static Timestamp longToTimestamp(Long timeLong) {
        if (timeLong != null) {
            return new Timestamp(timeLong);
        } else return null;
    }
}