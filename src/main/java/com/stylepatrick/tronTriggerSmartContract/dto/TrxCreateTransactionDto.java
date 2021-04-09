package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class TrxCreateTransactionDto {

    private String to_address;
    private String owner_address;
    private Integer amount;
    private Integer permission_id;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;


}
