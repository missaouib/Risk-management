package com.smartosc.fintech.risk.rulemanagement.config;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class MultiRoutingDataSource extends AbstractRoutingDataSource {

    private Map<Object, Object> dataSources = new HashMap<>();

    MultiRoutingDataSource() {
        setTargetDataSources(dataSources);
    }

    public void addDataSource(DBTypeEnum key, DataSource dataSource) {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSources.get(key);
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
        dataSources.put(key, dataSource);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getCurrentDb();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        return (DataSource) dataSources.get(determineCurrentLookupKey());
    }

}
