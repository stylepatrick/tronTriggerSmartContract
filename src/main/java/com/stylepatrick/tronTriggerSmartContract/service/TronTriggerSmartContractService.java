package com.stylepatrick.tronTriggerSmartContract.service;

import com.stylepatrick.tronTriggerSmartContract.TronTriggerSmartContractConfig;
import com.stylepatrick.tronTriggerSmartContract.dto.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TronTriggerSmartContractService {

    private final TronTriggerSmartContractConfig tronTriggerSmartContractConfig;

    public TronTriggerSmartContractService(
            TronTriggerSmartContractConfig tronTriggerSmartContractConfig
    ) {
        this.tronTriggerSmartContractConfig = tronTriggerSmartContractConfig;
    }

    public AccountBalanceDto getBalance(TrxAddressDto trxAddressDto){
        ResponseEntity<AccountBalanceDto> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/getaccount", trxAddressDto, AccountBalanceDto.class);
        return res.getBody();
    }

    public TrxTransactionDto buildTransaction(String toAddress, String fromAddress, Integer amount, Integer permissionId) {
        TrxCreateTransactionDto trxCreateTransactionDto = new TrxCreateTransactionDto();
        trxCreateTransactionDto.setTo_address(toAddress);
        trxCreateTransactionDto.setOwner_address(fromAddress);
        trxCreateTransactionDto.setAmount(amount);
        trxCreateTransactionDto.setPermission_id(permissionId);
        ResponseEntity<TrxTransactionDto> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/createtransaction", trxCreateTransactionDto, TrxTransactionDto.class);
        return res.getBody();
    }

    public Object signTransaction(Object transaction, String privateKey) {
        TrxSignTransactionDto trxSignTransactionDto = new TrxSignTransactionDto();
        trxSignTransactionDto.setTransaction(transaction);
        trxSignTransactionDto.setPrivateKey(privateKey);
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/gettransactionsign", trxSignTransactionDto, Object.class);
        return res.getBody();
    }

    public Object brodcastTransaction(Object object) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/broadcasttransaction", object, Object.class);
        return res.getBody();
    }

    public Object triggerSmartContract(TrxTriggerSmartContractDto trxTriggerSmartContractDto) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/triggersmartcontract", trxTriggerSmartContractDto, Object.class);
        return res.getBody();
    }

    private RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // Set Content Type for POST response to application/octet_stream
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}
