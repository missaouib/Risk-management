package com.smartosc.fintech.risk.rulemanagement.config;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    @Qualifier("mainDataSource")
    @ConfigurationProperties(prefix = "app.datasource.main")
    protected DataSource getMainDataSource() {
        return DataSourceBuilder
                .create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @Primary
    @Scope("singleton")
    public AbstractRoutingDataSource multiRoutingDataSource(@Autowired @Qualifier("mainDataSource") DataSource mainDataSource) {
        MultiRoutingDataSource routingDataSource = new MultiRoutingDataSource();
        routingDataSource.addDataSource(DBTypeEnum.MAIN, mainDataSource);
        routingDataSource.setDefaultTargetDataSource(mainDataSource);
        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setShowSql(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.smartosc.fintech.risk.rulemanagement.entity");
        factory.setDataSource(getMainDataSource());
        return factory;
    }
}
