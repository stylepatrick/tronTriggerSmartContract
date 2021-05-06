package com.stylepatrick.tronTriggerSmartContract.entity;

import org.springframework.http.ResponseEntity;

public class BrodcastTransaction extends RestTemplateBase {

    public Object brodcastTransaction(String apiUrl, Object object) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(apiUrl + "/wallet/broadcasttransaction", object, Object.class);
        return res.getBody();
    }
}
