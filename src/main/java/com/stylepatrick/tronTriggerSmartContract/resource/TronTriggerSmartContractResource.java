package com.stylepatrick.tronTriggerSmartContract.resource;

import com.stylepatrick.tronTriggerSmartContract.dto.*;
import com.stylepatrick.tronTriggerSmartContract.service.TronTriggerSmartContractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TronTriggerSmartContractResource {

    private final TronTriggerSmartContractService tronTriggerSmartContractService;

    public TronTriggerSmartContractResource(
            TronTriggerSmartContractService tronTriggerSmartContractService
    ) {
        this.tronTriggerSmartContractService = tronTriggerSmartContractService;
    }

    @GetMapping(value = "status")
    public String helloWorld() {
        return "API Running!";
    }

    @GetMapping(value = "account/{address}")
    public AccountBalanceDto getAccountByBase58(
            @PathVariable String address
    ) {
        TrxAddressDto trxAddressDto = new TrxAddressDto();
        trxAddressDto.setAddress(address);
        return tronTriggerSmartContractService.getBalance(trxAddressDto);
    }

    @PostMapping(value = "trigger")
    public Object trigger(
            @RequestBody SmartContractDto smartContractDto
    ) throws InterruptedException {
        //************************ mainAddress -> triggerAddress ********************************\\
        // Build transaction
        TrxTransactionDto mainTrxTransactionDto = tronTriggerSmartContractService.buildTransaction(
                smartContractDto.getTriggerAddress().getAddress(),
                smartContractDto.getMainAddress().getAddress(),
                smartContractDto.getExchangeAmount(),
                smartContractDto.getMainAddress().getPermissionId()
        );

        // Sign Transaction with PrivateKey
        Object mainSignTransaction = tronTriggerSmartContractService.signTransaction(
                mainTrxTransactionDto,
                smartContractDto.getMainAddress().getPrivateKey()
        );

        // Brodcast Transaction
        tronTriggerSmartContractService.brodcastTransaction(mainSignTransaction);

        // Wait 10 seconds to trigger smartContract
        Thread.sleep(10000);

        // Trigger Smart Contract
        TrxTriggerSmartContractDto trxTriggerSmartContractDto = new TrxTriggerSmartContractDto();
        trxTriggerSmartContractDto.setContract_address(smartContractDto.getContractAddress());
        trxTriggerSmartContractDto.setOwner_address(smartContractDto.getTriggerAddress().getAddress());
        trxTriggerSmartContractDto.setPermission_id(smartContractDto.getTriggerAddress().getPermissionId());

        Object smartContractTransaction = tronTriggerSmartContractService.triggerSmartContract(trxTriggerSmartContractDto);

        Object signSmartContractTransaction = tronTriggerSmartContractService.signTransaction(
                smartContractTransaction,
                smartContractDto.getTriggerAddress().getPrivateKey()
        );

        // Brodcast transaction and return
        return tronTriggerSmartContractService.brodcastTransaction(signSmartContractTransaction);
    }

}
