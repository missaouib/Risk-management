package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author vuhq
 * @Since 2/2/2021
 */
@Repository
public interface DataModelRepository extends JpaRepository<DataModelEntity, Long>, JpaSpecificationExecutor<DataModelEntity> {

    DataModelEntity findFirstByModelNameAndGroupModel(String modelName, GroupModelEntity groupModelEntity);
    List<DataModelEntity> findAllByStatusNotAndGroupModel(StatusEnum statusEnum, GroupModelEntity groupModelEntity);
    DataModelEntity findByIdAndStatusNot(Long id, StatusEnum statusEnum);
}
