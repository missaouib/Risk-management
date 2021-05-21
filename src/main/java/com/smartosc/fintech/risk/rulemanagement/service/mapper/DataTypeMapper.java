package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataTypeMapper {
    public static DataTypeEnum convertTypeStringToClass(String typeSql) {
        DataTypeEnum result;
        if (typeSql.contains("(")) {
            typeSql = typeSql.substring(0, typeSql.indexOf('('));
        }
        typeSql = typeSql.toUpperCase();
        switch (typeSql) {
            case "CHAR":
            case "VARCHAR":
            case "LONGVARCHAR":
                result = DataTypeEnum.STRING;
                break;

            case "NUMERIC":
            case "DECIMAL":
                result = DataTypeEnum.BIG_DECIMAL;
                break;

            case "Types":
                result = DataTypeEnum.BOOLEAN;
                break;

            case "TINYINT":
                result = DataTypeEnum.BYTE;
                break;

            case "SMALLINT":
                result = DataTypeEnum.SHORT;
                break;

            case "INTEGER":
            case "INT":
                result = DataTypeEnum.INTEGER;
                break;

            case "BIGINT":
                result = DataTypeEnum.LONG;
                break;
            case "REAL":
            case "FLOAT":
                result = DataTypeEnum.FLOAT;
                break;

            case "DOUBLE":
                result = DataTypeEnum.DOUBLE;
                break;
            case "DATE":
            case "DATETIME":
            case "TIMESTAMP":
            case "TIME":
                result = DataTypeEnum.DATE;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + typeSql.toUpperCase());
        }

        return result;
    }

}
