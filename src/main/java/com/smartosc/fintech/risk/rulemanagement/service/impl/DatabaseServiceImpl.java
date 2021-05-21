package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.dto.DatabaseDto;
import com.smartosc.fintech.risk.rulemanagement.entity.DatabaseEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.ObjectModel;
import com.smartosc.fintech.risk.rulemanagement.exception.ConnectionErrorException;
import com.smartosc.fintech.risk.rulemanagement.repository.DataModelGroupRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.DatabaseRepository;
import com.smartosc.fintech.risk.rulemanagement.service.DatabaseService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.DataMapper;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {
    private final DatabaseRepository databaseRepository;
    private final DataModelGroupRepository dataModelGroupRepository;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public DatabaseDto getAllDatabaseInfo() {

        List<DatabaseEntity> databaseEntities = databaseRepository.findAll();

        List<GroupModelEntity> groupModelEntities = dataModelGroupRepository.findAll();

        DatabaseDto databaseDtos = new DatabaseDto();

        databaseDtos.setDriverList(databaseEntities);

        databaseDtos.setGroupList(groupModelEntities);

        return databaseDtos;
    }

    @Override
    public List<ObjectModel> showListTable() {
        List<String> listTables = new ArrayList<>();
        try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};

            ResultSet rs = metaData.getTables(null, null, "%", types);
            while (rs.next()) {
                listTables.add(rs.getString(3));
            }
        } catch (Exception e) {
            throw new ConnectionErrorException();
        }
        List<ObjectModel> listSchemaTables = new ArrayList<>();
        for (String tableName : listTables) {
            StringBuilder queryBuilder = new StringBuilder();
            ObjectModel document = new ObjectModel();
            queryBuilder.append("DESC ").append(tableName);
            document.setModelName(tableName);
            try {
                document = jdbcTemplate.queryForObject(queryBuilder.toString(), new DataMapper(tableName));
                listSchemaTables.add(document);
                ObjectModel finalDocument = new ObjectModel();
                if (document != null) {
                    finalDocument.setModelName(document.getModelName());
                }
            } catch (Exception e) {
                if (document != null) {
                    document.setProperties(null);
                    listSchemaTables.add(document);
                }
            }
        }
        return listSchemaTables;
    }

}
