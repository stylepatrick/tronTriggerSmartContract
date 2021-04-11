package com.stylepatrick.tronTriggerSmartContract.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignTransactionDto {

    private Object transaction;
    private String privateKey;

}
