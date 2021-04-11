package com.stylepatrick.tronTriggerSmartContract.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAccountBalanceDto {

    private String address;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;
}
