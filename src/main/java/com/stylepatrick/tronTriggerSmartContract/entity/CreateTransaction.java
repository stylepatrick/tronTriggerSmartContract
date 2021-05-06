package com.stylepatrick.tronTriggerSmartContract.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class CreateTransaction extends RestTemplateBase {

    private String to_address;
    private String owner_address;
    private Integer amount;

    @JsonProperty("Permission_id")
    private Integer Permission_id;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;

    public Object createTransaction(String apiUrl) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(apiUrl + "/wallet/createtransaction", this, Object.class);
        return res.getBody();
    }


}
