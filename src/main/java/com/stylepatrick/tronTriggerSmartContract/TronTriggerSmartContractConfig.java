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

    @Value("${mainAddress.address}")
    private String mainAddress;

    @Value("${mainAddress.privateKey}")
    private String mainAddressPrivateKey;

    @Value("${mainAddress.permissionId}")
    private Integer mainAddressPermissionId;

    @Value("${mainAddress.exchangeAmount}")
    private Integer exchangeAmount;

    @Value("${contract.address}")
    private String contractAddress;

    @Value("${contract.callValue}")
    private Integer callValue;

    @Value("${contract.feeLimit}")
    private Integer feeLimit;

    @Value("${contract.function}")
    private String function;

    @Value("${contract.parameter}")
    private String parameter;


}
