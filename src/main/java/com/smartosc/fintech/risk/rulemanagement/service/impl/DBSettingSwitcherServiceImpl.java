package com.smartosc.fintech.risk.rulemanagement.service.impl;


import com.smartosc.fintech.risk.rulemanagement.config.MultiRoutingDataSource;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DBSettings;
import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.service.IDBSettingSwitcherService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DBSettingSwitcherServiceImpl implements IDBSettingSwitcherService {

    private final AbstractRoutingDataSource multiRoutingDataSource;

    public DBSettingSwitcherServiceImpl(AbstractRoutingDataSource multiRoutingDataSource) {
        this.multiRoutingDataSource = multiRoutingDataSource;
    }

    @Override
    public void applySettings(DBSettings dbSettings) {
        if (multiRoutingDataSource instanceof MultiRoutingDataSource) {
            // by default Spring uses DataSource from apache tomcat
            String urlConnection = "jdbc:" + dbSettings.getDatabaseType() + "://" + dbSettings.getHostName() + ":"
                    + dbSettings.getPort() + "/" + dbSettings.getDatabaseName() + "?useUnicode=yes&characterEncoding=UTF-8";
            DataSource dataSource = DataSourceBuilder
                    .create()
                    .type(HikariDataSource.class)
                    .username(dbSettings.getUserName())
                    .password(dbSettings.getPassword())
                    .url(urlConnection)
                    .driverClassName(dbSettings.getDriverClassName())
                    .build();

            MultiRoutingDataSource rds = (MultiRoutingDataSource) multiRoutingDataSource;

            rds.addDataSource(DBTypeEnum.NEW, dataSource);
        }
    }

}
