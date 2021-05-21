package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.request.DBSettings;

public interface IDBSettingSwitcherService {
    void applySettings(DBSettings dbSettings);

}
