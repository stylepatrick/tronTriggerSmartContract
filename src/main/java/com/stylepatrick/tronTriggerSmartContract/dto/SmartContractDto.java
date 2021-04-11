package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmartContractDto {

    private SmartContractDetails mainAddress;
    private SmartContractDetails triggerAddress;
    private String contractAddress;
    private Integer amount;

    public Integer formatAmount() {
        return this.amount * 1000000;
    }

}
