package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmartContractDetails {

    private String address;
    private String privateKey;
    private Integer permissionId;
}
