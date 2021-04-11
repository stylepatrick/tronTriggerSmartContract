package com.stylepatrick.tronTriggerSmartContract.resource;

import com.stylepatrick.tronTriggerSmartContract.TronTriggerSmartContractConfig;
import com.stylepatrick.tronTriggerSmartContract.dto.SmartContractDto;
import com.stylepatrick.tronTriggerSmartContract.dto.request.GetAccountBalanceDto;
import com.stylepatrick.tronTriggerSmartContract.dto.request.TriggerSmartContractDto;
import com.stylepatrick.tronTriggerSmartContract.dto.result.AccountBalanceResultDto;
import com.stylepatrick.tronTriggerSmartContract.dto.result.SmartContractResultDto;
import com.stylepatrick.tronTriggerSmartContract.service.TronTriggerSmartContractService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class TronTriggerSmartContractResource {

    private final TronTriggerSmartContractService tronTriggerSmartContractService;
    private final TronTriggerSmartContractConfig tronTriggerSmartContractConfig;

    public TronTriggerSmartContractResource(
            TronTriggerSmartContractService tronTriggerSmartContractService,
            TronTriggerSmartContractConfig tronTriggerSmartContractConfig
    ) {
        this.tronTriggerSmartContractService = tronTriggerSmartContractService;
        this.tronTriggerSmartContractConfig = tronTriggerSmartContractConfig;
    }

    @GetMapping(value = "status")
    public String helloWorld() {
        return "API Running!";
    }

    @PostMapping(value = "trigger")
    public Object trigger(
            @RequestBody SmartContractDto smartContractDto
    ) throws InterruptedException {
        //************************ mainAddress -> triggerAddress ********************************\\
        // Build main transaction
        Object mainTransaction = tronTriggerSmartContractService.buildTransaction(
                smartContractDto.getTriggerAddress().getAddress(),
                smartContractDto.getMainAddress().getAddress(),
                smartContractDto.formatAmount(),
                smartContractDto.getMainAddress().getPermissionId()
        );

        // Sign main transaction with PrivateKey
        Object mainTransactionSign = tronTriggerSmartContractService.signTransaction(
                mainTransaction,
                smartContractDto.getMainAddress().getPrivateKey()
        );

        // Broadcast main transaction
        tronTriggerSmartContractService.brodcastTransaction(mainTransactionSign);

        // Wait n seconds
        Thread.sleep(tronTriggerSmartContractConfig.getSleep());

        //************************ Trigger Smart Contract on triggerAddress ********************************\\
        // Build request DTO
        TriggerSmartContractDto triggerSmartContractDto = new TriggerSmartContractDto();
        triggerSmartContractDto.setContract_address(smartContractDto.getContractAddress());
        triggerSmartContractDto.setOwner_address(smartContractDto.getTriggerAddress().getAddress());
        triggerSmartContractDto.setPermission_id(smartContractDto.getTriggerAddress().getPermissionId());

        // Trigger Smart Contract
        SmartContractResultDto smartContractResultDto = tronTriggerSmartContractService.triggerSmartContract(triggerSmartContractDto);

        // Sign triggerContract transaction with PrivateKey
        Object signSmartContractTransaction = tronTriggerSmartContractService.signTransaction(
                smartContractResultDto.getTransaction(),
                smartContractDto.getTriggerAddress().getPrivateKey()
        );

        // Broadcast triggerContract
        tronTriggerSmartContractService.brodcastTransaction(signSmartContractTransaction);

        //************************ Get new balance from triggerAddress and send all back to mainAddress ********************************\\
        GetAccountBalanceDto getAccountBalanceDto = new GetAccountBalanceDto();
        getAccountBalanceDto.setAddress(smartContractDto.getTriggerAddress().getAddress());
        AccountBalanceResultDto triggerAddressBalance = tronTriggerSmartContractService.getBalance(getAccountBalanceDto);

        // Build trigger transaction
        Object triggerTransaction = tronTriggerSmartContractService.buildTransaction(
                smartContractDto.getMainAddress().getAddress(),
                smartContractDto.getTriggerAddress().getAddress(),
                triggerAddressBalance.getBalance(),
                smartContractDto.getTriggerAddress().getPermissionId()
        );

        // Sign trigger transaction with PrivateKey
        Object triggerTransactionSign = tronTriggerSmartContractService.signTransaction(
                triggerTransaction,
                smartContractDto.getTriggerAddress().getPrivateKey()
        );

        // Broadcast trigger transaction
        tronTriggerSmartContractService.brodcastTransaction(triggerTransactionSign);

        return "Finished";
    }

}
