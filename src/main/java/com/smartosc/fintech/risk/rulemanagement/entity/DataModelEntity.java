package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Table(name = "data_model")
@Entity
public class DataModelEntity extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name")
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "group_model_id")
    private GroupModelEntity groupModel = new GroupModelEntity();

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @OneToMany(mappedBy = "dataModel", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private Set<AttributeEntity> properties;

    public String getClassName() {
        return StringUtils.isEmpty(this.groupModel.getName()) ? this.modelName : this.groupModel.getName() + "." + this.modelName;
    }

    public void setProperties(Set<AttributeEntity> properties) {
        this.properties = properties;
        if (properties == null) {
            return;
        }

        for (AttributeEntity attribute : this.properties) {
            attribute.setDataModel(this);
        }
    }

}
