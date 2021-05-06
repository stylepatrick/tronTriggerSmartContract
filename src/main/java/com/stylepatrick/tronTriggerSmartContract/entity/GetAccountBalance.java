package com.stylepatrick.tronTriggerSmartContract.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class GetAccountBalance extends RestTemplateBase {

    private String address;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;

    public AccountBalanceResult getAccountBalance(String apiUrl) {
        ResponseEntity<AccountBalanceResult> res = restTemplate()
                .postForEntity(apiUrl + "/wallet/getaccount", this, AccountBalanceResult.class);
        return res.getBody();
    }
}
