package com.smartosc.fintech.risk.rulemanagement.startup;

import com.smartosc.fintech.risk.rulemanagement.config.DBContextHolder;
import com.smartosc.fintech.risk.rulemanagement.entity.DataTypeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.OperatorEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.service.DataTypeService;
import com.smartosc.fintech.risk.rulemanagement.service.ModelGroupService;
import com.smartosc.fintech.risk.rulemanagement.service.OperatorCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class DataMaster implements ApplicationListener<ContextRefreshedEvent> {

    private final DataTypeService dataTypeService;
    private final ModelGroupService modelGroupService;
    private final OperatorCacheService operatorCacheService;


    private static Map<String, DataTypeEntity> dataTypeCache;

    private static Map<Long, GroupModelEntity> modelGroupCache;

    private static Map<String, OperatorEntity> operatorCache;

    @Override

    public void onApplicationEvent(ContextRefreshedEvent event) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        log.info("get data type");
        setDataTypeCache(dataTypeService.getAllDataType());
        log.info(dataTypeCache.toString());
        log.info("get data model group");
        setModelGroupCache(modelGroupService.getAllModelGroup());
        log.info(modelGroupCache.toString());
        setOperatorCache(operatorCacheService.getAllOperators());
        log.info(operatorCache.toString());
    }

    public static Map<String, DataTypeEntity> getDataTypeCache() {
        return dataTypeCache;
    }

    public static Map<Long, GroupModelEntity> getModelGroupCache() {
        return modelGroupCache;
    }

    private static void setDataTypeCache(Map<String, DataTypeEntity> dataTypeCache) {
        DataMaster.dataTypeCache = dataTypeCache;
    }

    private static void setModelGroupCache(Map<Long, GroupModelEntity> modelGroupCache) {
        DataMaster.modelGroupCache = modelGroupCache;
    }

    public static Map<String, OperatorEntity> getOperatorCache() {
        return operatorCache;
    }

    public static void setOperatorCache(Map<String, OperatorEntity> operatorCache) {
        DataMaster.operatorCache = operatorCache;
    }

    public void updateDataTypeCache() {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        log.info("update data type");
        setDataTypeCache(dataTypeService.getAllDataType());
    }

    public void updateModelGroupCache() {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        log.info("update data model group");
        setModelGroupCache(modelGroupService.getAllModelGroup());
    }
}
