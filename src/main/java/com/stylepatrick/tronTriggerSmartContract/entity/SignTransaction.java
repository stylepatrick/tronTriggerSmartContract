package com.stylepatrick.tronTriggerSmartContract.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class SignTransaction extends RestTemplateBase {

    private Object transaction;
    private String privateKey;

    public Object signTransaction(String apiUrl) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(apiUrl + "/wallet/gettransactionsign", this, Object.class);
        return res.getBody();
    }

}
