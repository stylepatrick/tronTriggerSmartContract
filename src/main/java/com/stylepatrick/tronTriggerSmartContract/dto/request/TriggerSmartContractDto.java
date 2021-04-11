package com.stylepatrick.tronTriggerSmartContract.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriggerSmartContractDto {

    private String owner_address;
    private String contract_address;
    private String function_selector = "reward()";
    private String parameter = "";
    private Integer fee_limit = 3000000;

    @JsonProperty("Permission_id")
    private Integer Permission_id = 2;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;

}
