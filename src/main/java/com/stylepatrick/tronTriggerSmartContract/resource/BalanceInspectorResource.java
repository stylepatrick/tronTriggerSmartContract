package com.stylepatrick.tronTriggerSmartContract.resource;

import com.stylepatrick.tronTriggerSmartContract.TronTriggerSmartContractConfig;
import com.stylepatrick.tronTriggerSmartContract.entity.AccountBalanceResult;
import com.stylepatrick.tronTriggerSmartContract.entity.GetAccountBalance;
import com.stylepatrick.tronTriggerSmartContract.entity.SmartContractDetails;
import com.stylepatrick.tronTriggerSmartContract.entity.SmartContractDto;
import com.stylepatrick.tronTriggerSmartContract.service.TriggerService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/inspector")
@EnableScheduling
@Configuration
@AllArgsConstructor
public class BalanceInspectorResource {

    private final TriggerService triggerService;
    private final TronTriggerSmartContractConfig tronTriggerSmartContractConfig;

    private static boolean finished = false;
    List<List<String>> records;

    @GetMapping(value = "reset")
    public String resetInspector() {
        finished = false;
        return "Done!";
    }


    // Run every 30s
    @Scheduled(fixedRate = 30000)
    public void createLinkedBySheduler() {

        if (records.isEmpty()) {
            try (BufferedReader br = new BufferedReader(new FileReader("src/wallets.csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    records.add(Arrays.asList(values));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                records.remove(0);
            }
        }

        GetAccountBalance smartContractBalance = new GetAccountBalance();
        smartContractBalance.setAddress(tronTriggerSmartContractConfig.getContractAddress());
        AccountBalanceResult balance = smartContractBalance.getAccountBalance(tronTriggerSmartContractConfig.getApiUrl());

        Date now = new Date();
        System.out.println(now + " - " + balance.getBalance());

        if (balance.getBalance() != null && balance.getBalance() >= 1000000000 && !finished) {
            records.forEach(x -> {
                int permissionId;
                if (x.get(3).equals("multiSign")) {
                    permissionId = 2;
                } else {
                    permissionId = 1;
                }

                SmartContractDetails mainAddress = new SmartContractDetails();
                mainAddress.setAddress(tronTriggerSmartContractConfig.getMainAddress());
                mainAddress.setPrivateKey(tronTriggerSmartContractConfig.getMainAddressPrivateKey());
                mainAddress.setPermissionId(tronTriggerSmartContractConfig.getMainAddressPermissionId());

                SmartContractDetails triggerAddress = new SmartContractDetails();
                triggerAddress.setAddress(x.get(1));
                triggerAddress.setPrivateKey(x.get(2));
                triggerAddress.setPermissionId(permissionId);

                SmartContractDto smartContractDto = new SmartContractDto();
                smartContractDto.setMainAddress(mainAddress);
                smartContractDto.setTriggerAddress(triggerAddress);
                smartContractDto.setContractAddress(tronTriggerSmartContractConfig.getContractAddress());
                smartContractDto.setAmount(tronTriggerSmartContractConfig.getExchangeAmount());
                smartContractDto.setCallValue(tronTriggerSmartContractConfig.getCallValue());
                smartContractDto.setFeeLimit(tronTriggerSmartContractConfig.getFeeLimit());
                smartContractDto.setFunction(tronTriggerSmartContractConfig.getFunction());
                smartContractDto.setParameter(tronTriggerSmartContractConfig.getParameter());

                try {
                    triggerService.trigger(smartContractDto);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            finished = true;
        }

    }

}
