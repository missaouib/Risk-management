package com.smartosc.fintech.risk.rulemanagement.service.mapper;


import com.smartosc.fintech.risk.rulemanagement.entity.ObjectModel;
import com.smartosc.fintech.risk.rulemanagement.entity.Properties;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

public class DataMapper implements RowMapper<ObjectModel> {
    private String tableName;

    public DataMapper(String tableName) {
        this.tableName = tableName;
    }

    @SneakyThrows
    @Override
    public ObjectModel mapRow(ResultSet rs, int rowNum) {

        ObjectModel dataModel = new ObjectModel();
        dataModel.setModelName(tableName);
        Set<Properties> propertiesList = new HashSet<>();

        while (rs.next()) {
            Properties properties = new Properties();
            properties.setName(rs.getString(1));
            properties.setDataType(DataTypeMapper.convertTypeStringToClass(rs.getString(2)));
            propertiesList.add(properties);
        }
        dataModel.setProperties(propertiesList);
        return dataModel;

    }

}
