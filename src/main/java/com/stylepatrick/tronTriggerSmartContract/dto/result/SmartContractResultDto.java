package com.stylepatrick.tronTriggerSmartContract.dto.result;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SmartContractResultDto {

    private Map<String, Object> result;
    private Map<String, Object> transaction;

}
