package com.stylepatrick.tronTriggerSmartContract.service;

import com.stylepatrick.tronTriggerSmartContract.TronTriggerSmartContractConfig;
import com.stylepatrick.tronTriggerSmartContract.dto.request.GetAccountBalanceDto;
import com.stylepatrick.tronTriggerSmartContract.dto.request.CreateTransactionDto;
import com.stylepatrick.tronTriggerSmartContract.dto.request.SignTransactionDto;
import com.stylepatrick.tronTriggerSmartContract.dto.request.TriggerSmartContractDto;
import com.stylepatrick.tronTriggerSmartContract.dto.result.AccountBalanceResultDto;
import com.stylepatrick.tronTriggerSmartContract.dto.result.SmartContractResultDto;
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

    public AccountBalanceResultDto getBalance(GetAccountBalanceDto getAccountBalanceDto) {
        ResponseEntity<AccountBalanceResultDto> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/getaccount", getAccountBalanceDto, AccountBalanceResultDto.class);
        return res.getBody();
    }

    public Object buildTransaction(String toAddress, String fromAddress, Integer amount, Integer Permission_id) {
        CreateTransactionDto createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setTo_address(toAddress);
        createTransactionDto.setOwner_address(fromAddress);
        createTransactionDto.setAmount(amount);
        createTransactionDto.setPermission_id(Permission_id);
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/createtransaction", createTransactionDto, Object.class);
        return res.getBody();
    }

    public Object signTransaction(Object transaction, String privateKey) {
        SignTransactionDto signTransactionDto = new SignTransactionDto();
        signTransactionDto.setTransaction(transaction);
        signTransactionDto.setPrivateKey(privateKey);
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/gettransactionsign", signTransactionDto, Object.class);
        return res.getBody();
    }

    public Object brodcastTransaction(Object object) {
        ResponseEntity<Object> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/broadcasttransaction", object, Object.class);
        return res.getBody();
    }

    public SmartContractResultDto triggerSmartContract(TriggerSmartContractDto triggerSmartContractDto) {
        ResponseEntity<SmartContractResultDto> res = restTemplate()
                .postForEntity(tronTriggerSmartContractConfig.getApiUrl() + "/wallet/triggersmartcontract", triggerSmartContractDto, SmartContractResultDto.class);
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
