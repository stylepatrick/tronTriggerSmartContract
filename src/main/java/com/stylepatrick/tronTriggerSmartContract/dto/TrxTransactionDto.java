package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class TrxTransactionDto {

    private String txID;
    private Boolean visible;
    private Object raw_data;
    private String raw_data_hex;
    private String[] signature;
}
