package com.stylepatrick.tronTriggerSmartContract.service;

import com.stylepatrick.tronTriggerSmartContract.TronTriggerSmartContractConfig;
import com.stylepatrick.tronTriggerSmartContract.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Configuration
@EnableAsync
public class TriggerService {

    private final TronTriggerSmartContractConfig tronTriggerSmartContractConfig;

    @Async
    public String trigger(SmartContractDto smartContractDto) throws InterruptedException {

        //************************ mainAddress -> triggerAddress ********************************\\
        // Build main transaction
        CreateTransaction mainTransaction = new CreateTransaction();
        mainTransaction.setTo_address(smartContractDto.getTriggerAddress().getAddress());
        mainTransaction.setOwner_address(smartContractDto.getMainAddress().getAddress());
        mainTransaction.setAmount(smartContractDto.formatAmount());
        mainTransaction.setPermission_id(smartContractDto.getMainAddress().getPermissionId());

        Object mainTransactionResult = mainTransaction.createTransaction(tronTriggerSmartContractConfig.getApiUrl());


        // Sign main transaction with PrivateKey
        SignTransaction mainSignTransaction = new SignTransaction();
        mainSignTransaction.setTransaction(mainTransactionResult);
        mainSignTransaction.setPrivateKey(smartContractDto.getMainAddress().getPrivateKey());

        Object mainSignTransactionResult = mainSignTransaction.signTransaction(tronTriggerSmartContractConfig.getApiUrl());


        // Broadcast main transaction
        BrodcastTransaction brodcastMainTransaction = new BrodcastTransaction();
        brodcastMainTransaction.brodcastTransaction(tronTriggerSmartContractConfig.getApiUrl(), mainSignTransactionResult);


        // Wait n seconds
        Thread.sleep(tronTriggerSmartContractConfig.getSleep());


        //************************ Trigger Smart Contract on triggerAddress ********************************\\
        // Build request DTO
        TriggerSmartContract triggerSmartContract = new TriggerSmartContract();
        triggerSmartContract.setContract_address(smartContractDto.getContractAddress());
        triggerSmartContract.setOwner_address(smartContractDto.getTriggerAddress().getAddress());
        triggerSmartContract.setPermission_id(smartContractDto.getTriggerAddress().getPermissionId());
        triggerSmartContract.setCall_value(smartContractDto.getCallValue());
        triggerSmartContract.setFee_limit(smartContractDto.formatFeeLimit());
        triggerSmartContract.setFunction_selector(smartContractDto.getFunction());
        triggerSmartContract.setParameter(smartContractDto.getParameter());

        TriggerSmartContractResult triggerSmartContractResult = triggerSmartContract.triggerSmartContract(tronTriggerSmartContractConfig.getApiUrl());


        // Sign triggerContract transaction with PrivateKey
        SignTransaction triggerSmartContractSignTransaction = new SignTransaction();
        triggerSmartContractSignTransaction.setTransaction(triggerSmartContractResult.getTransaction());
        triggerSmartContractSignTransaction.setPrivateKey(smartContractDto.getTriggerAddress().getPrivateKey());

        Object triggerSmartContractSignTransactionResult = triggerSmartContractSignTransaction.signTransaction(tronTriggerSmartContractConfig.getApiUrl());

        // Broadcast triggerContract
        BrodcastTransaction brodcastTriggerSmartContractTransaction = new BrodcastTransaction();
        brodcastTriggerSmartContractTransaction.brodcastTransaction(tronTriggerSmartContractConfig.getApiUrl(), triggerSmartContractSignTransactionResult);


        // Wait n seconds
        Thread.sleep(tronTriggerSmartContractConfig.getSleep());


        //************************ Get new balance from triggerAddress and send all back to mainAddress ********************************\\
        GetAccountBalance triggerAccountBalance = new GetAccountBalance();
        triggerAccountBalance.setAddress(smartContractDto.getTriggerAddress().getAddress());
        AccountBalanceResult triggerAccountBalanceResult = triggerAccountBalance.getAccountBalance(tronTriggerSmartContractConfig.getApiUrl());

        // Build trigger transaction
        CreateTransaction triggerTransaction = new CreateTransaction();
        triggerTransaction.setTo_address(smartContractDto.getMainAddress().getAddress());
        triggerTransaction.setOwner_address(smartContractDto.getTriggerAddress().getAddress());
        triggerTransaction.setAmount(triggerAccountBalanceResult.getBalance());
        triggerTransaction.setPermission_id(smartContractDto.getTriggerAddress().getPermissionId());

        Object triggerTransactionResult = triggerTransaction.createTransaction(tronTriggerSmartContractConfig.getApiUrl());


        // Sign trigger transaction with PrivateKey
        SignTransaction triggerSignTransaction = new SignTransaction();
        triggerSignTransaction.setTransaction(triggerTransactionResult);
        triggerSignTransaction.setPrivateKey(smartContractDto.getTriggerAddress().getPrivateKey());

        Object triggerSignTransactionResult = triggerSignTransaction.signTransaction(tronTriggerSmartContractConfig.getApiUrl());

        // Broadcast trigger transaction
        BrodcastTransaction brodcastTriggerTransaction = new BrodcastTransaction();
        brodcastTriggerTransaction.brodcastTransaction(tronTriggerSmartContractConfig.getApiUrl(), triggerSignTransactionResult);

        return "Finished";
    }
}
