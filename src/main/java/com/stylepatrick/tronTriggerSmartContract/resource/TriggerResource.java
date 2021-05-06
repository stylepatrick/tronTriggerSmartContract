package com.stylepatrick.tronTriggerSmartContract.resource;

import com.stylepatrick.tronTriggerSmartContract.entity.SmartContractDto;
import com.stylepatrick.tronTriggerSmartContract.service.TriggerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class TriggerResource {

    private final TriggerService triggerService;

    @GetMapping(value = "status")
    public String helloWorld() {
        return "API Running!";
    }

    @PostMapping(value = "trigger")
    public String trigger(
            @RequestBody SmartContractDto smartContractDto) throws InterruptedException {
        return triggerService.trigger(smartContractDto);
    }

}
