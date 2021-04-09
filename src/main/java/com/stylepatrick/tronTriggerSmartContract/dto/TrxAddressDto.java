package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrxAddressDto {

    private String address;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;
}
