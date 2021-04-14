package com.stylepatrick.tronTriggerSmartContract.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class CreateTransactionDto {

    private String to_address;
    private String owner_address;
    private Integer amount;

    @JsonProperty("Permission_id")
    private Integer Permission_id;

    // Optional,whether the address is in base58 format; true if yes
    private Boolean visible = true;


}
