package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrxTriggerSmartContractDto {

    private String owner_address;
    private String contract_address;
    private String function_selector = "reward()";
    private String parameter = "";
    private Integer fee_limit;
    private Integer permission_id;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;

}
