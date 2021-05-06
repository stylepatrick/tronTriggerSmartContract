package com.stylepatrick.tronTriggerSmartContract.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TriggerSmartContractResult {

    private Map<String, Object> result;
    private Map<String, Object> transaction;

}
