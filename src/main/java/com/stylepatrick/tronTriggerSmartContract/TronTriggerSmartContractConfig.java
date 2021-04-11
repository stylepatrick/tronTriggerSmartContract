package com.stylepatrick.tronTriggerSmartContract;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class TronTriggerSmartContractConfig {

    @Value("${tronApi.url}")
    private String apiUrl;

    @Value("${tronApi.sleep}")
    private Integer sleep;

}
