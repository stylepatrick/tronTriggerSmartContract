package com.stylepatrick.tronTriggerSmartContract.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrxSignTransactionDto {

    private Object transaction;
    private String privateKey;

}
