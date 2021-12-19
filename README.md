# tronTriggerSmartContract

Send x TRX coins and triggers a smart contract with Spring Boot 2. There are two main functions. Trigger smart contract on single address manually via rest API, or asynchronous via planned scheduler on multiply addresses from a .csv file.

#### 1. Trigger a smart contract with the rest API:

Send x TRX coins from the main address to the trigger address and triggers a given smart contract function.
   
1. Sends x TRX from mainAddress to triggerAddress
2. Wait n seconds
3. Trigger a smart contract on the triggerAddress
4. Get new balance on triggerAddress and sends whole amount back to mainAddress 
   
Example Post Request (/api/trigger)
```
{
    "mainAddress": {
        "address": "address",
        "privateKey": "privateKey",
        "permissionId": permissionId
    },
    "triggerAddress": {
        "address": "address",
        "privateKey": "privateKey",
        "permissionId": permissionId
    },
    "contractAddress": "smartContractAddress",
    "amount": exchangeAmount,
    "callValue": callValue,
    "feeLimit": feeLimit,
    "function": "function()",
    "parameter": ""
}
```

#### 2. Trigger smart contract on multiply wallets given from a .csv file if balance on smart contract is available: 

There is a scheduler running every 30s which checks the balance on the smart contract address.

If the balance is > 1000 TRX then:
1. Sends x TRX from mainAddress to triggerAddress
2. Wait n seconds
3. Trigger a smart contract on the triggerAddress
4. Get new balance on triggerAddress and sends whole amount back to mainAddress 
   
This steps will start asynchronous on all addresses given in the .csv file. 
When every address in the .csv list is done the functions are re-startable via a rest API endpoint (/inspector/reset).

#### DockerHub

https://hub.docker.com/repository/docker/stylepatrick/tron-trigger-smart-contract
