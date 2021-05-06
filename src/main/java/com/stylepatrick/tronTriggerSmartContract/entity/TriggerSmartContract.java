package com.stylepatrick.tronTriggerSmartContract.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class TriggerSmartContract extends RestTemplateBase {

    private String owner_address;
    private String contract_address;
    private String function_selector;
    private String parameter;
    private Integer fee_limit;
    private Integer call_value;

    @JsonProperty("Permission_id")
    private Integer Permission_id;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;

    public TriggerSmartContractResult triggerSmartContract(String apiUrl) {
        ResponseEntity<TriggerSmartContractResult> res = restTemplate()
                .postForEntity(apiUrl + "/wallet/triggersmartcontract", this, TriggerSmartContractResult.class);
        return res.getBody();
    }

}
