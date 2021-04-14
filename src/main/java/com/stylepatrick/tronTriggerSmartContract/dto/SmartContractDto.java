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
    private Integer callValue;
    private Integer feeLimit;
    private String function;
    private String parameter;

    public Integer formatAmount() {
        return this.amount * 1000000;
    }

    public Integer formatFeeLimit() {
        return this.feeLimit * 100000000;
    }

}
